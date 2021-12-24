# Spring Security

## 原理

### FilterChainProxy

- FilterChainProxy的创建初始化过程是在security配置解析过程中完成的。解析工作主要由SecurityNamespaceHandler的parse()完成。如果是采用注解方式，@EnableWebSecurity注解会@Import WebSecurityConfiguration，WebSecurityConfiguration中的springSecurityFilterChain()方法将会创建FilterChainProxy实例，具体是在WebSecurity的performBuild()方法中。

- 使用DelegatingFilterProxy从IOC中获取到FilterChainProxy实例，bean name默认为springSecurityFilterChain。然后调用其doFilter()方法
- FilterChainProxy中核心是维护了一个List\<SecurityFilterChain\>，每一个SecurityFilterChain（真正类型是DefaultSecurityFilterChain）维护一个RequestMatcher和List\<Filter\>，这个在FilterChainProxy初始化的时候已经设置好了，在HttpSecurityBeanDefinitionParser的createFilterChain()中使用HttpConfigurationBuilder和AuthenticationConfigBuilder分别创建相应的Filter，并且会按照SecurityFilters中的顺序排序，注解方式是在WebSecurity的performBuild()方法中。

### WebSecurity

- WebSecurity是由WebSecurityConfiguration创建，主要是为了创建FilterChainProxy对象。在WebSecurityConfiguration的springSecurityFilterChain()就会创建返回FilterChainProxy对象，具体是先会调用AutowiredWebSecurityConfigurersIgnoreParents的getWebSecurityConfigurers()获取到IOC中所有WebSecurityConfigurer对象，并将其设置到WebSecurityConfiguration中维护的WebSecurity对象中，当springSecurityFilterChain()创建Bean时会调用该WebSecurity对象的build()方法创建FilterChainProxy对象。build()核心逻辑中会调用AbstractConfiguredSecurityBuilder的doBuild()，这个方法中会依次调用init()、configure()、performBuild()，其中init()会获取前面所有apply到WebSecurity对象中的SecurityConfigurer，并调用他们的init()方法，configure()会获取前面所有apply到WebSecurity对象中的SecurityConfigurer，并调用他们的configure()方法，performBuild()才是真正执行创建逻辑。performBuild()分别被AuthenticationManagerBuilder、WebSecurity、HttpSecurity实现，其中AuthenticationManagerBuilder的实现主要是为了创建ProviderManager对象（AuthenticationManager），WebSecurity是为了创建FilterChainProxy对象，HttpSecurity是为了创建DefaultSecurityFilterChain对象。WebSecurityConfigurerAdapter本质上也是一个SecurityConfigurer，而在其init()方法中会获取到配置好后的HttpSecurity，并将其应用到WebSecurity中，参与FilterChainProxy的创建。

### HttpSecurity

- HttpSecurity是用来配置对于http请求的权限控制。HttpSecurity是WebSecurity配置的一部分，最终会放入WebSecurity。HttpSecurity的配置参考AbstractHttpConfigurer的子类

### WebSecurityConfigurer和WebSecurityConfigurerAdapter

#### configure(AuthenticationManagerBuilder)

- 用于通过允许轻松添加AuthenticationProviders来建立身份验证机制：例如，以下内容定义了具有内置“用户”和“管理员”登录名的内存中身份验证

  ```java
  public void configure(AuthenticationManagerBuilder auth) {
      auth
          .inMemoryAuthentication()
          .withUser("user")
          .password("password")
          .roles("USER")
      .and()
          .withUser("admin")
          .password("password")
          .roles("ADMIN","USER");
  }
  ```

  

#### configure(WebSecurity)

- 用于影响全局安全性的配置设置（忽略资源，设置调试模式，通过实现自定义防火墙定义拒绝请求）。例如，以下方法将导致以/ resources /开头的任何请求都被忽略，以进行身份验证。

  ```java
  public void configure(WebSecurity web) throws Exception {
      web
          .ignoring()
          .antMatchers("/resources/**");
  }
  ```

  

#### configure(HttpSecurity)

- 允许基于选择匹配在资源级别配置基于Web的安全性-例如，以下示例将以/ admin /开头的URL限制为具有ADMIN角色的用户，并声明需要使用其他任何URL成功认证。

  ```java
  protected void configure(HttpSecurity http) throws Exception {
      http
          .authorizeRequests()
          .antMatchers("/admin/**").hasRole("ADMIN")
          .anyRequest().authenticated()
  }
  ```



### AuthenticationManager

- 默认实现是ProviderManager，核心是维护了一个List\<AuthenticationProvider\>，每个AuthenticationProvider代表一种认证方式，表单登录方式默认的实现是DaoAuthenticationProvider类型。DaoAuthenticationProvider中核心维护着一个PasswordEncoder和一个UserDetailsService，使用UserDetailsService获取UserDetails，并使用指定的PasswordEncoder将用户提交的Authentication和UserDetails比较，如果通过则认证通过。

### SecurityMetadataSource

- 代表访问规则抽象

## 认证流程

- 当HttpSecurity开启formLogin()功能后会创建FormLoginConfigurer，其会自动创建UsernamePasswordAuthenticationFilter，该Filter会默认从请求参数中获取username和password，创建UsernamePasswordAuthenticationToken，交给AuthenticationManager的authenticate()认证。默认的AuthenticationManager类型为ProviderManager，当在WebSecurityConfigurerAdapter的getHttp()获取HttpSecurity时，会通过authenticationManager()返回AuthenticationManager对象，而该方法最终会通过AuthenticationManagerBuilder的build()方法，最终在AuthenticationManagerBuilder的performBuild()返回ProviderManager对象。
- SecurityContextHolder将认证通过的Authentication对象放到线程上下文中，方便获取。

## 授权流程

### Web授权

- Web授权流程的起点是从http.authorizeRequests() 开始。authorizeRequests()会创建ExpressionUrlAuthorizationConfigurer，ExpressionUrlAuthorizationConfigurer的configure()会创建一个FilterSecurityInterceptor对象，FilterSecurityInterceptor本质是一个Filter，所以请求会被过滤，核心的逻辑在AbstractSecurityInterceptor的beforeInvocation()中，通过obtainSecurityMetadataSource()获取FilterSecurityInterceptor持有的SecurityMetadataSource对象（实际类型是ExpressionBasedFilterInvocationSecurityMetadataSource，在ExpressionUrlAuthorizationConfigurer创建FilterSecurityInterceptor时创建的），然后将与当前请求匹配的ConfigAttribute集合返回，委托AccessDecisionManager的decide()进行授权决策，默认的AccessDecisionManager实现类是AffirmativeBased。

### 方法授权

- 方法授权起点在于GlobalMethodSecurityConfiguration，这个配置类中会创建方法授权的核心MethodInterceptor，具体实现根据代理模式不同创建为AspectJMethodSecurityInterceptor或者MethodSecurityInterceptor。以MethodSecurityInterceptor为例，创建的时候会设置默认的AccessDecisionManager（默认类型为AffirmativeBased），注入MethodSecurityMetadataSource，MethodSecurityMetadataSource是根据@EnableGlobalMethodSecurity注解上的属性配置创建对应的MethodSecurityMetadataSource实现类。拦截的核心逻辑在AbstractSecurityInterceptor的beforeInvocation()中，主要是通过AOP技术拦截指定方法，并获取Collection\<ConfigAttribute\>，然后委托给AccessDecisionManager进行授权决策。

## 常用的Filter

### UsernamePasswordAuthenticationFilter

### ExceptionTranslationFilter

- 先通过获取异常堆栈中所有异常的cause，并在其中查找AuthenticationException和AccessDeniedException，这一步主要是为了防止这两个异常被包装后导致无法catch到，其中AuthenticationException是认证阶段的异常，AccessDeniedException是授权阶段的异常。主要异常处理逻辑在handleSpringSecurityException()，用来处理AuthenticationException和AccessDeniedException。如果是AuthenticationException，则会直接将其委托给AuthenticationEntryPoint的commence()处理。如果是AccessDeniedException，判断是否是Anonymous或者RememberMe，是则直接将其委托给AuthenticationEntryPoint的commence()处理，否则会委托给AccessDeniedHandler处理。
