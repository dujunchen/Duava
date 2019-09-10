## 组合模式
- 又叫作部分-整体模式，它是一种将对象组合成树状的层次结构的模式，用来表示“部分-整体”的关系，使用户对单个对象和组合对象具有一致的访问性。

- 组合模式分为透明式组合模式和安全式组合模式。透明式组合模式中，顶层抽象构件包含了所有子类的方法，客户端不用区分是叶子节点还是容器节点，是透明访问的。缺点是叶子节点并没有对子对象的管理功能，但是依然需要实现add、remove、getChild之类方法（空实现或者抛异常），会带来一些安全问题。安全式组合模式将对子对象的管理方法移到了容器节点，顶层抽象构件只定义公共方法。这样避免了透明式的问题，但是这样客户端访问前需要区别是容器节点还是叶子节点。

- 角色
  - 抽象构件(Component)角色: 定义了叶子和容器构件的共同点
  - 叶子(Leaf)构件角色：无子节点
  - 容器(Composite)构件角色： 有容器特征，可以包含子节点
  
- 应用场景

  - 操作系统的资源管理器

  - GUI中的容器层次图

  - XML文件解析

  - OA系统中，组织结构的处理

  - Junit单元测试框架

- 代码实现
```java
//通用抽象接口 组件
public interface AbstractFile {
	void killVirus();
}
//叶子节点 组件
class ImageFile implements AbstractFile {
	private String name;
  
	public ImageFile(String name) {
		this.name = name;
	}
	@Override
	public void killVirus() {
		System.out.println("---图像文件：" + name + ",进行查杀！");
	}
}
//叶子节点 组件
class TextFile implements AbstractFile {
	private String name;

	public TextFile(String name) {
		this.name = name;
	}
	@Override
	public void killVirus() {
		System.out.println("---文本文件：" + name + ",进行查杀！");
	}
}
//容器节点 组件
class Folder implements AbstractFile {
	private String name;
	private List<AbstractFile> subFiles;

	public Folder(String name) {
		this.name = name;
		subFiles = new ArrayList<AbstractFile>();
	}
	public void add(AbstractFile file) {
		subFiles.add(file);
	}
	public void remove(AbstractFile file) {
		subFiles.remove(file);
	}
	public AbstractFile getChild(int index) {
		return subFiles.get(index);
	}
	@Override
	public void killVirus() {
		System.out.println("---文件夹：" + name + ",进行查杀");
		for (AbstractFile file : subFiles) {
			file.killVirus();
		}
	}
}

public class Client {
	public static void main(String[] args) {
		Folder folder = new Folder("我的收藏");
		AbstractFile image = new ImageFile("xxx.jpg");
		AbstractFile text = new TextFile("xxx.txt");
		folder.add(image);
		folder.add(text);
		Folder folder2 = new Folder("子文件夹");
		AbstractFile image2 = new ImageFile("xxx2.jpg");
		AbstractFile text2 = new TextFile("xxx2.txt");
		folder2.add(image2);
		folder2.add(text2);
		folder.add(folder2);
		folder.killVirus();
	}
}
```