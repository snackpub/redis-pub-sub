## redis-pub-sub 发布及订阅消息
发布订阅(pub/sub)是一种消息通信模式，主要的目的是解耦消息发布者和消息订阅者之间的
耦合，这点和设计模式中的观察者模式比较相似。pub/sub 不仅仅解决发布者和订阅者直接
代码级别耦合也解决两者在物理部署上的耦合。redis 作为一个 pub/sub 的 server，在订阅者
和发布者之间起到了消息路由的功能。订阅者可以通过 subscribe 和 psubscribe 命令向 redis 
server 订阅自己感兴趣的消息类型，redis 将消息类型称为通道(channel)。当发布者通过
publish 命令向 redis server 发送特定类型的消息时。订阅该消息类型的全部 client 都会收到
此消息。这里消息的传递是多对多的。一个client可以订阅多个channel,也可以向多个channel
发送消息。


###### 环境依赖
JDK1.8   
Redis version 3.0.503  
安装 Lombok 插件   
修改redis、mysql连接账户密码

## 工程结构

``` 
Redis-Pub-Sub
├── core-boot -- 常用工具封装包
├── core-coupon-achieve -- 优惠券发布模块
├── core-coupon-expired -- 优惠券逾期模块
└── core-coupon-api -- 优惠券api 
```

## redis key过期事件
```__keyevent@0__:expired```
##### 修改配置文件
找到redis.conf[windows:redis.windows.conf]配置文件,找到   
```notify-keyspace-events Elg``` 默认被注释的，取消注释，[Elg]选择对应的参数替换就好了
```
具体参数如下：
# K    键空间通知，以__keyspace@<db>__为前缀  
# E    键事件通知，以__keysevent@<db>__为前缀
# g    del , expipre , rename 等类型无关的通用命令的通知, ...
# $    String命令
# l    List命令
# s    Set命令
# h    Hash命令
# z    有序集合命令
# x    过期事件（每次key过期时生成）
# e    驱逐事件（当key在内存满了被清除时生成）
# A    g$lshzxe的别名，因此”AKE”意味着所有的事件
```

##### 使用redis-cli开启事件监听
```config set notify-keyspace-events KEA```

注意：如何不设置的话就算程序中开启了监听也不会进行调用！！

## 测试
0. 测试redis是否正常连接 core-coupon-expired 模型 redis 测试类
1. 启动 core-coupon-expired 模型 CouponExpiredApplication.java
2. 运行 core-coupon-achieve 测试类