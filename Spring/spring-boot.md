# SpringBoot运行机制

- SpringBoot应用的启动和自动配置主要包括两部分：@SpringBootApplication和SpringApplication#run()

## @SpringBootApplication

- 主要由@SpringBootConfiguration、@EnableAutoConfiguration、@ComponentScan组成，其中@EnableAutoConfiguration是核心，@EnableAutoConfiguration又包含了@AutoConfigurationPackage和@Import(AutoConfigurationImportSelector.class)

### @AutoConfigurationPackage

- 会import AutoConfigurationPackages.Registrar实例，该类的主要作用是获取到扫描的包名，默认为@AutoConfigurationPackage所在的类包以及子包，也就是@SpringBootApplication所在的主启动类，并将其注册到IOC中，为后面@ComponentScan加载组件做准备

### AutoConfigurationImportSelector

- AutoConfigurationImportSelector是一个Bean注入选择器，其selectImports()方法用来确定最终向IOC中注入的Bean。

- 首先会通过spring.boot.enableautoconfiguration=true检查自动配置是否开启，默认开启
- 加载spring-boot-autoconfigure包下META-INF/spring-autoconfigure-metadata.properties中的内容到PropertiesAutoConfigurationMetadata中。这个主要是为过滤后面候选配置类是否满足加载条件做准备的，新版本已经删除该逻辑
- 使用SpringFactoriesLoader加载META-INF/spring.factories文件中所有以org.springframework.boot.autoconfigure.EnableAutoConfiguration为key的自动配置类，并去重。获取@EnableAutoConfiguration的exclude和excludeName属性，并排除指定配置类。获取org.springframework.boot.autoconfigure.AutoConfigurationImportFilter为key的过滤类，过滤不满足加载条件的配置类。
- 返回最终需要导入的配置类，并将其导入IOC

## SpringApplication

封装一个Spring应用启动的流程。

- main()方法中SpringApplication.run()方法，会创建一个SpringApplication实例，并在其构造器中，会通过classpath的依赖推断其WebApplicationType，通过SpringFactoriesLoader加载spring-boot包下META-INF/spring.factories文件中定义的ApplicationContextInitializer和ApplicationListener，通过堆栈信息推断主启动类。最终会调用SpringApplication的run(String... args)方法。
- 通过SpringFactoriesLoader加载spring-boot包下META-INF/spring.factories文件中的SpringApplicationRunListener，并通过反射创建对象。
- SpringApplicationRunListener.starting()异步或者同步广播"开始"消息。
- 通过SimpleCommandLineArgsParser解析命令行参数，并返回ApplicationArguments。
- 根据推断的WebApplicationType创建不同的ConfigurableEnvironment，配置ConfigurableEnvironment。
- SpringApplicationRunListener.environmentPrepared()异步或者同步广播"环境已准备"消息。
- 将ConfigurableEnvironment绑定到SpringApplication中。
- 打印Banner信息。
- 通过SpringFactoriesLoader加载spring-boot包下META-INF/spring.factories文件中的SpringBootExceptionReporter。
- 创建ApplicationContext，并为其做一些PostProcess配置。
- **调用AbstractApplicationContext的refresh()。（Application Context初始化 重点）**
  - prepareRefresh()做一些Context初始化的操作，比如设置一些状态值，初始化参数，设置时间等。
  - 获取并设置ConfigurableListableBeanFactory。
  - postProcessBeanFactory()扩展点。
  - 获取并调用所有的BeanFactoryPostProcessor。
  - registerBeanPostProcessors()注册BeanPostProcessor。
  - initMessageSource()初始化MessageSource。
  - initApplicationEventMulticaster()初始化ApplicationEventMulticaster用于事件发布。
  - onRefresh()模板方法由各子类实现。其中对于Web应用来说，会被ServletWebServerApplicationContext实现，其onRefresh()主要是会通过createWebServer()创建Web容器。createWebServer()会通过ServletWebServerFactory.getWebServer()创建Web容器。默认SpringBoot内置Jetty、Tomcat和Undertow。详见TomcatServletWebServerFactory.getWebServer()。
  - registerListeners()注册所有的ApplicationListener。
  - finishBeanFactoryInitialization()初始化完成所有剩余的单例Bean，这里面会完成了Bean整个生命周期。**（重点）**
  - finishRefresh()完成容器初始化。
- registerShutdownHook注册JVM关闭的回调，主要是用来做Context关闭操作。
- afterRefresh用于IOC初始化完毕后的扩展。
- SpringApplicationRunListener.started()异步或者同步广播"容器已启动"消息。
- 从IOC中获取所有的ApplicationRunner或者CommandLineRunner实例，并调用其run()方法。
- 整个SpringBoot应用启动完毕。



