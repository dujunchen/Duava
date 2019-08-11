## 备忘录模式
- 在不破坏封装性的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态，以便以后当需要时能将该对象恢复到原先保存的状态。该模式又叫快照模式。

- 角色

  - 发起人（Originator）角色：记录当前时刻的内部状态信息，提供创建备忘录和恢复备忘录数据的功能，实现其他业务功能，它可以访问备忘录里的所有信息。
  - 备忘录（Memento）角色：负责存储发起人的内部状态，在需要的时候提供这些内部状态给发起人。
  - 管理者（Caretaker）角色：对备忘录进行管理，提供保存与获取备忘录的功能，但其不能对备忘录的内容进行访问与修改。

- 优点
  
  - 提供了一种可以恢复状态的机制。当用户需要时能够比较方便地将数据恢复到某个历史的状态。
  - 实现了内部状态的封装。除了创建它的发起人之外，其他对象都不能够访问这些状态信息。
  - 简化了发起人类。发起人不需要管理和保存其内部状态的各个备份，所有状态信息都保存在备忘录中，并由管理者进行管理，这符合单一职责原则。
  
- 缺点
  
  - 资源消耗大。如果要保存的内部状态信息过多或者特别频繁，将会占用比较大的内存资源。
  
- 应用场景
  
  - 棋类游戏中悔棋
  - 普通软件中的撤销操作
  - 事务管理中的回滚操作
  - Photoshop软件中的历史记录
  
- 代码实现
  
  - 白盒备忘录
  
    白盒实现将发起人角色的状态存储在一个大家都看得到的地方，并且对所有对象公开，因此是破坏封装性的。
  
    ```java
    //备忘录角色类，备忘录对象将发起人对象传入的状态存储起来。
    public class Memento {
        private String state;
    
        public Memento(String state) {
            this.state = state;
        }
    }
    //发起人角色类，发起人角色会创建一个备忘录对象，并将自己的内部状态存储起来。
    public class Originator {
        private String state;
    
        public Memento createMemento() {
            return new Memento(this.state);
        }
        public void recover(Memento memento) {
            this.state = memento.getState();
        }
    }
    //负责人角色类，负责人角色负责保存备忘录对象，但是从不修改（甚至不查看）备忘录对象的内容。
    public class Caretaker {
        private Memento memento;
    
        public void saveMemento(Memento memento) {
            this.memento = memento;
        }
        public Memento getMenento() {
            return this.memento;
        }
    }
    
    public class Client {
        public static void main(String[] args) {
            Originator o = new Originator();
            Caretaker c = new Caretaker();
            o.setState("ON");
            System.out.println("发起者的状态 --> " + o.getState());
            c.saveMemento(o.createMemento());
            o.setState("OFF");
            System.out.println("发起者的状态 --> " + o.getState());
            o.recover(c.getMenento());
            System.out.println("发起者的状态 --> " + o.getState());
        }
    }
    
    //输出：
    发起者的状态 --> ON
    发起者的状态 --> OFF
    发起者的状态 --> ON
    ```
  
    
  
  - 黑盒备忘录
  
    ```java
    //黑盒备忘录
    public class Originator {
        private String state;
      
        public Memento createMemento(){
            return new Memento(state);
        }
        public void recover(MementoIF mementoIF){
            setState(((Memento)mementoIF).getState());
        }
    		//黑盒设计的关键在于将Memento作为Originator的内部类，这样使得Memento的数据只能被Originator使用，外部对象无法对其访问。
        private class Memento implements MementoIF{
            private String state;
    
            public Memento(String state) {
                this.state = state;
            }
        }
    }
    ```
  
    
  
  - 多点备忘
  
    ```java
    public class Memento {
    
        private List<String> states;
        private int index;
    
        public Memento(List<String> states , int index){
            this.states = new ArrayList<>(states);
            this.index = index;
        }
        public List<String> getStates() {
            return states;
        }
        public int getIndex() {
            return index;
        }
    }
    public class Originator {
    
        private List<String> states = new ArrayList<>();
        private int index = -1;
    
        public Memento createMemento() {
            return new Memento(states, index);
        }
        public void recover(Memento memento) {
            states = memento.getStates();
            index = memento.getIndex();
        }
        public void setState(String state) {
            states.add(++index, state);
        }
        public String getState(int index) {
            return states.get(index);
        }
        public void printStates() {
            for (String state : states) {
                System.out.println(state);
            }
        }
    }
    public class Caretaker {
        private List<Memento> mementos = new ArrayList<>();
        private int current = -1;
    
        public int saveMemento(Memento memento){
            mementos.add(++current, memento);
            return current;
        }
        public Memento getMemento(int index){
            return mementos.get(index);
        }
    }
    
    public class Client {
        public static void main(String[] args) {
            Originator o = new Originator();
            Caretaker c = new Caretaker();
            o.setState("state1");
            o.setState("state2");
            int point1 = c.saveMemento(o.createMemento());
            o.setState("state3");
            o.setState("state4");
            o.printStates();
            System.out.println("---还原前---");
            o.recover(c.getMemento(point1));
            o.printStates();
        }
    }
    
    //输出：
    state1
    state2
    state3
    state4
    ---还原前---
    state1
    state2
    ```
  
    
  
  