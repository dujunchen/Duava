## 桥接模式
- 桥接模式核心要点 
- 处理**多层继承**结构，处理**多维度**变化的场景，将各个维度设计成独立的继承结构，使各个维度可以独立的扩展在抽象层建立关联。举个例子：比如图形既可按形状分，又可按颜色分。如果采用继承方式，m种形状和n种颜色，共需要建立m*n个类，造成类膨胀，而且复用性很差。桥接模式采用组合模式代替多层继承，使每一维度独立变化。
- 代码实现
```java
/*
 * 品牌维度
 */
public interface Brand {
	void sale();
}

class Lenovo implements Brand {
	@Override
	public void sale() {
		System.out.println("销售联想电脑");
	}
}

class Dell implements Brand {
	@Override
	public void sale() {
		System.out.println("销售Dell电脑");
	}
}

class Shenzhou implements Brand {
	@Override
	public void sale() {
		System.out.println("销售神舟电脑");
	}
}

/*
 * 终端类型维度
 */
public abstract class ComputerType {
	private Brand brand;

	public ComputerType(Brand brand) {
		this.brand = brand;
	}
	public void sale() {
		// 品牌维度
		brand.sale();
	}
}

class Laptop extends ComputerType {
	public Laptop(Brand brand) {
		super(brand);
	}
	@Override
	public void sale() {
		super.sale();
		System.out.println("销售桌面电脑");
	}
}

class Pad extends ComputerType {
	public Pad(Brand brand) {
		super(brand);
	}
	@Override
	public void sale() {
		super.sale();
		System.out.println("销售平板电脑");
	}
}

//测试类
public class Client {
	public static void main(String[] args) {
//		new Laptop(new Lenovo()).sale();
//		new Pad(new Lenovo()).sale();
		new Laptop(new Dell()).sale();
	}
}
```

- 总结：  
  - 桥接模式可以取代多层继承的方案。 多层继承违背了单一职责原则，复用性较差，类的个数也非常多。桥接模式可以极大的减少子类的个数，从而降低管理和维护的成本。
  - 桥接模式极大的提高了系统可扩展性，在两个变化维度中任意扩展一个维度，都不需要修改原有的系统，符合开闭原则。

- 应用场景：
  - JDBC驱动程序
  - 银行日志管理：
    - 格式分类：操作日志、交易日志、异常日志
    - 距离分类：本地记录日志、异地记录日志
  - 人力资源系统中的奖金计算模块：
    - 奖金分类：个人奖金、团体奖金、激励奖金。
    - 部门分类：人事部门、销售部门、研发部门
  - OA系统中的消息处理：
    - 业务类型：普通消息、加急消息、特急消息
    - 发送消息方式：系统内消息、手机短信、邮件