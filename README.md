# redis-pub-sub 发布及订阅消息
发布订阅(pub/sub)是一种消息通信模式，主要的目的是解耦消息发布者和消息订阅者之间的
耦合，这点和设计模式中的观察者模式比较相似。pub/sub 不仅仅解决发布者和订阅者直接
代码级别耦合也解决两者在物理部署上的耦合。redis 作为一个 pub/sub 的 server，在订阅者
和发布者之间起到了消息路由的功能。订阅者可以通过 subscribe 和 psubscribe 命令向 redis 
server 订阅自己感兴趣的消息类型，redis 将消息类型称为通道(channel)。当发布者通过
publish 命令向 redis server 发送特定类型的消息时。订阅该消息类型的全部 client 都会收到
此消息。这里消息的传递是多对多的。一个client可以订阅多个channel,也可以向多个channel
发送消息。
