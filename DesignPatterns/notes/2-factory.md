## 工厂模式
- 实现了对象的创建者和使用者的分离。
- 分类
  - 简单工厂模式 （不属于GOF23）
    也叫静态工厂模式。通过传入的参数不同返回不同类型的对象。要增加新产品需要修改源代码。不符合开闭原则。
  - 工厂方法模式  
    在简单工厂模式基础上进一步抽象，将工厂类设计成抽象类或接口，不同的产品实现各自的工厂实体类，创建对象时，只需要提供对应的产品类和该产品的工厂类即可，不需要知道内部创建过程。当需要增加新产品，只需要提供新产品的类型和对应的工厂类，不需要修改原有代码，符合开闭原则。但是每个产品对应一个工厂类，会造成类数量膨胀，不好维护管理。
  - 抽象工厂模式  
    - 用来生产不同产品族的全部产品。当系统中每一个产品族都只生产一种产品时，抽象工厂模式将退化到工厂方法模式。
    - 当增加一个新的产品族时只需增加一个新的具体工厂，不需要修改原代码，满足开闭原则。当产品族中需要增加一个新种类的产品时，则所有的工厂类都需要进行修改，不满足开闭原则。
- 代码实现

  - 简单工厂模式（只需要一个工厂类）

  ```java
  /*简单工厂模式 根据不同的参数构建不同的对象
   */
  public class SimpleFactory {
  	public static  Car createCar(String type){
  		if("奥迪".equals(type)){
  			return new Audi();
  		}else if("比亚迪".equals(type)){
  			return new Byd();
  		}else{
  			return null;
  		}
  	}
  	//或者
  	public static  Car createAudi(){
  		return new Audi();
  	}
  	public static  Car createByd(){
  		return new Byd();
  	}
  }
  ```

  

  - 工厂方法模式（每个产品对应一个工厂实现类）

  ```java
  /*工厂方法模式 将工厂抽象，每个产品对应一个工厂实现类
   */
  public interface CarFactory {
  	Car createCar();
  }
  public class AudiFactory implements CarFactory {
  	@Override
  	public Car createCar() {
  		return new Audi();
  	}
  }
  public class BydFactory implements CarFactory {
  	@Override
  	public Car createCar() {
  		return new Byd();
  	}
  }
  ```

  

  - 抽象工厂模式（每个产品族对应一个工厂实现类）

  ```java
  public interface Seat {
  	void massage();
  }
  
  class LuxurySeat implements Seat {
  	@Override
  	public void massage() {
  		System.out.println("可以自动按摩！");
  	}
  }
  
  class LowSeat implements Seat {
  	@Override
  	public void massage() {
  		System.out.println("不能按摩！");
  	}
  }
  
  public interface Engine {
  	void run();
  	void start();
  }
  
  class LuxuryEngine implements Engine{
  	@Override
  	public void run() {
  		System.out.println("转的快！");
  	}
  	@Override
  	public void start() {
  		System.out.println("启动快!可以自动启停！");
  	}
  }
  
  class LowEngine implements Engine{	
  	@Override
  	public void run() {
  		System.out.println("转的慢！");
  	}	
  	@Override
  	public void start() {
  		System.out.println("启动慢!");
  	}
  }
  
  //抽象工厂，用来生产产品族。如果新增一个产品，则需要修改该接口增加相应的创建方法，导致所有实现的工厂类也全部要修改，不符合开闭原则。
  public interface CarFactory {
  	Engine createEngine();
  	Seat createSeat();
  }
  public class LowCarFactory implements CarFactory {
  	@Override
  	public Engine createEngine() {
  		return new LowEngine();
  	}
  	@Override
  	public Seat createSeat() {
  		return new LowSeat();
  	}
  }
  ```

- 应用场景
  - JDK中Calendar的getInstance方法
  - JDBC中Connection对象的获取
  - Hibernate中SessionFactory创建Session
  - spring中IOC容器创建管理bean对象
  - XML解析时的DocumentBuilderFactory创建解析器对象
  - 反射中Class对象的newInstance()