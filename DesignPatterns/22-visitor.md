## 访问者模式

- 有些集合对象中存在多种不同的元素，且每种元素也存在多种不同的访问者和处理方式。访问者模式将作用于某种数据结构中的各元素的操作分离出来封装成独立的类，使其在不改变数据结构的前提下可以添加作用于这些元素的新的操作，为数据结构中的每个元素提供多种访问方式。它将对数据的操作与数据结构进行分离，是行为类模式中最复杂的一种模式。

- 角色

  - 抽象访问者（Visitor）角色：定义一个访问具体元素的接口，为**每个具体元素类**对应一个访问操作 visit() ，该操作中的参数类型标识了被访问的具体元素。
  - 具体访问者（ConcreteVisitor）角色：实现抽象访问者角色中声明的各个访问操作，确定访问者访问一个元素时该做什么。
  - 抽象元素（Element）角色：声明一个包含接受操作 accept() 的接口，accept() 方法的参数是一个具体的访问者对象。
  - 具体元素（ConcreteElement）角色：实现抽象元素角色提供的 accept() 操作，其方法体通常都是 visitor.visit(this) ，另外具体元素中可能还包含本身业务逻辑的相关操作。
  - 对象结构（Object Structure）角色：是一个包含元素角色的容器，**提供让访问者对象遍历容器中的所有元素的方法**，通常由 List、Set、Map 等聚合类实现。

- 优点

  - 访问者模式把相关的行为封装在一起，构成一个访问者，使每一个访问者的功能都比较单一，符合单一职责原则。
  - 将数据结构与作用于结构上的操作解耦，使得操作集合可相对自由地演化而不影响系统的数据结构。
  - 可以通过访问者来定义整个对象结构通用的功能。
  - 能够在不修改对象结构中的元素的情况下，为对象结构中的元素添加新的功能。

- 缺点

  - 增加新的元素类很困难。在访问者模式中，每增加一个新的元素类，都要在每一个具体访问者类中增加相应的具体操作，这违背了“开闭原则”。
  - 破坏封装。访问者模式中具体元素对访问者公布细节，这破坏了对象的封装性。
  - 违反了依赖倒置原则。访问者模式依赖了具体类，而没有依赖抽象类。

- 应用场景

  - 对象结构相对稳定，但其操作算法经常变化的程序。
  - 对象结构包含很多类型的对象，希望对这些对象实施一些依赖于其具体类型的操作。

- 代码实现

  ```java
  public interface Visitor {
    	//给学生评选的逻辑
      void award(Student student);
    	//给老师评选的逻辑
      void award(Teacher teacher);
  }
  
  //鼓励奖评选
  public class GuLiJiangVisitor implements Visitor {
  
      @Override
      public void award(Student student) {
          int tmp;
          if ((tmp = student.score) >= 70 && tmp < 85) {
              System.out.println("同学：【" + student.name + "】分数：【" + student.score + "】荣获鼓励奖。");
          }
      }
      @Override
      public void award(Teacher teacher) {
          int tmp;
          if ((tmp = teacher.score) >= 80 && tmp < 95) {
              System.out.println("老师：【" + teacher.name + "】分数：【" + teacher.score + "】荣获鼓励奖。");
          }
      }
  }
  
  //优秀奖评选
  public class YouXiuJiangVisitor implements Visitor {
  
      @Override
      public void award(Student student) {
          if (student.score >= 85) {
              System.out.println("同学：【" + student.name + "】分数：【" + student.score + "】荣获优秀奖。");
          }
      }
      @Override
      public void award(Teacher teacher) {
          if (teacher.score >= 95) {
              System.out.println("老师：【" + teacher.name + "】分数：【" + teacher.score + "】荣获优秀奖。");
          }
      }
  }
  
  public abstract class Person {
      protected String name;
      protected int score;
  
      public Person(String name, int score) {
          this.name = name;
          this.score = score;
      }
    	//每一个元素会接收一个访问者来处理自己
      protected  abstract void accept(Visitor visitor);
  }
  
  public class Student extends Person {
      public Student(String name, int score) {
          super(name, score);
      }
      @Override
      public void accept(Visitor visitor) {
          visitor.award(this);
      }
  }
  
  public class Teacher extends Person {
      public Teacher(String name, int score) {
          super(name, score);
      }
      @Override
      public void accept(Visitor visitor) {
          visitor.award(this);
      }
  }
  
  //奖项评委会
  public class PingWeiHui {
      List<Person> personList = new ArrayList<>();
  
      public void pingXuan(Visitor visitor) {
          for (Person person : personList) {
              person.accept(visitor);
          }
      }
    	//添加参选人员
      public void addPerson(Person person) {
          personList.add(person);
      }
    	//淘汰参选人员
      public void remove(Person person) {
          personList.remove(person);
      }
  }
  
  public class Client {
      public static void main(String[] args) {
          Person stu1 = new Student("张三", 75);
          Person stu2 = new Student("李四", 85);
          Person t1 = new Teacher("孙老师", 99);
          Person t2 = new Teacher("赵老师", 85);
          PingWeiHui pingWeiHui = new PingWeiHui();
          pingWeiHui.addPerson(stu1);
          pingWeiHui.addPerson(stu2);
          pingWeiHui.addPerson(t1);
          pingWeiHui.addPerson(t2);
          System.out.println("先评选鼓励奖...");
          pingWeiHui.pingXuan(new GuLiJiangVisitor());
          System.out.println("再评选优秀奖...");
          pingWeiHui.pingXuan(new YouXiuJiangVisitor());
      }
  }
  
  //输出：
  先评选鼓励奖...
  同学：【张三】分数：【75】荣获鼓励奖。
  老师：【赵老师】分数：【85】荣获鼓励奖。
  再评选优秀奖...
  同学：【李四】分数：【85】荣获优秀奖。
  老师：【孙老师】分数：【99】荣获优秀奖。
  ```

  