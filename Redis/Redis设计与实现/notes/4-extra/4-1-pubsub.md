### 发布订阅

1. 客户端通过subscribe命令可以订阅一个或多个频道，当有其他客户端向被订阅的频道发送消息时，频道所有订阅者都会收到消息。通过unsubscribe退订某个频道。redis将所有的频道订阅关系保存在RedisServer结构的pubsub_channels字典中，键是某个被订阅的频道，值是一个保存所有订阅该频道的订阅者的链表。订阅退订操作本质就是操作pubsub_channels字典实现的。

2. 客户端可以通过psubscribe订阅一个或多个模式，当有客户端向某个频道发送消息时，消息不仅会发送所有的频道订阅者，还会发送给所有与该频道相匹配的模式的订阅者。通过punsubscribe退订某个模式的订阅。模式的订阅关系保存在RedisServer结构中的pubsub_patterns链表中，链表中每一个元素都是pubsubPattern，该结构的pattern属性记录被订阅的模式，client属性记录订阅模式的客户端。

3. 使用publish <channel><message>发送消息。服务器会将消息发送给channel频道的所有订阅者，以及与该channel相匹配的模式的订阅者。publish的本质就是操作pubsub_channels字典或者pubsub_patterns链表实现的。

4. 查看订阅消息的命令

   三个命令都是通过读取pubsub_channels字典或者pubsub_patterns链表的数据实现的。

   - pubsub channels [pattern]返回服务器端被订阅的频道信息。
   - pubsub numsub [channel1,channel2 ...]返回指定channel的订阅者数量。
   - pubsub numpat 返回服务器被订阅的模式的数量。