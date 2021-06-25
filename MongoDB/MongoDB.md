# MongoDB

## 安装配置

## 概念

- 数据库database
- 集合collection
- 文档document

## 基本指令

- show dbs(或者databases)
显示所有databases
- use <db_name>
使用指定的db
- db
当前的db
- show collections
显示数据库中所有集合
- db.<collection>.drop()
删除collection

## CRUD操作

- 插入
  - db.<collection>.insert(document)    向集合中插入一个文档
  - db.<collection>.insertOne(document)
  - db.<collection>.insertMany(documentList)  
  如果没有指定_Id，会自动生成，如果指定了mongodb就不会生成了。
  也可以执行ObjectId()方法生成。
- 查询
  - db.<collection>.find({})  查询当前集合中符合条件文档，默认按照_id升序。
    - {}为空表示查询所有，可以省略。{属性:值}表示查询该属性的值等于或者包含指定值的文档，可以使用.形式匹配内嵌文档，此时属性名称必须加引号，多个AND条件用逗号隔开。
    - 返回结果是一个Document数组。
  - db.person.findOne() 查询返回符合条件的第一个文档对象。返回结果是一个Document对象。
- 计数  
  - db.person.find({}).count()  计数
- 分页
  - skip()
  - limit()
- 排序
  - sort()
     - db.user.find({}).sort({"age":1,"username":-1}) 
     1表示升序，-1表示降序。前面的先排序，后面的后排序。 
  - skip()、limit()、sort()可以按照任意顺序调用。
- 投影
  - db.user.find({},{"username":1,"_id":0}) 查询结果只显示username字段。0表示不显示，1表示显示。

- 修改
  - db.<collection>.update(查询条件,新对象
      - 默认只修改第一个符合条件的文档，可以使用multi属性设置修改多个。
      - 默认采用替换方式修改，如果不想使用替换方式，可以使用修改操作符（具体参考MongoDB官方文档）。
          - $set    修改文档中指定属性
          - $unset  删除文档中指定属性
          - $rename 重命名字段名
          - $push   往数组中添加一个元素，不考虑重复元素
          - $addToSet   往数组中添加一个元素，重复元素不会添加
  - db.person.updateMany
  - db.person.updateOne

- 删除
  - db.person.remove({})  默认会删除符合条件的所有文档，第二个参数设置为true，则只删除第一个文档。remove()必须要传参数。
  - db.person.deleteOne()
  - db.person.deleteMany()

