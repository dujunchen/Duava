## 建造者模式
- 将一个复杂的对象分解为多个简单的对象，然后一步一步构建而成，实现了对象子组件的构建和装配的解耦。不同的构建器，相同的装配，也可以做出不同的对象；相同的构建器，不同的装配顺序也可以做出不同的对象。也就是实现了构建算法和装配算法的解耦，实现了更好的复用。

- 优点
  
  - 各个具体的建造者相互独立，有利于系统的扩展。
  - 客户端不必知道产品内部组成的细节，便于控制细节风险。
  
- 缺点
  
  - 产品的组成部分必须相同，这限制了其使用范围。
  - 如果产品的内部变化复杂，该模式会增加很多的建造者类。
  
- 开发中应用场景
  
  - StringBuilder类的append方法
  - SQL中的PreparedStatement
  - JDOM中， DomBuilder、 SAXBuilder
  
- 实现方法

  ```java
  public class Computer {
      private String CPU;
      private String HD;
      private String Mainboard;
  
      public void show(){
          System.out.println(this.CPU + ":" + this.HD + ":" + Mainboard);
      }
  }
  
  public abstract class ComputerBuilder {
      protected Computer computer = new Computer();
  
      abstract void buildCPU();
  
      abstract void buildMainboard();
  
      abstract void buildHD();
  
      abstract Computer getComputer();
  }
  
  public class AComputerBuilder extends ComputerBuilder{
      @Override
      public void buildCPU() {
          computer.setCPU("酷睿i7");
      }
  
      @Override
      public void buildMainboard() {
          computer.setMainboard("罗技键盘");
      }
  
      @Override
      public void buildHD() {
          computer.setHD("三星屏幕");
      }
  
      @Override
      public Computer getComputer() {
          return computer;
      }
  }
  //指挥者，控制整个构建的流程
  public class Director {
      private ComputerBuilder builder;
      public Director(ComputerBuilder builder){
          this.builder = builder;
      }
      public Computer construct(){
          builder.buildCPU();
          builder.buildHD();
          builder.buildMainboard();
          return builder.getComputer();
      }
  }
  
  public class BuilderTest {
      public static void main(String[] args) {
          Computer computer = new Director(new AComputerBuilder()).construct();
          computer.show();
      }
  }
  
  ```

  