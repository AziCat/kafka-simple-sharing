# Kafka简单分享

## 前言
由于公司技术架构变化并推行微服务架构的原因，现在和以后使用到`消息中间件`的
情况会变得很常见。在这么多常用的`消息中间件`中，**Kafka**也算是业界标杆，所以
今天来跟同事们介绍一下**Kafka**。

因为大部分同事可能对**Kafka**比较陌生，所以此次分享的内容会比较简单。大概介绍一下
其`部署`，`相关名词`介绍和`简单使用`，让大家心里有数，等到实际使用的时候可以
再去自行探究。(~~前些日子跑去出差了，摸了好久才把这个摸出来了，大家将就着看吧~~:laughing:。

## 目录
* Kafka简介
* 部署
* Producer API使用
* Customer API使用
* FAQ
* Reference

## Kafka简介

**Apache Kafka® 是 一个分布式流处理平台. 这到底意味着什么呢?**

我们知道流处理平台有以下三种特性:
* 可以让你发布和订阅流式的记录。这一方面与消息队列或者企业消息系统类似。
* 可以储存流式的记录，并且有较好的容错性。
* 可以在流式记录产生时就进行处理。

Kafka适合什么样的场景?

它可以用于两大类别的应用:

* 构造实时流数据管道，它可以在系统或应用之间可靠地获取数据。 (相当于message queue)
* 构建实时流式应用程序，对这些流数据进行转换或者影响。 (就是流处理，通过kafka stream topic和topic之间内部进行变化)

首先是一些概念:

* Kafka作为一个集群，运行在一台或者多台服务器上.
* Kafka 通过 topic 对存储的流数据进行分类。
* 每条记录中包含一个key，一个value和一个timestamp（时间戳）。

Kafka有四个核心的API:

* **Producer API** 允许一个应用程序发布一串流式的数据到一个或者多个Kafka `topic`。
* **Consumer API** 允许一个应用程序订阅一个或多个 `topic` ，并且对发布给他们的流式数据进行处理。
* **Streams API** 允许一个应用程序作为一个流处理器，消费一个或者多个`topic`产生的输入流，然后生产一个输出流到一个或多个`topic`中去，在输入输出流中进行有效的转换。
* **Connector API** 允许构建并运行可重用的生产者或者消费者，将Kafka `topics`连接到已存在的应用程序或者数据系统。比如，连接到一个关系型数据库，捕捉表（table）的所有变更内容。

![](resource/kafka-apis-t.png)

### Kafka相关名词分析

* Broker：Kafka节点，一个Kafka节点就是一个broker，多个broker可以组成一个Kafka集群
* Topic：一类消息，消息存放的目录即主题，例如page view日志、click日志等都可以以topic的形式存在，Kafka集群能够同时负责多个topic的分发
* massage： Kafka中最基本的传递对象。
* Partition：topic物理上的分组，一个topic可以分为多个partition，每个partition是一个有序的队列
* Segment：partition物理上由多个segment组成，每个Segment存着message信息
* Producer : 生产者，生产message发送到topic
* Consumer : 消费者，订阅topic并消费message, consumer作为一个线程来消费
* Consumer Group：消费者组，一个Consumer Group包含多个consumer
* Offset：偏移量，理解为消息partition中的索引即可
