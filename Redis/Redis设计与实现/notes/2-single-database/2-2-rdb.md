### RDB持久化

1. RDB持久化既可以手动执行（save和bgsave），也可以根据配置定期执行。RDB文件是一个经过压缩的二进制文件，保存了某个时间点的数据库状态。

   - save和bgsave的区别：

     save会阻塞redis 服务器进程，直至RDB文件创建完毕。在阻塞期间，服务器不能处理其他请求。而bgsave会派生一个子进程专门负责创建RDB文件，不会阻塞父进程。但是在bgsave执行期间，save、bgsave、bgrewriteaof命令方式会有所不同。在bgsave执行期间，客户端发送的save和bgsave命令会被拒绝。因为同时执行save和bgsave或者两个bgsave会同时调用rdbSave函数，产生竞争。另外，bgsave和bgrewriteaof不能同时执行，如果在bgsave正在执行，bgrewriteaof会被延迟。如果bgrewriteaof正在执行，bgsave会被拒绝。因为bgsave和bgrewriteaof都是大量的磁盘IO操作，同时开启两个子进程执行bgsave和bgrewriteaof性能很差。

2. redis没有专门的载入RDB文件的命令，而是在redis服务器启动时检测到RDB文件自动执行载入。但是如果同时开启了AOF和RDB持久化功能，服务器会优先使用AOF文件还原数据库状态。只有AOF功能关闭，才会使用RDB还原。因为AOF文件的更新频率一般比RDB文件高，数据更新。

3. 自动间隔性保存：通过配置save选项设置多个保存条件，只要其中任意一个条件满足，服务器就自动会执行一次bgsave操作。redisServer结构中维护一个saveparams数组，每个元素保存一个saveparam结构，saveparam结构包含seconds和changes属性，分别保存save条件的秒数和修改数。另外redisServer维持一个dirty属性和lastsave属性，dirty记录上一次成功执行save或者bgsave后服务器对数据库状态的修改次数。lastsave记录上一次成功执行save或者bgsave的时间。serverCron函数会每隔100ms遍历saveparams，如果条件满足就会执行bgsave操作。

4. RDB文件的结构

   一个完整的RDB文件包含以下几部分：REDIS标识、db_version、databases、EOF、check_sum。

   （1）RDB文件以REDIS标识开头，占5个字节，保存REDIS5个字符，作用是在载入文件时快速检查是否是RDB文件。

   （2）db_version长度4个字节，表示RDB文件的版本号。

   （3）databases包含零个或者多个的数据库以及各个数据库的键值对数据。长度根据数据库数据量不定。也有可能为空。每个非空数据库在RDB中的结构包含SELECTDB、db_number、key_value_pairs。

   - SELECTDB常量占一个字节，表示后面紧跟的是一个db_number。
   - db_number保存一个数据库号码。根据号码不同，长度可能是1、2、5个字节。当读取该号码时，会调用SELECT切换到对应的数据库。
   - key_value_pairs保存数据库中所有的键值对，包括带有过期时间的键值对。
     1. 不带过期时间的键值对在RDB中由type、key、value组成。
        - type占一个字节，记录value的类型编码。
        - key保存键值对的键对象。
        - value保存键值对的值对象。value的结构和长度取决于type。
     2. 带过期时间的键值对在RDB中由EXPIRETIME_MS、ms、type、key、value组成。
        - type、key、value的含义和前面一样。
        - EXPIRETIME_MS占一个字节，表示后面紧跟的是一个毫秒为单位的过期时间。
        - ms占8个字节，表示键值对的过期时间。

（4）EOF常量长度1个字节，表示RDB文件正文内容（数据库所有键值对）载入完毕。

（5）check_sum占8个字节，保存一个校验和，这个校验和是通过前四部分计算而得，在载入文件时，会计算载入数据的校验和与check_sum所记录的校验和比较，以此检查RDB文件是否有出错或者损坏。