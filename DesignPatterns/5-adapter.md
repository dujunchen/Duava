## 适配器模式
- 将一个类的接口转换成客户希望的另外一个接口，使得原本由于接口不兼容而不能一起工作的那些类能一起工作。适配器模式可以有两种实现方式：**类结构和对象结构**。因为java是单继承的，并且根据组合聚合复用原则，优先使用组合完成对象复用，所以优先使用对象结构实现。
  
- 优点
  
  - 客户端通过适配器可以透明地调用目标接口。
  - 复用了现存的类，程序员不需要修改原有代码而重用现有的适配者类。
  - 将目标类和适配者类解耦，解决了目标类和适配者类接口不一致的问题。
  
- 缺点
  
  - 更换适配器的实现过程比较复杂。
  
- 模式中的角色
  
  - 目标接口（Target）：当前需要使用的接口规范
  - 需要适配的类（Adaptee）：现有组件库中不兼容的老接口
  - 适配器（Adapter）
  
- 应用场景
  
  - 以前开发的系统存在满足新系统功能需求的类，但其接口同新系统的接口不一致。
  - 使用第三方提供的组件，但组件接口定义和自己要求的接口定义不同。
  
  - JDK IO转换流`java.io.InputStreamReader(InputStream)`和`java.io.OutputStreamWriter(OutputStream)`。
  
- 代码实现
  
  - 类结构适配器
  
  ```java
  public interface Target {
      void request();
  }

  public class Adaptee {
      public void specificRequest()
      {
          System.out.println("现有组件库中的代码被调用！");
      }
  }
  //类结构适配器是采用继承方式实现的
  public class ClassAdapter extends Adaptee implements Target{
      @Override
      public void request() {
          specificRequest();
      }
  }

  public class ClassAdapterTest {
      public static void main(String[] args) {
          new ClassAdapter().request();
      }
  }
  ```
  
  - 对象结构适配器
  
  ```java
  //对象结构适配器是采用组合方式实现的
  public class ObjectAdapter implements Target{
   private Adaptee adaptee;
      public ObjectAdapter(Adaptee adaptee) {
          this.adaptee = adaptee;
      }
      @Override
      public void request() {
          adaptee.specificRequest();
      }
  }
  public class ObjectAdapterTest {
      public static void main(String[] args) {
          new ObjectAdapter(new Adaptee()).request();
      }
  }
  ```
  
  
  
  - 双向适配
  
  ```java
  //Target接口和Adaptee和上面相同
  public class TargetImpl implements Target{
      @Override
      public void request() {
          System.out.println("目标接口被调用！");
      }
  }
  
  public class DoubleAdapter extends Adaptee implements Target{
      private Adaptee adaptee;
      private Target target;
  
      public DoubleAdapter(Adaptee adaptee) {
          this.adaptee = adaptee;
      }
  
      public DoubleAdapter(Target target) {
          this.target = target;
      }
  
      @Override
      public void request() {
          adaptee.specificRequest();
      }
  
      @Override
      public void specificRequest() {
          target.request();
      }
  }
  
  public class DoubleAdapterTest {
      public static void main(String[] args) {
          //用新的接口规范的调用方式去调用现有组件库（老的接口）的方法
         	// new DoubleAdapter(new Adaptee()).request();
          //用现有组件库（老的接口）规范的调用方式去调用新的接口实现
          new DoubleAdapter(new TargetImpl()).specificRequest();
      }
  }
  
  ```
  
  