## 模板方法模式
- 定义一个操作中的算法骨架，而将算法的一些步骤延迟到子类中，使得子类可以不改变该算法结构的情况下重定义该算法的某些特定步骤。

- 应用场景
  
  - 实现一个算法时，整体步骤很固定。但是，某些部分易变。易变部分可以抽象成出来，供子类实现。
  
- 代码实现

  ```java
  //抽象类
  abstract class AbstractClass{
      public void TemplateMethod(){
          SpecificMethod();
          abstractMethod1();          
          abstractMethod2();
      }  
      public void SpecificMethod(){
          System.out.println("抽象类中的具体方法被调用...");
      }   
      public abstract void abstractMethod1(); //抽象方法1
      public abstract void abstractMethod2(); //抽象方法2
  }
  //具体子类
  class ConcreteClass extends AbstractClass{
      public void abstractMethod1(){
          System.out.println("抽象方法1的实现被调用...");
      }   
      public void abstractMethod2(){
          System.out.println("抽象方法2的实现被调用...");
      }
  }
  ```

  