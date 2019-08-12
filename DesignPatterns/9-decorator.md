## 装饰者模式
- 在不改变现有对象结构的情况下，动态地给该对象增加一些职责（即增加其额外功能）。
  
- 优点
  
  - 采用装饰模式扩展对象的功能比采用继承方式更加灵活。
  - 可以设计出多个不同的具体装饰类，创造出多个不同行为的组合。
  
- 缺点
  
  - 容易造成子类膨胀。
  
- 角色
  
  - 抽象组件，真实对象和装饰对象的共同接口。
  - 真实对象
  - 抽象装饰对象，装饰对象中持有真实对象引用，从而控制在真实对象调用前后增加新的功能。
  
  - 具体装饰对象，继承自装饰对象，负责给真实对象增加新功能。根据不同增强功能可能会有多个不同的具体装饰对象。
  
- 应用场景：
  - io流设计
  - Servlet API 中提供了一个request对象的Decorator设计模式的默认实现类HttpServletRequestWrapper，HttpServletRequestWrapper类，增强了request对象的功能。
  
- 代码实现

  ```java
  public interface Component {
      void operate();
  }
  
  public class Target implements Component{
      @Override
      public void operate() {
          System.out.println("调用真实对象方法。");
      }
  }
  
  public abstract class Decorator implements Component{
      private Component component;
      public Decorator(Component component) {
          this.component = component;
      }
      @Override
      public void operate() {
          component.operate();
      }
  }
  
  public class ConcreteDecorator extends Decorator{
      public ConcreteDecorator(Component component) {
          super(component);
      }
      @Override
      public void operate() {
          super.operate();
          newFunction();
      }
      public void newFunction(){
          System.out.println("增加新功能。");
      }
  }
  
  public class Client {
      public static void main(String[] args) {
          Component target = new Target();
          target.operate();
        	System.out.println("---装饰前---");
          Component wrapper = new ConcreteDecorator(target);
          wrapper.operate();
      }
  }
  
  //输出：
  调用真实对象方法。
  ---装饰前---
  调用真实对象方法。
  增加新功能。
  
  ```
  
  