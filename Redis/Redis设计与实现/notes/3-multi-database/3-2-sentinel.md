### Sentinel

1. sentinel是redis一种高可用解决方案，由一个或者多个sentinel实例组成一个系统，监控多个主服务器以及这些主服务器属下所有从服务器。并在主服务器下线状态时，自动将其属下某个从服务器升级成新的主服务器，有新的主服务器代替已下线服务器处理命令请求。

2. sentinel初始化过程
   
   可以使用redis-sentinel sentinel.conf或者redis-server sentinel.conf --sentinel启动一个sentinel实例。
   
   - 初始化服务器
     
     sentinel是一个特殊的redis服务器。首先会初始化一个普通的redis服务器，不过初始化过程会和普通服务器有区别。比如普通服务器启动时会载入RDB或者AOF文件，但是sentinel不会。
   
   - 将普通服务器使用的代码替换成sentinel专用代码。
   
   - 初始化sentinel状态。
     
     sentinelState结构保存了服务器所有和sentinel功能相关的状态，但是服务器的一般状态还是保存在redisServer结构中。
   
   - 根据配置文件初始化sentinel的监视主服务器列表。
     
     sentinelState结构中的masters字典记录所有被sentinel监视的主服务器的相关信息。字典的键时被监视主服务器名称，值是一个sentinelRedisInstance结构。每一个sentinelRedisInstance表示一个被sentinel监视的实例，可以是主服务器、从服务器、另外一个sentinel。sentinelRedisInstance的addr属性一个sentinelAddr结构，这个结构保存实例的ip和port。sentinel的初始化会导致maters字典的初始化，而maters字典的初始化是根据载入的sentinel.conf配置来的。
   
   - 创建连向主服务器的网络连接。
     
     sentinel会创建两个连向主服务器的异步连接，命令连接和订阅连接。命令连接用于向主服务器发送命令并接收命令回复。订阅连接专门用于订阅主服务器的\__sentinel__:hello频道。

3. 获取主服务器信息
   
   默认10秒一次向主服务器发送info命令，通过分析回复获取主服务器信息。回复信息包括：（1）主服务器本身的信息，如runid、role。sentinel会根据这些信息对主服务器的sentinelRedisInstance结构进行更新。（2）主服务器属下所有从服务器的信息，sentinel无需用户提供从服务器信息就可以自动发现这些从服务器。这些信息会被用于更新主服务器的sentinelRedisInstance结构的slaves字典。slaves字典记录主服务器下所有从服务器列表，字典的键是sentinel自动设置从服务器名称，格式ip:port。值为从服务器对应的sentinelRedisInstance结构。

4. 获取从服务器信息
   
   默认10秒一次向从服务器发送info命令，通过分析回复获取从服务器信息。

5. 向主服务器和从服务器发布消息
   
   默认2秒一次向所有被监视的主、从服务器发送publish \__sentinel__:hello命令

6. 接收主、从服务器的频道信息
   
   - sentinel会订阅每一个连接的主、从服务器的 \__sentinel__:hello频道，对于每一个与sentinel连接的服务器，sentinel既通过命令向\__sentinel__:hello频道发送消息，也会订阅\__sentinel__:hello频道消息。
   - 对于监视同一个服务器的多个sentinel来说，一个sentinel发送的信息会被其他sentinel接收到，这些信息会被用于更新其他sentinel对发送sentinel的认知，也会用于更新其他sentinel对被监视服务器的认知。因此用户无需提供各个sentinel的地址信息，监视同一个服务器的多个sentinel可以自动相互发现。
   - 当sentinel通过频道信息发现新的sentinel，他会在自己服务器所对应的sentinelRedisInstance结构的sentinels字典中创建该新sentinel的实例结构，还会创建连向新sentinel的命令连接，新的sentinel也会创建连向这个sentinel的连接，最终监视同一个服务器的所有的sentinel会形成相互连接的网络。

7. 检测主观下线
   
   - 默认sentinel会一秒一次向所有相连的实例发送ping，通过回复判断实例是否在线。sentinel配置down-after-millseconds表示实例在连续的指定时间内向sentinel返回无效回复，sentinel会将其所对应的实例结构的flags属性打开SRI_S_DOWN标识，表示这个实例进入主观下线状态。
   - down-after-millseconds不仅是用来判断主服务器主观下线的标准，也会用于判断该主服务器属下所有从服务器以及所有监视该主服务器的其他sentinel的主观下线标准。
   - 多个sentinel设置的down-after-millseconds时长可能不同。

8. 检查客观下线
   
   - sentinel将一个主服务器判断主观下线后，会发送命令sentinel is-master-down-by-addr给同样监视该服务器的其他sentinel，并根据其他sentinel的命令回复，统计同意该服务器下线的数量，如果数量超过sentinel配置中quonum的值，那么sentinel会将该主服务器实例结构的flags的SRI_O_DOWN标识打开，表示该主服务器进入客观下线状态。
   - 不同的sentinel判断客观下线的条件可能不同。

9. 选举leader sentinel
   
   当一个主服务器被判定客观下线后，需要从监视该下线服务器所有sentinel中选举leader处理故障转移。
   
   - 监视下线服务器的各个sentinel都有机会称为leader sentinel。
   - 每轮选举过后，不管选举是否成功，所有的sentinel的配置纪元都会自增一次。
   - 在一个纪元里面，所有sentinel都有一次机会将某个其它sentinel设置为自己的局部leader，一旦设置，同一纪元中无法再修改。
   - 一个sentinel（源sentinel）向另一个sentinel（目标sentinel）发送sentinel is-master-down-by-addr并带上自己的runid，表示源sentinel要求目标sentinel将自己设置为目标sentinel的局部leader。目标sentinel接收到源sentinel的命令后，会发送给源sentinel一个回复，回复包含目标sentinel的局部leader sentinel的runid和配置纪元。设置局部leader采用先到先得的规则，先向目标sentinel发送命令的sentinel会成为其局部leader，之后发送的都会被拒绝。源sentinel接收到目标sentinel的回复后，会对比回复中的配置纪元和runid，如果和自己的配置纪元和runid一致，说明目标sentinel已经将自己设置为其局部leader。
   - 如果某个sentinel被半数以上的sentinel设置为局部sentinel，那么这个sentinel将成为真正的sentinel leader。如果在给定时限内没有一个sentinel被选举为leader sentinel，那么会在一段时间后再次选举，直到选举出leader sentinel为止。

10. 故障转移
    
    选举产生的leader sentinel会对已下线主服务器执行故障转移。包含三个步骤：
    
    - 在已下线的主服务器属下所有从服务器中选出一个从服务器，将其转换成主服务器。
      
      leader会将已下线的主服务器属下所有从服务器维护在一个列表中，按照是否在线，最近成功通信状态，数据比较新，从服务器优先级，复制偏移量等因素进行过滤挑选出最合适的服务器作为新的主服务器。
    
    - 让已下线主服务器属下所有从服务器改为复制新的主服务器。
      
      向已下线的主服务器属下所有从服务器发送slaveof命令让其复制新的主服务器。
    
    - 已下线主服务器设置为新主服务器的从服务器，当旧服务器重新上线后会成为新主服务器的从服务器。