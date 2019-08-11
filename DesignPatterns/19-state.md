## 状态模式
- 对有状态的对象，把复杂的“判断逻辑”提取到不同的状态对象中，允许状态对象在其内部状态发生改变时改变其行为。用于解决系统中复杂对象的状态转换以及不同状态下行为的封装问题。

- 优点

  - 将与特定状态相关的行为局部化到一个状态中，并且将不同状态的行为分割开来，满足“单一职责原则”。
  - 将不同的状态引入独立的对象中会使得状态转换变得更加明确，且减少对象间的相互依赖。
  - 通过定义新的子类可以很容易地增加新的状态和转换。

- 缺点

  - 状态模式的使用必然会增加系统的类与对象的个数。

- 状态模式和策略模式的对比
  
  
  
- 角色
  
  - Context环境类  
    - 环境类中维护一个State对象，他是定义了当前的状态。
  - State抽象状态类
  - ConcreteState具体状态类  
    - 每一个类封装了一个状态对应的行为。
  
- 代码实现
  
  ```java
  //context会维护一个当前state
  public class ScoreContext {
      private AbstractState state;
  
      public ScoreContext() {
          state = new LowState("low", 0);
      }
  
      public void addScore(int n) {
        	//具体的状态转换工作转发给各个状态类自己完成
          state.addScore(n, this);
          System.out.println("current: stateName-->" + state.getStateName() + ",score-->" + state.getScore());
      }
  		//省略setter,getter
  }
  //抽象状态类
  public abstract class AbstractState {
      private String stateName;
      private int score;
  
      public AbstractState(String stateName, int score) {
          this.stateName = stateName;
          this.score = score;
      }
  
      public void addScore(int n, ScoreContext context){
          score += n;
          addScore0(score, context);
      }
    	//各个不同状态类各自的状态转换的判断逻辑，各自实现。
      protected abstract void addScore0(int n, ScoreContext context);
    	//省略setter,getter
  }
  //不及格（具体状态类）
  public class LowState extends AbstractState {
      public LowState(AbstractState state) {
          super("low", state.getScore());
      }
  
      public LowState(String stateName, int score) {
          super(stateName, score);
      }
  
      @Override
      protected void addScore0(int n, ScoreContext context) {
          if(n >= 90){
            	//向优秀状态转换，以下其它的状态转换同理。
              context.setState(new HighState(this));
          }else if(n >=60){
              context.setState(new MiddleState(this));
          }
      }
  }
  //良
  public class MiddleState extends AbstractState {
  
      public MiddleState(AbstractState state) {
          super("middle", state.getScore());
      }
  
      @Override
      protected void addScore0(int n, ScoreContext context) {
          if(n >= 90){
              context.setState(new HighState(this));
          }else if(n < 60){
              context.setState(new LowState(this));
          }
      }
  }
  //优秀
  public class HighState extends AbstractState {
  
      public HighState(AbstractState state){
          super("high", state.getScore());
      }
  
      @Override
      protected void addScore0(int n, ScoreContext context) {
          if(n < 60){
              context.setState(new LowState(this));
          }else if (n < 90){
              context.setState(new MiddleState(this));
          }
      }
  }
  
  public class Client {
      public static void main(String[] args) {
          ScoreContext context = new ScoreContext();
          context.addScore(10);
          context.addScore(50);
          context.addScore(-10);
          context.addScore(45);
          context.addScore(-60);
      }
  }
  //输出：
  current: stateName-->low,score-->10
  current: stateName-->middle,score-->60
  current: stateName-->low,score-->50
  current: stateName-->high,score-->95
  current: stateName-->low,score-->35
  ```
  
  
  
- 开发中常见的场景
  - 银行系统中账号状态的管理
  - OA系统中公文状态的管理
  - 酒店系统中，房间状态的管理
  - 线程对象各状态之间的切换