## 原型模式

- 和new对象的区别 
  
  - 克隆类似于new，但是不同于new。new创建新的对象属性采用的是默认值。克隆出的对象的属性值完全和原型对象相同。并且克隆出的新对象改变不会影响原型对象。然后，可以再修改克隆对象的值。

- 优点 
  
  - 效率高(直接克隆，避免了new所需要的重新执行构造过程步骤)。

- 应用场景
  
  - 对象之间相同或相似，即只是个别的几个属性不同的时候。
  
  - 适用于短时间大量创建新对象，新对象创建非常耗费资源，需要非常繁琐的数据准备或访问权限

- 浅克隆和深克隆
  
  - Object自带clone是浅克隆实现，同时需要被复制的对象实现`Cloneable`接口。

- 实现方式
  
  - 浅克隆
    
    ```java
    //以下代码全部省略setter、getter、toString
    public class A {
      private int a;
    }
    
    public class B implements Cloneable{
      private int b;
      private A a;
    
      @Override
      public Object clone() throws CloneNotSupportedException {
          return super.clone();
      }
    }
    
    public class ShallowClone {
      public static void main(String[] args) throws CloneNotSupportedException {
          A a = new A();
          a.setA(1);
          B b = new B();
          b.setA(a);
          b.setB(2);
          B b2 = (B)b.clone();
          System.out.println("b-->" + b);
          System.out.println("b2-->" + b2);
          b2.setB(3);
          System.out.println("b-->" + b);
          System.out.println("b2-->" + b2);
          b2.getA().setA(10);
          System.out.println("b-->" + b);
          System.out.println("b2-->" + b2);
      }
    }
    
    //输出：
    b-->B{b=2, a=A{a=1}}
    b2-->B{b=2, a=A{a=1}}
    b-->B{b=2, a=A{a=1}}
    b2-->B{b=3, a=A{a=1}}
    b-->B{b=2, a=A{a=10}}
    b2-->B{b=3, a=A{a=10}}
    ```
    
    > 通过上面的输出结果发现：浅克隆下克隆生成对象的基本数据类型（包括对应包装类）属性和String拷贝的是值，后续修改克隆对象的该属性值，并不会影响原来的对象里的值。但是引用类型属性拷贝的是引用，拷贝得到的对象和原来的对象的属性指向同一个对象，所以，后续修改其属性值，会影响原来的对象里的对应的属性值。

- 深克隆
  
  ```java
  public class A implements Cloneable{
      private int a;
  
      @Override
      public Object clone() throws CloneNotSupportedException {
          return super.clone();
      }
  }
  
  public class B implements Cloneable{
      private Integer b;
      private A a;
  
      @Override
      public Object clone() throws CloneNotSupportedException {
      //深克隆的关键在于将对象clone后，将对象中的引用类型(除了String)也clone一份赋值给新对象。
          B b = (B) super.clone();
          A a = b.getA();
          b.setA((A) a.clone());
          return b;
      }
  }
  
  public class DeepClone {
      public static void main(String[] args) throws CloneNotSupportedException {
          A a = new A();
          a.setA(1);
          B b = new B();
          b.setA(a);
          b.setB(2);
          B b2 = (B)b.clone();
          System.out.println("b-->" + b);
          System.out.println("b2-->" + b2);
          b2.setB(3);
          System.out.println("b-->" + b);
          System.out.println("b2-->" + b2);
          b2.getA().setA(10);
          System.out.println("b-->" + b);
          System.out.println("b2-->" + b2);
      }
  }
  //输出：
  b-->B{b=2, a=A{a=1}}
  b2-->B{b=2, a=A{a=1}}
  b-->B{b=2, a=A{a=1}}
  b2-->B{b=3, a=A{a=1}}
  b-->B{b=2, a=A{a=1}}
  b2-->B{b=3, a=A{a=10}}
  ```
  
  - 利用序列化和反序列化技术也可以实现深克隆。
    
    ```java
    public class A implements Serializable {
      private int a;
    }
    public class B implements Serializable {
      private Integer b;
      private A a;
    }
    
    public class DeepClone2 {
      public static void main(String[] args) throws IOException, ClassNotFoundException {
          A a = new A();
          a.setA(1);
          B b = new B();
          b.setA(a);
          b.setB(2);
          //使用序列化和反序列化实现深克隆
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          ObjectOutputStream oos = new ObjectOutputStream(baos);
          oos.writeObject(b);
          ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
          B b2 = (B) ois.readObject();
          System.out.println("b-->" + b);
          System.out.println("b2-->" + b2);
          b2.setB(3);
          System.out.println("b-->" + b);
          System.out.println("b2-->" + b2);
          b2.getA().setA(10);
          System.out.println("b-->" + b);
          System.out.println("b2-->" + b2);
      }
    }
    //输出：
    b-->B{b=2, a=A{a=1}}
    b2-->B{b=2, a=A{a=1}}
    b-->B{b=2, a=A{a=1}}
    b2-->B{b=3, a=A{a=1}}
    b-->B{b=2, a=A{a=1}}
    b2-->B{b=3, a=A{a=10}}
    ```