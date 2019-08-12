## 享元模式
- 要点
  - 内存属于稀缺资源，不要随便浪费。如果有很多个**完全相同或相似的对象**，我们可以通过享元模式**共享相同的部分，而仅仅将可变不能共享的部分后续以参数传入**，以达到节省内存。
- 享元对象能做到共享的关键是区分了内部状态和外部状态。
    - **内部状态**：可以共享，不会随环境变化而改变。
    - **外部状态**：不可以共享，会随环境变化而改变。
- 角色
  - 享元工厂类  
    - 创建并管理享元对象，享元池一般设计成键值对。
  - 抽象享元类
    - 是所有的具体享元类的基类，为具体享元规范需要实现的公共接口，非享元的外部状态以参数的形式通过方法传入。
  - 具体享元类  
    - 实现抽象享元角色中所规定的接口，为**内部状态**提供**成员变量**进行存储。
  - 非共享享元类  
    - 外部状态可以设计为**非共享享元类**，它以参数的形式注入具体享元的相关方法中。
- 优缺点
  - 优点
    - 极大减少内存中对象的数量。
    - 相同或相似对象内存中只存一份，极大的节约资源，提高系统性能。
    - 外部状态相对独立，不影响内部状态。
  - 缺点
    - 模式较复杂，使程序逻辑复杂化。
    - 为了节省内存，共享了内部状态，分离出外部状态，而读取外部状态使运行时间变长。用时间换取了空间。
- 应用场景
  - 享元模式由于其共享的特性，可以在任何“池”中操作，比如：线程池、数据库连接池。
  - String类的设计也是享元模式。
- 代码实现
```java
/*
 * 享元抽象类
 */
public interface Chess {

	void setColor(String c);

	String getColor();

	void display(Coordinate c);
}

/*
    具体享元类
 */
public class ChessFlyWeight implements Chess{
	//内部状态用成员属性存储
	private String color;	
  
	public ChessFlyWeight(String color) {
		this.color = color;
	}
	@Override
	public void setColor(String c) {
		this.color = c;
	}

	@Override
	public String getColor() {
		return this.color;
	}

	@Override
	//外部状态用单独的类存储，通过方法参数传入
	public void display(Coordinate c) {
		System.out.println("颜色： " + this.color);
		System.out.println("坐标：x=" + c.getX() + ",y="+ c.getY());
	}
}
/**
 * 坐标类 （外部状态类）
 */
public class Coordinate {
	private int x;
	private int y;
  
	public Coordinate(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
  //省略setter,getter
}
/*
    享元工厂类
 */
public class ChessFlyWeightFactory {
	private static Map<String, Chess> map = new HashMap<String, Chess>();

	public static Chess getChess(String color) {
		Chess tmp = map.get(color);
		if (tmp == null) {
			Chess chess = new ChessFlyWeight(color);
			map.put(color, chess);
			return chess;
		}
		return tmp;
	}
}

public class Client {
	public static void main(String[] args) {
		Chess chess1 = ChessFlyWeightFactory.getChess("black");
		Chess chess2 = ChessFlyWeightFactory.getChess("black");
		System.out.println(chess1 == chess2);
		chess1.display(new Coordinate(10, 10));
		chess2.display(new Coordinate(30, 30));
	}
}
```