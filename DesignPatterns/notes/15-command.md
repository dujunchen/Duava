## 命令模式
- 命令模式是对命令的封装。命令模式把发出命令的责任和执行命令的责任分割开，委派给不同的对象。比如去饭店点餐，服务员（命令发出者）接收到客户点餐请求（菜单），会将该命令（做菜命令）发送给厨师（命令执行者）。这个过程中，服务员并不需要知道厨师做菜的过程和细节。
  
- 角色
  
  - 抽象命令类（Command）角色
  - 具体命令角色（Concrete Command）角色
  - 命令实现者/接收者（Receiver）角色
  - 命令调用者/请求者（Invoker）角色
  
- 优点
  
  - 新的命令很容易地被加入到系统里。比如饭店新增一个菜系（一个新的命令），只需要新招聘一名该菜系的厨师（对应的命令执行者）即可。
  - 可以容易地实现对请求的撤销和恢复。比如菜单上的菜临时不要了，可以在发给厨师做之前从菜单上划去。
  - 在需要的情况下，可以较容易地将命令记入日志。有需要可以记录一下今天点过哪些菜下次继续再点。
  
- 缺点
  
  - 可能产生大量具体命令类。因为计对每一个具体操作都需要设计一个具体命令类，这将增加系统的复杂性。
  
- 代码实现
  
  ```java
  //抽象命令
  public interface Food {
      void cook();
  }
  
  //命令接收者
  public class Cooker {
      public void cookChaofan(){
          System.out.println("厨师正在炒炒饭...");
      }
      public void cookHunTun(){
          System.out.println("厨师正在煮馄饨...");
      }
  }
  //具体命令（炒炒饭的命令）
  public class ChaoFan  implements Food{
      Cooker cooker;
  
      public ChaoFan(Cooker cooker) {
          this.cooker = cooker;
      }
      @Override
      public void cook() {
          System.out.println("炒炒饭的命令发给厨师，等待厨师开炒...");
          cooker.cookChaofan();
      }
  }
  //具体命令（煮馄饨的命令）
  public class HunTun implements Food {
      Cooker cooker;
  
      public HunTun(Cooker cooker) {
          this.cooker = cooker;
      }
      @Override
      public void cook() {
          System.out.println("煮馄饨的命令发给厨师，等待厨师开煮...");
          cooker.cookHunTun();
      }
  }
  //命令发出者（服务员）
  public class Waiter {
    	//菜单
      private List<Food> menu = new ArrayList<>();
  		
      public void add(Food food){
          menu.add(food);
      }
      public void delete(Food food){
          menu.remove(food);
      }
      public void clear(){
          menu.clear();
      }
    	//把菜单交给厨师，让厨师做上面的菜
      public void callCooking(){
          for(Food food : menu){
              food.cook();
          }
      }
  }
  
  public class Client {
      public static void main(String[] args) {
        	//厨师就绪
          Cooker cooker = new Cooker();
          Food chaoFan = new ChaoFan(cooker);
          Food hunTun = new HunTun(cooker);
        	//服务员就绪
          Waiter waiter = new Waiter();
        	//客户点了一个炒饭
          waiter.add(chaoFan);
        	//又点了一个馄饨
          waiter.add(hunTun);
        	//炒饭不想要了
          waiter.delete(chaoFan);
        	//点好了开始做菜
          waiter.callCooking();
        	//换一个新菜单为下一次点餐准备
          waiter.clear();
          System.out.println("---第二次点餐---");
        	//重新又点了一个炒饭
          waiter.add(chaoFan);
        	//点好开始做菜
          waiter.callCooking();
        	//换一个新菜单为下一次点餐准备
          waiter.clear();
      }
  }
  
  //输出：
  煮馄饨的命令发给厨师，等待厨师开煮...
  厨师正在煮馄饨...
  ------
  炒炒饭的命令发给厨师，等待厨师开炒...
  厨师正在炒炒饭...
  
  ```
  
  
  
- 开发中常见的场景
  
  - 命令的撤销和恢复。
  
  - Struts2中， action的整个调用过程中就有命令模式。
  - 数据库事务机制的底层实现。