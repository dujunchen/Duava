## 单例模式
- 特点
  保证一个类只有一个实例，并且这个实例必须由类自己创建，并且提供一个访问该实例的全局访问点。

- 常见的应用场景
  + Windows任务管理器、回收站、文件系统
  + 读取配置文件的类
  + 网站计数器
  + 数据库连接池
  + 线程池
  + Servlet中的application对象
  + Spring容器中的bean默认是单例模式
  + 在servlet编程中，每个Servlet也是单例
  + SpringMVC的Controller对象
  
- 优点
  + 由于单例模式只生成一个实例，减少了系统性能开销，当一个对象的产生需要比较多的资源时，如读取配置、产生其他依赖对象时，则可以通过在应用启动时直接产生一个单例对象，然后永久驻留内存的方式来解决。
  + 单例模式可以在系统设置全局的访问点，优化环共享资源访问，例如可以设计一个单例类，负责所有数据表的映射处理。
  
- 实现方式

  - 饿汉式

  ```java
   /*
      饿汉式
    */
    public class HungrySingleton {
      private HungrySingleton() {
      }    
      //静态属性在类加载时就初始化，并且只会初始化一次。所以不会有并发问题。执行效率较高，但是 不能实现延迟加载。
      private static final HungrySingleton instance = new HungrySingleton();
  
      public static HungrySingleton getInstance() {
        return instance;
      }
    }
  ```

  

  - 懒汉式

  ```java
    /*
      懒汉式
    */
    public class LazySingleton {
      private LazySingleton() {
      }	
      //volatile保证多线程可见
      private static volatile LazySingleton instance = null;
      //会有并发问题，需要加synchronized,可以实现延迟加载，但是每次获取对象都需要同步，效率低
      public static synchronized LazySingleton getInstance() {
        if(instance == null){
          instance = new LazySingleton();
        }
        return instance;
      }
    }
  ```

  

  - 双重检测锁

  ```java
    //改进版 双重检测锁
    public class LazySingleton {
      private LazySingleton() {
      }	
      //volatile可以防止指令重排序不可省略，否则会有问题
      //因为instance = new LazySingleton()这行代码不是原子操作，在字节码层面会分成4个阶段：
      //	1.申请内存空间，
      //	2.初始化默认值（区别于构造器方法的初始化），
      //	3.执行构造器方法
      //	4.连接引用和实例。
      //不加volatile，3和4有可能发生指令重排序，导致返回的对象是一个未初始化完成的对象，导致后续运行错误。
      private static volatile LazySingleton instance = null;
      public static LazySingleton getInstance(){
        if(instance == null){	//只有instance==null的时候才需要进行同步，提高效率
          synchronized (LazySingleton.class) {
            if(instance == null)	//保证在高并发安全
            instance = new LazySingleton();
          }
        }
        return instance;
      }
    }
  ```

  

  - 静态内部类实现

  ```java
  	/*
      静态内部类实现懒汉式单例
    */
    public class StaticInnerSingleton {
      private StaticInnerSingleton() {
      }
      //外部类加载时，内部类并不会立刻加载。可以延迟加载。同时，static属性只能在类加载时加载一次，所以也不存在并发问题。
      private static class StaticInnerSingletonInstance {
        private static final StaticInnerSingleton instance = new StaticInnerSingleton();
      }
  
      public static StaticInnerSingleton getInstance() {
        return StaticInnerSingletonInstance.instance;
      }
    }
  ```

  

  - 枚举类实现

  ```java
  	/*
      枚举类实现单例
      枚举本身就是单例模式。由JVM从根本上提供保障！避免通过反射和反序列化的漏洞！
    */
      enum EnumSingletonInstance{
        INSTANCE;
        public static EnumSingletonInstance getInstance(){
      	return INSTANCE;
        }
      }
  ```

- 单例破解及解决方案

  上面各种单例的实现中，除了枚举实现，其余几种单例实现方式都可以用反射或者反序列化破解。

  1. 反射破解 

    以饿汉式为例，其余同理：

    ```java
    // 1.反射可以破解，通过Class.newInstance()调用无参构造器创建新对象。
      private static void test() {
        try {
          HungrySingleton obj = HungrySingleton.class.newInstance();
          HungrySingleton obj2 = HungrySingleton.class.newInstance();
          System.out.println(obj.toString());
          System.out.println(obj2.toString());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    ```
    
    解决方法：

    ```java
    //解决方法：可以在构造器中手动抛出异常以防止反射破解单例。
      private HungrySingleton() {
        if (instance != null) {
          try {
            throw new Exception("只能创建一个对象！");
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    ```

  

  2. 反序列化破解

    ```java
  	// 2.反序列化可以破解
     	private static void test() {
     		HungrySingleton obj = HungrySingleton.getInstance();
     		try {
     			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:/1.txt"));
     			oos.writeObject(obj);
     			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("D:/1.txt"));
     			HungrySingleton obj2 = (HungrySingleton) ois.readObject();
     			System.out.println(obj.toString());
     			System.out.println(obj2.toString());
     		} catch (Exception e) {
     			e.printStackTrace();
     		}
     	}
    ```
    
     解决方法：
    
  ```java
     	//解决方法：在单例类中增加readResolve()方法，方法内返回我们自己指定要返回的对象。
       //当JVM从内存中反序列化地"组装"一个新对象时，就会自动调用这个 readResolve方法来返回我们指定好的对象了，单例规则也就得到了保证。
     	private Object readResolve() throws ObjectStreamException{
     		return instance;
     	}
  ```
  
     

- 具体选用原则：
  
  - 单例对象占用资源少，不需要延时加载：枚举式好于饿汉式  
- 单例对象占用资源大，需要延时加载：静态内部类式好于懒汉式