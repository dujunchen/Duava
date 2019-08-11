## 责任链模式
- 为了避免请求发送者与多个请求处理者耦合在一起，将所有请求的处理者通过前一对象记住其下一个对象的引用而连成一条链；当有请求发生时，可将请求沿着这条链传递，直到有对象处理它为止。

- 优点

  - 将请求的发送者和请求的处理者解耦。客户只需要将请求发送到责任链上即可，无须知道到底是哪一个对象处理其请求以及链的结构，也无须关心请求的处理细节和请求的传递过程。
  - 可以根据需要增加新的请求处理类，满足开闭原则。
  - 当工作流程发生变化，可以动态地改变链内的成员或者调动它们的次序，也可动态地新增或者删除责任。
  - 每个类只需要处理自己该处理的工作，不该处理的传递给下一个对象完成，明确各类的责任范围，符合类的单一职责原则。

- 缺点

  - 不能保证每个请求一定被处理。由于一个请求没有明确的接收者，所以不能保证它一定会被处理，该请求可能一直传到链的末端都得不到处理。

  - 对比较长的职责链，请求的处理可能涉及多个处理对象，系统性能将受到一定影响。

  - 职责链建立的合理性要靠客户端来保证，增加了客户端的复杂性，可能会由于职责链的错误设置而导致系统出错，如可能会造成循环调用。

- 场景：
  - 各类审批流程。
  - Java中，异常机制就是一种责任链模式。
  - Javascript语言中，事件的冒泡和捕获机制。Java语言中，事件的处理采用观察者模式。
  - Servlet开发中，过滤器的链式处理。
  - Struts2中，拦截器的调用也是典型的责任链模式。

- 代码实现：

  ```java
  /**
   * 封装请假的基本信息
   */
  public class LeaveRequest {
  	private String empName;
  	private int leaveDays;
  	private String reason;
  	
  	public LeaveRequest(String empName, int leaveDays, String reason) {
  		super();
  		this.empName = empName;
  		this.leaveDays = leaveDays;
  		this.reason = reason;
  	}
  }
  
  /**
   * 抽象类
   */
  public abstract class Leader {
  	protected String name;
  	protected Leader nextLeader; //责任链上的后继对象
  	
  	public Leader(String name) {
  		super();
  		this.name = name;
  	}
  	
  	//设定责任链上的后继对象
  	public void setNextLeader(Leader nextLeader) {
  		this.nextLeader = nextLeader;
  	}
  	
  	/**
  	 * 处理请求的核心的业务方法
  	 * 责任链上每个节点的业务逻辑不相同，需要定义成抽象类
  	 * @param request
  	 */
  	public abstract void handleRequest(LeaveRequest request);
  }
  
  /**
   * 主任
   */
  public class Director extends Leader {
  
  	public Director(String name) {
  		super(name);
  	}
  
  	@Override
  	public void handleRequest(LeaveRequest request) {
  		if(request.getLeaveDays()<3){
  			System.out.println("员工："+request.getEmpName()+"请假，天数："+request.getLeaveDays()+",理由："+request.getReason());
  			System.out.println("主任："+this.name+",审批通过！");
  		}else{
  			if(this.nextLeader!=null){
  				this.nextLeader.handleRequest(request);
  			}
  		}
  	}
  }
  
  /**
   * 经理
   */
  public class Manager extends Leader {
  
  	public Manager(String name) {
  		super(name);
  	}
  
  	@Override
  	public void handleRequest(LeaveRequest request) {
  		if(request.getLeaveDays()<10){
  			System.out.println("员工："+request.getEmpName()+"请假，天数："+request.getLeaveDays()+",理由："+request.getReason());
  			System.out.println("经理："+this.name+",审批通过！");
  		}else{
  			if(this.nextLeader!=null){
  				this.nextLeader.handleRequest(request);
  			}
  		}
  	}
  }
  
  /**
   * 副总经理
   */
  public class ViceGeneralManager extends Leader {
  
  	public ViceGeneralManager(String name) {
  		super(name);
  	}
  
  	@Override
  	public void handleRequest(LeaveRequest request) {
  		if(request.getLeaveDays()<20){
  			System.out.println("员工："+request.getEmpName()+"请假，天数："+request.getLeaveDays()+",理由："+request.getReason());
  			System.out.println("副总经理："+this.name+",审批通过！");
  		}else{
  			if(this.nextLeader!=null){
  				this.nextLeader.handleRequest(request);
  			}
  		}
  	}
  }
  
  /**
   * 总经理
   */
  public class GeneralManager extends Leader {
  
  	public GeneralManager(String name) {
  		super(name);
  	}
  
  	@Override
  	public void handleRequest(LeaveRequest request) {
  		if(request.getLeaveDays()<30){
  			System.out.println("员工："+request.getEmpName()+"请假，天数："+request.getLeaveDays()+",理由："+request.getReason());
  			System.out.println("总经理："+this.name+",审批通过！");
  		}else{
  			System.out.println("莫非"+request.getEmpName()+"想辞职，居然请假"+request.getLeaveDays()+"天！");
  		}
  	}
  }
  
  public class Client {
  	public static void main(String[] args) {
  		Leader a = new Director("张三");
  		Leader b = new Manager("李四");
  		Leader b2 = new ViceGeneralManager("赵六");
  		Leader c = new GeneralManager("王五");
  		//组织责任链对象的关系
  		a.setNextLeader(b);
  		b.setNextLeader(b2);
  		b2.setNextLeader(c);
  		//开始请假操作
  		LeaveRequest req = new LeaveRequest("TOM", 19, "回英国老家探亲！");
  		a.handleRequest(req);
  	}
  }
  ```