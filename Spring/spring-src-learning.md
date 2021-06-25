## IOC

### 核心组件

#### Resource

- 将一切资源抽象，典型的有ClassPathResource和FileSystemResource实现。

#### BeanDefinitionReader

- bean定义信息加载，主要有XmlBeanDefinitionReader实现。通过XmlBeanDefinitionReader的loadBeanDefinitions()方法解析加载xml的bean信息。主要逻辑有：

  - 将Resource包装为EncodedResource
  - 检测排除循环import的情况
  - 使用DefaultDocumentLoader加载XML信息
  - 使用DefaultBeanDefinitionDocumentReader注册bean信息
  - 使用BeanDefinitionParserDelegate解析获得BeanDefinitionHolder（BeanDefinition封装类）
  - 将BeanDefinitionHolder中的BeanDefinition和aliases注册到BeanDefinitionRegistry（BeanFactory）中



#### BeanFactory

- 主要有DefaultListableBeanFactory实现。主要持有属性ConcurrentHashMap beanDefinitionMap存储解析到的BeanDefinition。

### Bean注册流程

- 定义好Spring的配置文件
- 通过Resource将配置文件抽象成一个具体的Resource对象，并将其包装为EncodedResource
- 定义好将要使用的BeanFactory，定义好XmlBeanDefinitionReader，并将BeanFactory传入，XmlBeanDefinitionReader开始读取之前获取的Resource对象，流程开始解析。
- 使用DefaultDocumentLoader->DefaultBeanDefinitionDocumentReader->BeanDefinitionParserDelegate对XML中各种元素以及属性进行解析
- 解析完毕后返回一个BeanDefinitionHolder，该对象封装了BeanDefinition，以及beanName和alias，BeanFactory将BeanDefinition存入内部持有的ConcurrentHashMap beanDefinitionMap属性中
- 调用Bean解析完毕的触发动作，触发相应的Listener方法执行

### Bean创建流程

- 处理bean的name，去除FactoryBean前缀和处理别名
- 先查询单例缓存（singletonObjects属性，使用ConcurrentHashMap存储）中看是否有该bean，如果已经存在，则直接返回。（有可能是直接返回实例本身，也有可能是返回FactoryBean的getObject()返回结果）
- 如果存在parentBeanFactory，则调用parentBeanFactory.getBean()
- 标记该bean为已创建或者正在创建中，优化缓存
- 获取RootBeanDefinition，并检查是否是abstract的，如果是，则抛出异常

- 如果有depends on的bean，则先注册并初始化依赖的bean
- 根据配置的scope属性，确定当前创建bean的类型（singleton还是prototype），然后开始创建bean。无论是singleton还是prototype，底层创建的过程大致相同，都是先后调用createBean()和getObjectForBeanInstance()获得。不过，如果是singleton，会调用getSingleton()获取一个singleton实例，具体逻辑有：先从singletonObjects中查找是否存在该实例，如果已经存在，直接返回。否则调用createBean()创建bean实例，并在创建成功后调用addSingleton()将其缓存到singletonObjects中。
- createBean()的主要逻辑：判断是否注册有InstantiationAwareBeanPostProcessors，如果存在，调用其postProcessBeforeInstantiation()和postProcessAfterInitialization()方法处理bean，如果bean!=null，则直接返回该bean实例，而并不会继续往下执行流程。否则执行doCreateBean()方法创建bean实例。doCreateBean()方法主要逻辑：如果存在instanceSupplier，则通过instanceSupplier获取bean实例。如果存在factory method，则通过factory method创建bean实例，默认是反射调用无参构造器创建对象，并且将bean封装成BeanWrapper
- 如果存在MergedBeanDefinitionPostProcessor，调用其postProcessMergedBeanDefinition()修改merged bean definition
- 调用populateBean()对bean对象装配属性，根据注入方式（按照name还是type还是构造器注入）注入相关属性，同时对属性值作相应的类型转换
- 调用initializeBean()对bean进行初始化，主要逻辑：对于实现了Aware接口的bean，调用Aware接口中相应的方法。获取所有注册的BeanPostProcessor并调用其postProcessBeforeInitialization方法。如果实现InitializingBean接口，则调用其afterPropertiesSet()方法。如果定义init method，则通过反射调用自定义的init method。获取所有注册的BeanPostProcessor并调用其postProcessAfterInitialization方法
- 对于实现了DestructionAwareBeanPostProcessors，或者DisposableBean或者有自定义destroy method，将当前bean注册到Disposable bean的缓存中，当bean被销毁后将会回调
- getObjectForBeanInstance()的主要逻辑：如果bean name是以&开头，则返回对应的FactoryBean实例本身。如果该bean不是FactoryBean类型，则直接返回，否则通过调用FactoryBean的getObject()方法返回实例
- 对于getBean方法传入bean类型的参数的，如果类型不匹配，会调用TypeConverter（TypeConverter又会委托给ConversionService，ConversionService又会委托给GenericConverter）进行类型转换

## AOP

### ProxyFactoryBean

### Spring AOP实现原理

- 通过ProxyFactoryBean配置其target、interceptorNames、interfaces等生成代理对象所需要的信息
- 在整个ProxyFactoryBean实例构建和缓存过程中，流程和普通bean完全一致。只是当调用getObjectForBeanInstance()会判断是否是FactoryBean类型，如果是，先查找factoryBeanObjectCache是否有缓存，如果有直接返回，否则会调用ProxyFactoryBean的getObject()返回实例。（所以，Spring AOP是基于Spring IOC基础上实现的）ProxyFactoryBean的getObject()的主要逻辑：先根据配置初始化advisor (interceptor) chain，从BeanFactory中获取所有的Advisor。然后调用getSingletonInstance()返回代理对象，具体逻辑：先查看是否有缓存，如果有直接返回，否则根据不同的策略（JDK动态代理或者CGLIB）生成代理对象，返回客户端
- 当客户端调用目标对象的方法时，实际上是调用了代理对象中同名方法，而代理对象会将其委托给其持有的InvocationHandler的invoke()方法执行。以JDK动态代理为例，Spring会将其委托给JdkDynamicAopProxy的invoke()执行，主要逻辑：获取到所有生成动态代理对象所需要的信息后，构建ReflectiveMethodInvocation实例，并调用其proceed()方法。ReflectiveMethodInvocation的proceed()方法主要逻辑：获取到我们自定义的MethodInterceptor实现类，并调用其invoke()方法，通过反射执行Advice的逻辑，当在我们自定义的MethodInterceptor实现类中执行ReflectiveMethodInvocation的proceed()方法时，进入invokeJoinpoint()，使用反射调用目标对象中的方法

## TX

### PlatformTransactionManager

### Spring事务管理实现原理

- 通过TransactionProxyFactoryBean配置事务管理所需要的信息
- 在TransactionProxyFactoryBean创建和缓存流程也和普通的Bean完全相同，当实例创建初始化完毕后，会会返回TransactionProxyFactoryBean的getObject()。
- 当调用目标方法时，会委托给JdkDynamicAopProxy的invoke()执行。其中会执行ReflectiveMethodInvocation的proceed()方法，而proceed()会调用TransactionInterceptor的invoke()方法，TransactionInterceptor的invoke()方法中调用invokeWithinTransaction()给目标方法加上事务的逻辑。该方法中主要是委托给具体的PlatformTransactionManager实现类完成对事务的开启、提交或者回滚。

## 注解方式Bean注册流程

- 注解方式下的核心容器类是AnnotationConfigApplicationContext。当创建AnnotationConfigApplicationContext对象时，会创建一个DefaultListableBeanFactory，同时会初始化AnnotatedBeanDefinitionReader和ClassPathBeanDefinitionScanner对象。
- 创建AnnotatedBeanDefinitionReader时，会调用AnnotationConfigUtils.registerAnnotationConfigProcessors()，向beanDefinitionMap中注册跟注解解析相关的处理器的信息，其中最重要的是ConfigurationClassPostProcessor。
- 调用register()方法将自定义@Configuration类的BeanDefinition信息注册到beanDefinitionMap。
- 调用refresh()刷新容器。核心逻辑：invokeBeanFactoryPostProcessors中会执行所有的BeanFactoryPostProcessors，其中主要是ConfigurationClassPostProcessor，该处理器将会查找所有的@Configuration类，并且会递归解析里面定义的@Bean方法。调用ConfigurationClassBeanDefinitionReader的loadBeanDefinitions()加载并注册BeanDefinition信息。
- 按照优先级注册所有的BeanPostProcessor
- 调用finishBeanFactoryInitialization()创建剩余所有的singleton实例。主要逻辑：调用DefaultListableBeanFactory的preInstantiateSingletons()，里面主要会获取所有的BeanDefinition信息，调用DefaultListableBeanFactory的getBean()方法创建并初始化实例，接下去的逻辑跟传统XML方式配置没有任何区别

### 注解方式和XML方式区别

- 两种方式创建Bean时机不同。传统XML方式创建Bean实例是在第一次调用DefaultListableBeanFactory的getBean()方法时创建初始化并缓存在IOC中，而注解方式则是在refresh()的时候就已经创建好对象，并且缓存在IOC中，之后再调用DefaultListableBeanFactory的getBean()方法则直接返回之前缓存中的Bean。
- XML方式创建Bean的时候一般采用反射调用无参构造器创建。而注解方式创建Bean走的是工厂方法的分支。

## Spring Boot

### Spring Boot FatJar启动原理

- FatJar文件结构
  - BOOT-INF
    - classes	
      - 项目中的class文件
    - lib
      - 项目中依赖的jar包
  - META-INF
    - MANIFEST.MF
      - 主要指定了Start-Class和Main-Class
  - spring-boot-loader组件源代码
    - 因为根据Jar规范，只有Jar中顶层main方法才会被调用，嵌套的jar中的代码无非被App类加载器加载运行。spring-boot-loader代码主要是作为jar启动的入门程序，进而里面委托自定义类加载器对项目中的启动类进行加载启动
- Spring Boot FatJar启动主要依赖于spring-boot-loader组件。首先App类加载器加载org.springframework.boot.loader.JarLauncher，创建其对象并调用main()方法，JarLauncher构造器会调用父类的无参构造器，其中会获取到当前执行的jar包的完整位置，并创建Archive对象。JarLauncher的main()方法进而调用父类Launcher的launch方法，里面会调用getClassPathArchives()获取到所有BOOT-INF/classes/和BOOT-INF/lib/下所有的资源，并创建LaunchedURLClassLoader加载器，指定App类加载器作为其父类加载器，并将其设置为线程上下文类加载器。获取MANIFEST.MF中Start-Class，并使用线程上下文类加载器中的自定义类加载器将其加载到内存中，然后使用反射调用启动类中的main方法启动SpringBoot项目。

### JDWP

### SpringApplication.run()执行流程

- 创建SpringApplication对象，在构造器中主要逻辑：根据classpath中引入的依赖推断WebApplicationType，使用SpringFactoriesLoader加载位于classpath中META-INF/spring.factories中配置的所有组件，使用反射创建对应的对象。这边是加载所有的ApplicationContextInitializer和ApplicationListener对象。推断出当前项目中main()方法所在的启动类。
- 使用SpringFactoriesLoader加载所有的SpringApplicationRunListener，并且组装成SpringApplicationRunListeners对象。
- 通知starting()事件
- 解析启动参数，封装成ApplicationArguments对象
- 创建并且配置ConfigurableEnvironment对象
- 通知environmentPrepared()事件
- 打印banner信息
- 根据WebApplicationType创建对应的ConfigurableApplicationContext对象，并为其设置Environment，执行所有的ApplicationContextInitializer
- 通知contextPrepared()事件
- 打印启动信息和profile日志
- 向bean Factory 注册springApplicationArguments和springBootBanner实例，设置allowBeanDefinitionOverriding属性，加载其它一些自定义的外部资源
- 通知contextLoaded()事件
- refresh 容器并注册JVM关闭钩子函数，在refresh的时候，如果是Web应用，ServletWebServerApplicationContext重写了onRefresh()方法，里面会通过createWebServer()创建Web容器，主要逻辑：通过调用ServletWebServerFactory的getWebServer()方法创建Web容器，以Tomcat为例，TomcatServletWebServerFactory的getWebServer()-->prepareContext()-->configureContext()中会创建TomcatStarter对象，并且将其设置给Tomcat的Context。而TomcatStarter实现了Servlet3.0规范的ServletContainerInitializer，所以当Servlet3.0容器启动时，会自动调用其onStartup完成ServletContext的初始化
-  通知started()事件
- 获取所有的ApplicationRunner和CommandLineRunner，并调用其run()方法
- 通知running()事件
- ConfigurableApplicationContext创建完毕并返回

### Spring Boot和SpringMVC整合原理

#### Servlet 3.0规范

- Servlet3.0容器启动时会使用ServiceLoader对jar包中META-INF/services的文件进行加载（这种SPI方式加载要求加载的类中必须带有无参构造器），spring-web模块中存在META-INF/services/javax.servlet.ServletContainerInitializer文件，里面是Spring对其的实现SpringServletContainerInitializer。

#### SpringServletContainerInitializer

- 当Servlet3.0容器启动时，会调用javax.servlet.ServletContainerInitializer的onStartup()方法
- SpringServletContainerInitializer并不会真正的完成对ServletContext的初始化工作。而是会委托给所有的WebApplicationInitializer实现类去完成
- SpringServletContainerInitializer使用@HandlesTypes指定了WebApplicationInitializer，Servlet3.0容器启动时将会自动从classpath下扫描加载WebApplicationInitializer的实现类，并传给SpringServletContainerInitializer的onStartup()方法

#### Spring Boot初始化Web容器

- 传统SpringMVC采用SpringServletContainerInitializer初始化Web容器，而对应Spring Boot应用并未使用SpringServletContainerInitializer进行容器初始化，而是使用了TomcatStarter。但是TomcatStarter因为三点原因使得它无法使用SPI机制进行初始化，1、没有不带参数的构造器。2、类的声明不是public。3.所在jar包下并不存在META-INF/services/javax.servlet.ServletContainerInitializer文件。TomcatStarter是由Spring Boot启动容器时手动创建出来的，然后会遍历调用持有的所有的ServletContextInitializer的onStartup()方法完成初始化工作，在ServletContextInitializer的onStartup()方法会使用Servlet 3.0的API（ServletContext的addServlet和addFilter和addListener方法）完成serlvet组件的配置
- 传统的SpringMVC应用依赖于外部Servlet容器初始化，这种部署方式符合Servlet规范，所以可以使用Servlet3.0的SPI机制加载META-INF/services/javax.servlet.ServletContainerInitializer文件（SpringMVC具体实现为SpringServletContainerInitializer）对容器进行初始化。但是SpringBoot还可以采用java -jar启动内置Web容器完成应用部署，这样就无法使用标准的Servlet3.0规范实现加载，必须有SpringBoot框架本身完成手动创建SpringServletContainerInitializer并初始化

#### ServletRegistrationBean和FilterRegistrationBean

### Spring Boot属性注入

#### BeanPostProcessor和BeanFactoryPostProcessor

- BeanPostProcessor主要作用是Spring在创建bean和装配完毕后，但是在对bean初始化（所谓的初始化是指调用InitializingBean's afterPropertiesSet或者自定义init-method）之前，会对bean执行一系列自定义的逻辑。BeanPostProcessor面对的主体是bean实例
- BeanFactoryPostProcessor主要作用是在bean Factory已经加载所有的bean definition，但是还没有开始创建任何bean实例的时候，对bean definition执行自定义逻辑。BeanFactoryPostProcessor面对的主体是bean definition

#### AutowiredAnnotationBeanPostProcessor

### Eureka

#### 核心概念

- 服务注册
- 服务续约
- 服务同步
- 客户端从注册中心获取服务列表
- 服务调用
- 服务离开
- 服务剔除
- 自我保护机制

#### 核心原理

##### DiscoveryClient

### Ribbon

#### 核心功能

- 维护各个节点的IP等信息
- 根据特定逻辑选取节点

#### 模块

- Rule
- Ping
- ServerList

#### 核心原理

##### LoadBalancerClient

- LoadBalancerClient是Ribbon实现负载均衡的核心类。LoadBalancerClient具体交给ILoadBalancer来处理，ILoadBalancer通过配置IRule和IPing，向EurekaClient获取注册列表，默认每十秒向Eureka Client发送一个ping，进而检查是否需要更新服务的注册列表。得到注册列表后，ILoadBalancer根据IRule策略进行负载均衡。
- 使用RestTemplate增加@LoadBalance注解后，使用RestTemplate的拦截器（LoadBalancerInterceptor），在请求具体达到目标前，使用Ribbon的LoadBalancerClient去处理，从而实现负载均衡。



### Feign

#### 实现原理

