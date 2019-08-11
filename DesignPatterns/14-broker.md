## 中介者模式

- 好多对象之间存在复杂的交互关系，这种交互关系常常是“网状结构”，它要求每个对象都必须知道它需要交互的对象。定义一个中介对象来封装一系列对象之间的交互，使原有对象之间的耦合松散，且可以独立地改变它们之间的交互。中介者模式又叫调停模式，它是迪米特法则的典型应用。

- 角色

  - 抽象中介者（Mediator）角色：它是中介者的接口，提供了同事对象注册与转发同事对象信息的抽象方法。
  - 具体中介者（ConcreteMediator）角色：实现中介者接口，定义一个 List 来管理同事对象，协调各个同事角色之间的交互关系，因此它依赖于同事角色。
  - 抽象同事类（Colleague）角色：定义同事类的接口，保存中介者对象，提供同事对象交互的抽象方法，实现所有相互影响的同事类的公共功能。
  - 具体同事类（Concrete Colleague）角色：是抽象同事类的实现者，当需要与其他同事对象交互时，由中介者对象负责后续的交互。

- 优点

  - 降低了对象之间的耦合性，使得对象易于独立地被复用。
  - 将对象间的一对多关联转变为一对一的关联，提高系统的灵活性，使得系统易于维护和扩展。

- 缺点

  - 当同事类太多时，中介者的职责将很大，它会变得复杂而庞大，以至于系统难以维护。

- 应用场景
  
- MVC模式(其中的C，控制器就是一个中介者对象。M和V都和他打交道)
  - 通讯录的使用，使得每个人不需要记住自己所有亲朋好友的联系方式，只要跟通讯录交互即可。
  - 聊天程序
  
- 代码实现

  ```java
  //同事类的接口
  public interface Department {	
  	void selfAction(); // 做本部门的事情
  	void outAction(); // 通过中介者(总经理)跟其他同事部门交涉
  }
  
  //中介者对象，用以维护各同事对象之间的关系
  public interface Mediator {
  	void register(String dname, Department d);
  	void command(String dname);
  }
  
  //总经理类
  public class President implements Mediator {
  	
  	private static Map<String,Department> map = new HashMap<String , Department>();
  	
  	@Override
  	public void command(String dname) {
  		map.get(dname).selfAction();
  	}
  
  	@Override
  	public void register(String dname, Department d) {
  		map.put(dname, d);
  	}
  }
  //研发部
  public class Development implements Department {
  
  	private Mediator m;  //持有中介者(总经理)的引用
  	
  	public Development(Mediator m) {
  		this.m = m;
  		m.register("development", this);
  	}
  
  	@Override
  	public void outAction() {
  		System.out.println("汇报工作！没钱了，需要资金支持！");
  	}
  
  	@Override
  	public void selfAction() {
  		System.out.println("专心科研，开发项目！");
  	}
  }
  //财务部
  public class Finacial implements Department {
  
  	private Mediator m;  //持有中介者(总经理)的引用
  	
  	public Finacial(Mediator m) {
  		this.m = m;
  		m.register("finacial", this);
  	}
  
  	@Override
  	public void outAction() {
  		System.out.println("汇报工作！没钱了，钱太多了！怎么花?");
  	}
  
  	@Override
  	public void selfAction() {
  		System.out.println("数钱！");
  	}
  }
  //市场部
  public class Market implements Department {
  
  	private Mediator m;  //持有中介者(总经理)的引用
  	
  	public Market(Mediator m) {
  		this.m = m;
  		m.register("market", this);
  	}
  
  	@Override
  	public void outAction() {
  		System.out.println("汇报工作！项目承接的进度，需要资金支持！");
  		//市场部需要同财务部交涉，都通过中介者对象间接实现
  		m.command("finacial");
  	}
  
  	@Override
  	public void selfAction() {
  		System.out.println("跑去接项目！");
  	}
  }
  
  public class Client {
  	public static void main(String[] args) {
  		// 中介者
  		Mediator m = new President();
  		Market market = new Market(m);
  		Development devp = new Development(m);
  		Finacial f = new Finacial(m);
  		market.selfAction();
  		market.outAction();
  	}
  }
  ```