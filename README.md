# BackEndCore

> 💻本仓库中所有文字纯原创，如果需要转载，请注明转载出处，谢谢！😘如果该系列文字对您有帮助的话，还请点个star给予精神支持！😁
>

## 一、设计模式系列

### 设计模式相关理论

- 设计模式是解决特定问题的一系列套路，是前辈们的代码设计经验的总结，具有一定的普遍性，可以反复使用。其目的是为了提高代码的可重用性、代码的可读性和代码的可靠性。

- 设计模式的本质是面向对象设计原则的实际运用，是对类的封装性、继承性和多态性以及类的关联关系和组合关系的充分理解。

- **面向对象7大设计原则**
1. 开闭原则
  
  	- 软件实体应当对扩展开放，对修改关闭。

  	- 可以通过“抽象约束、封装变化”来实现开闭原则，即通过接口或者抽象类为软件实体定义一个相对稳定的抽象层，外界只能跟该抽象层交互，而软件中易变的细节可以从抽象派生来的实现类来进行扩展，当软件需要发生变化时，只需要根据需求重新派生一个实现类来扩展就可以了。
  2. 里氏替换原则
    
     - 子类可以扩展父类的功能，但不能改变父类原有的功能。也就是说，子类继承父类时，除添加新的方法完成新增功能外，尽量不要重写父类的方法。
  3. 依赖倒置原则
  
     - 其核心思想是：要面向接口编程，不要面向实现编程。
  4. 单一职责原则
     - 一个类应该有且仅有一个引起它变化的原因，否则类应该被拆分。
  5. 接口隔离原则
     - 要为各个类建立它们需要的专用接口，而不要试图去建立一个很庞大的接口供所有依赖它的类去调用。
  6. 迪米特法则
     - 如果两个软件实体无须直接通信，那么就不应当发生直接的相互调用，可以通过第三方转发该调用。其目的是降低类之间的耦合度，提高模块的相对独立性。
  7. 组合聚合复用原则
     - 软件复用优先使用组合或者聚合关系复用，少用继承关系复用。

### 分类

- **创建型模式**

  创建型模式的主要关注点是“怎样创建对象”，它的主要特点是**将对象的创建与使用分离**。这样可以降低系统的耦合度，使用者不需要关注对象的创建细节。

  - **[单例模式](DesignPatterns/1-singleton.md)**
  - **[工厂模式](DesignPatterns/2-factory.md)**
  - **[抽象工厂模式](DesignPatterns/2-factory.md)**
  - **[建造者模式](DesignPatterns/3-builder.md)**
  - **[原型模式](DesignPatterns/4-prototype.md)**

- **结构型模式**
  
  结构型模式描述如何将类或对象按某种布局组成更大的结构。它分为**类结构型模式和对象结构型模式**，前者采用继承机制来组织接口和类，后者釆用组合或聚合来组合对象。
  
  - **[适配器模式](DesignPatterns/5-adapter.md)**
  - **[桥接模式](DesignPatterns/7-bridging.md)**
  - **[装饰模式](DesignPatterns/9-decorator.md)**
  - **[组合模式](DesignPatterns/8-composite.md)**
  - **[外观模式](DesignPatterns/10-facade.md)**
  - **[享元模式](DesignPatterns/11-flyweight.md)**
  - **[代理模式](DesignPatterns/6-proxy.md)**
  
- **行为型模式**
  
  行为型模式用于描述程序在运行时复杂的流程控制，即描述多个类或对象之间怎样相互协作共同完成单个对象都无法单独完成的任务，它涉及**算法与对象间职责的分配**。它分为**类行为模式和对象行为模式**，前者采用继承机制来在类间分派行为，后者采用组合或聚合在对象间分配行为。
  
  - **[模板方法模式](DesignPatterns/18-template-method.md)**
  - **[命令模式](DesignPatterns/15-command.md)**
  - **[迭代器模式](DesignPatterns/13-iterator.md )**
  - **[观察者模式](DesignPatterns/20-observer.md)**
  - **[中介者模式](DesignPatterns/14-broker.md)**
  - **[备忘录模式](DesignPatterns/21-memento.md)**
  - **[状态模式](DesignPatterns/19-state.md)**
  - **[策略模式](DesignPatterns/17-strategy.md)**
  - **[责任链模式](DesignPatterns/12-chain-of-responsibility.md)**
  - **[访问者模式](DesignPatterns/22-visitor.md)**
  - **[解释器模式](DesignPatterns/16-interpreter.md)**
  
## 二、源码剖析系列

### JDK的一些源码

- **[深入理解Java集合中的Iterator](SourceCodeAnalyse/deep-analysis-of-java-iterator/深入理解Java集合中的Iterator.md)**
- **[Java动态代理模式的奥秘](SourceCodeAnalyse/deep-analysis-of-jdk-dynamic-proxy/Java动态代理模式的奥秘.md)**
