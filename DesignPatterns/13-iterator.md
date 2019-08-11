## 迭代器模式

- 提供一个对象来顺序访问聚合对象中的一系列数据，而不暴露聚合对象的内部表示。通常访问一个聚合对象的各个元素会将遍历行为放在聚合对象中，但这种方式不利于程序的扩展，如果要更换遍历方法就必须修改程序源代码，这违背了 “开闭原则”。而将遍历方法由用户自己实现则会暴露聚合类的内部表示，有安全问题。“迭代器模式”能较好地克服以上缺点，它在客户访问类与聚合类之间插入一个迭代器，这分离了聚合对象与其遍历行为，对客户也隐藏了其内部细节，且满足“单一职责原则”和“开闭原则”，如 [Java](http://c.biancheng.net/java/) 中的 Collection、List、Set、Map 等都包含了迭代器。

- 角色

  - 抽象聚合（Aggregate）角色：定义存储、添加、删除聚合对象以及创建迭代器对象的接口。
  - 具体聚合（ConcreteAggregate）角色：实现抽象聚合类，返回一个具体迭代器的实例。
  - 抽象迭代器（Iterator）角色：定义访问和遍历聚合元素的接口，通常包含 hasNext()、first()、next() 等方法。
  - 具体迭代器（Concretelterator）角色：实现抽象迭代器接口中所定义的方法，完成对聚合对象的遍历，记录遍历的当前位置。

- 优点

  - 访问一个聚合对象的内容而无须暴露它的内部表示。
  - 遍历任务交由迭代器完成，这简化了聚合类。
  - 它支持以不同方式遍历一个聚合，甚至可以自定义迭代器的子类以支持新的遍历。
  - 增加新的聚合类和迭代器类都很方便，无须修改原有代码。
  - 封装性良好，为遍历不同的聚合结构提供一个统一的接口。

- 缺点

  - 增加了类的个数，这在一定程度上增加了系统的复杂性。

- 代码实现

  ```java
  //抽象聚合
  interface Aggregate{ 
      void add(Object obj); 
      void remove(Object obj); 
      Iterator getIterator(); 
  }
  //具体聚合
  public class ConcreteAggregate implements Aggregate{ 
      private List<Object> list=new ArrayList<>(); 
      public void add(Object obj){ 
          list.add(obj); 
      }
      public void remove(Object obj){ 
          list.remove(obj); 
      }
      public Iterator getIterator(){ 
          return new ConcreteIterator(list); 
      }     
  }
  //抽象迭代器
  interface Iterator{
      Object first();
      Object next();
      boolean hasNext();
  }
  //具体迭代器
  class ConcreteIterator implements Iterator{ 
      private List<Object> list=null; 
      private int index=-1; 
      public ConcreteIterator(List<Object> list){ 
          this.list=list; 
      } 
      public boolean hasNext(){ 
          if(index<list.size()-1){ 
              return true;
          }else{
              return false;
          }
      }
      public Object first(){
          index=0;
          Object obj=list.get(index);;
          return obj;
      }
      public Object next(){ 
          Object obj=null; 
          if(this.hasNext()){ 
              obj=list.get(++index); 
          } 
          return obj; 
      }   
  }
  ```

  