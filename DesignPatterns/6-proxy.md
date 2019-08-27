## 代理模式
  - 由于某些原因需要给某对象提供一个代理以控制对该对象的访问。这时，访问对象不适合或者不能直接引用目标对象，代理对象作为访问对象和目标对象之间的中介。
    
  - 优点
    
    - 代理对象在客户端与目标对象之间起到一个中介作用和保护目标对象的作用。
    - 可以详细控制访问某个（某类）对象的方法，在调用这个方法前做前置处理，调用这个方法后做后置处理。（即AOP的微观实现）
    - 将客户端与目标对象分离，在一定程度上降低了系统的耦合度。
    
  - 缺点
    
    - 在客户端和目标对象之间增加一个代理对象，会造成请求处理速度变慢。
    - 增加了系统的复杂度。
    
  - 代理模式和继承，装饰者模式相比
    
    - 继承
      - 被增强的对象和增强内容都没法改变。
    - 装饰者模式 
    
      - 被增强对象可以改变，增强内容没法变。
    - 动态代理
    
      - 被增强的对象和增强内容都可以改变。
    
  - 角色
    - 抽象角色
    - 代理角色
    - 真实角色
    
  - 分类
    - 静态代理
      - 缺点
        - 真实主题与代理主题一一对应，增加真实主题也要增加代理。
        - 设计代理以前真实主题必须事先存在，不太灵活。
      
    - 代码实现
      
        ```java
         public interface Star {
            //面谈
            void confer();
            //签合同
            void signContract();
            //订票
            void bookTicket();
            //唱歌
            void sing();
            //收钱
            void collectMoney();
          }
        
          public class RealStar implements Star {
        
            @Override
            public void bookTicket() {
            	System.out.println("RealStar.bookTicket()");
            }
        
            @Override
            public void collectMoney() {
            	System.out.println("RealStar.collectMoney()");
            }
        
            @Override
            public void confer() {
            	System.out.println("RealStar.confer()");
            }
        
            @Override
            public void signContract() {
            	System.out.println("RealStar.signContract()");
            }
        
            @Override
            public void sing() {
            	System.out.println("RealStar(周杰伦本人).sing()");
            }
          }
        
          public class ProxyStar implements Star {
        
            private Star star;
        
            public ProxyStar(Star star) {
              super();
              this.star = star;
            }
        
            @Override
            public void bookTicket() {
            	System.out.println("ProxyStar.bookTicket()");
            }
        
            @Override
            public void collectMoney() {
            	System.out.println("ProxyStar.collectMoney()");
            }
        
        
        
            @Override
            public void confer() {
            	System.out.println("ProxyStar.confer()");
            }
        
            @Override
            public void signContract() {
            	System.out.println("ProxyStar.signContract()");
            }
        
            @Override
            public void sing() {
            	star.sing();
            }
          }
        ```
      
        
      
    - 动态代理 （**[动态代理原理前往专题阅读](../SourceCodeAnalyse/deep-analysis-of-jdk-dynamic-proxy/Java动态代理模式的奥秘.md)**） 
      
      - 实现方式：jdk自带、javassist、CGlib、 Asm都可以动态生成代理类。
      - 比静态代理的优势：可以自动生成代理类，抽象角色中(接口)声明的所有方法都被转移到调用处理器一个集中的方法InvokeHandler.invoke()中处理，这样，我们可以更加灵活和统一的处理众多的方法。
      - jdk自带动态代理
    
    ```java
     public interface Star {
            
        	int bookTicket(String p);
        	
        	void sing();
        	//其余方法略
        }
    public class RealStar implements Star {
    
            @Override
            public int bookTicket(String p) {
            	System.out.println("RealStar.bookTicket()");
            	return Integer.parseInt(p);
            }
            
            @Override
            public void sing() {
            	System.out.println("RealStar(周杰伦本人).sing()");
            }
        }
        
    public class Client {    
      public static void main(String[] args) {
        final Star real = new RealStar();
        Star proxy = (Star)Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[] { Star.class }, new InvocationHandler() {
          @Override
          //代理对象所实现接口中的所有方法被调用时，都会委托给InvocationHandler.invoke()方法。  
          //InvocationHandler.invoke()方法返回结果就是调用真实角色中对应方法的返回结果
          //proxy为自动生成的代理对象，method为当前调用真实角色中的方法，args为该方法的参数列表
       		//该方法返回值就是真实角色中的该方法的返回值
          public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println(proxy.getClass());	//class com.sun.proxy.$Proxy0
            System.out.println(method.getName());	//bookTicket
            System.out.println(Arrays.toString(args));//[200]
            Object res = method.invoke(real, args);//代理会转发给真实对象调用该方法
            System.out.println(res);//200
            return res;
          }
        });
        proxy.sing();
        System.out.println("-------");
        Object res = proxy.bookTicket("200");
        System.out.println("-------");
        System.out.println(res);
      }
    }
    ```
    
      - 代理工厂
    
    ```java
    // 代理工厂类（创建代理对象）
    public class ProxyFactory {
      private Object target; // 目标对象
      private BeforeAdvice beforeAdvice;//前置通知
      private AfterAdvice afterAdvice;//后置通知
      
    	//创建代理对象
      public Object createProxy() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        Class<?>[] interfaces = this.target.getClass().getInterfaces();
        InvocationHandler ih = new InvocationHandler() {    
          @Override
          public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (beforeAdvice != null)
              beforeAdvice.before();
            Object result = method.invoke(target, args);
            if (afterAdvice != null)
              afterAdvice.after();
            return result;
          }
        };
        Object proxyInstance = Proxy.newProxyInstance(classLoader, interfaces, ih);
        return proxyInstance;
      }
      //省略所有的setter、getter
    }
    
    //前置通知接口
    public interface BeforeAdvice {
      void before();
    }
    
    //后置通知接口
    public interface AfterAdvice {
      void after();
    }
    
    //目标对象实现的接口
    public interface Waiter {
      void serve();
      void shouQian();
    }
    
    //目标对象类（需要被增强的对象）
    public class ManWaiter implements Waiter {
      @Override
      public void serve() {
        System.out.println("正在服务中。。。");
      }
      @Override
      public void shouQian() {
        System.out.println("收钱。。。");
      }
    }
    
    //测试类
    public class FactoryTest {
      public static void main(String[] args) {
        ProxyFactory factory = new ProxyFactory();
        //设置需要增强目标对象
        factory.setTarget(new ManWaiter());	
        //设置前置通知
        factory.setBeforeAdvice(new BeforeAdvice() {	
          public void before() {
            System.out.println("欢迎...");
          }
        });
        //设置后置通知
        factory.setAfterAdvice(new AfterAdvice() {
          public void after() {
            System.out.println("再见...");
          }
        });
        Waiter proxy = (Waiter) factory.createProxy();
        proxy.serve();
      }
    }
    
    //运行结果：
    欢迎...
    正在服务中。。。
    再见...
    ```
    
  - 应用场景
    - struts2中拦截器的实现
    - 数据库连接池关闭处理
    - Hibernate中延时加载的实现
    - mybatis中实现拦截器插件
    - **AspectJ**的实现
    - spring中**AOP**的实现
    - 日志拦截
    - 声明式事务处理
    - web service
    - RMI远程方法调用