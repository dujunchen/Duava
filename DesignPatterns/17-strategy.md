## 策略模式
- 本质
  分离算法的实现和使用。
- 策略模式对应于**解决某一个问题的一个算法族**，允许用户从该算法族中任选一个算法解决某一问题，同时可以方便的更换算法或者增加新的算法。并且由客户端决定调用哪个算法。
- 优点
  - 避免使用多重ifelse条件语句。
  - 提供了对开闭原则的完美支持，可以在不修改原代码的情况下，灵活增加新算法。
- 缺点
  - 客户端必须理解所有策略算法的区别，以便适时选择恰当的算法类。
  - 策略模式造成很多的策略类。
- 角色
- 抽象策略（Strategy）类：定义了一个公共接口，各种不同的算法以不同的方式实现这个接口，环境角色使用这个接口调用不同的算法，一般使用接口或抽象类实现。
  
- 具体策略（Concrete Strategy）类：实现了抽象策略定义的接口，提供具体的算法实现。
  
- 环境（Context）类：持有一个策略类的引用，最终给客户端调用。
- 代码实现
```java
/*
  算法族
 */
public interface Strategy {
	double calculate(double a, double b);
}

class SumStrategy implements Strategy {
	@Override
	public double calculate(double a, double b) {
		return a + b;
	}
}

class MinusStrategy implements Strategy {
	@Override
	public double calculate(double a, double b) {
		return a - b;
	}
}

class MultiplyStrategy implements Strategy {
	@Override
	public double calculate(double a, double b) {
		return a * b;
	}
}

class DivideStrategy implements Strategy {
	@Override
	public double calculate(double a, double b) {
		if (b == 0)
			throw new ArithmeticException();
		return a / b;
	}
}

/*
  负责和具体的策略类交互
 */
public class Context {
	private Strategy strategy;
	
	public Context(Strategy strategy) {
		this.strategy = strategy;
	}

	public void result(double a, double b){
		System.out.println("计算结果： " + strategy.calculate(a, b));
	}
}

public class Client {
	public static void main(String[] args) {
		new Context(new SumStrategy()).result(20, 10);
		new Context(new MinusStrategy()).result(20, 10);
		new Context(new MultiplyStrategy()).result(20, 10);
		new Context(new DivideStrategy()).result(20, 10);
	}
}
```