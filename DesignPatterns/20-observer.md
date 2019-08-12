## 观察者模式
- 多个对象间存在一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并被自动更新。这种模式有时又称作发布-订阅模式。

- 角色

  - 抽象主题（Subject）角色：也叫抽象目标类，它提供了一个用于保存观察者对象的聚集类和增加、删除观察者对象的方法，以及通知所有观察者的抽象方法。

  - 具体主题（Concrete Subject）角色：也叫具体目标类，它实现抽象目标中的通知方法，当具体主题的内部状态发生改变时，通知所有注册过的观察者对象。

  - 抽象观察者（Observer）角色：它是一个抽象类或接口，它包含了一个更新自己的抽象方法，当接到具体主题的更改通知时被调用。

  - 具体观察者（Concrete Observer）角色：实现抽象观察者中定义的抽象方法，以便在得到目标的更改通知时更新自身的状态。

- 优点

  - 降低了目标与观察者之间的耦合关系。
  - 目标与观察者之间建立了一套触发机制。

- 缺点

  - 目标与观察者之间的依赖关系并没有完全解除，而且有可能出现循环引用。
  - 当观察者对象很多时，通知的发布会花费很多时间，影响程序的效率。

- 代码实现
  
  ```java
  //抽象主题
  public abstract class News {
      protected List<Listener> listeners = new ArrayList<>();
  
      public void add(Listener listener) {
          listeners.add(listener);
      }
  
      public void remove(Listener listener) {
          listeners.remove(listener);
      }
  
      protected abstract void notifyAllListeners();
  }
  //具体主题
  public class Movie extends News {
      @Override
      protected void notifyAllListeners() {
          System.out.println("电影新闻更新了，发送给所有读者。");
          for(Listener listener : listeners){
              listener.listen();
          }
      }
  }
  
  public class TiYu extends News {
      @Override
      protected void notifyAllListeners() {
          System.out.println("体育新闻更新了，发送给所有读者。");
          for(Listener listener : listeners){
              listener.listen();
          }
      }
  }
  
  //抽象观察者
  public interface Listener {
      void listen();
  }
  
  //具体观察者
  public class Student implements Listener {
      @Override
      public void listen() {
          System.out.println("学生开始听新闻...");
      }
  }
  
  public class Teacher implements Listener {
      @Override
      public void listen() {
          System.out.println("老师开始听新闻...");
      }
  }
  
  public class Client {
      public static void main(String[] args) {
          News movie = new Movie();
          News tiyu = new TiYu();
          Listener student = new Student();
          Listener teacher = new Teacher();
          System.out.println("电影频道");
          movie.add(student);
          movie.add(teacher);
          movie.notifyAllListeners();
          System.out.println("体育频道");
          tiyu.add(student);
          tiyu.notifyAllListeners();
      }
  }
  ```
  
  
  
- JDK提供了java.util.Observable类和java.util.Observer接口来方便实现观察者模式。可以不需要我们自己实现抽象主题和抽象观察者。
  
  ```java
  public class Movie extends Observable {
      //新上映n部电影
      public void show(int n){
          //调用表示通知，不调用则不通知
          setChanged();
        	//jdk底层实现时，采用倒序通知，也就是说后加入的观察者会比先加入的先收到通知。
          notifyObservers(n);
      }
  }
  
  public class TiYu extends Observable {
      //新上映n部体育节目
      public void show(int n) {
          //调用表示通知，不调用则不通知
          setChanged();
          notifyObservers(n);
      }
  }
  
  public class Student implements Observer {
      @Override
      public void update(Observable o, Object arg) {
          System.out.println(String.format("订阅的%s频道开始更新了，更新的节目数量为%s部...", o.getClass().getSimpleName(), arg));
          System.out.println("学生收听ing...");
      }
  }
  
  public class Teacher implements Observer {
      @Override
      public void update(Observable o, Object arg) {
          System.out.println(String.format("订阅的%s频道开始更新了，更新的节目数量为%s部...", o.getClass().getSimpleName(), arg));
          System.out.println("老师收听ing...");
      }
  }
  
  public class Client {
      public static void main(String[] args) {
          Observable movie = new Movie();
          Observable tiyu = new TiYu();
          Observer student = new Student();
          Observer teacher = new Teacher();
          movie.addObserver(student);
          movie.addObserver(teacher);
          ((Movie) movie).show(5);
          tiyu.addObserver(student);
          tiyu.addObserver(teacher);
          tiyu.deleteObserver(student);
          ((TiYu) tiyu).show(10);
      }
  }
  
  //输出：
  订阅的Movie频道开始更新了，更新的节目数量为5部...
  老师收听ing...
  订阅的Movie频道开始更新了，更新的节目数量为5部...
  学生收听ing...
  订阅的TiYu频道开始更新了，更新的节目数量为10部...
  老师收听ing...
  ```
  
  
  
- 应用场景
  
  - 聊天室程序，服务器转发给所有客户端。
  - 网络游戏(多人联机对战)场景中，服务器将客户端的状态进行分发。
  - 邮件订阅。
  - Servlet中，监听器的实现。
  - Android中，广播机制。
  - JDK的AWT中事件处理模型,基于观察者模式的委派事件模型(Delegation Event Model)。
    - 事件源--目标对象
    - 事件监听器--观察者
  - 京东商城中，群发某商品打折信息。