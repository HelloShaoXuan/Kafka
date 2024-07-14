Kafka：
    GitHub：
        - 创建GitHub仓库
        - cd 项目根目录
        - 初始化Git仓库
            git init
        - 添加远程仓库
            git remote add origin https://github.com/HelloShaoXuan/Kafka.git
        - 添加并提交文件
            git add .
            git commit -m "Initial commit"
        - 推送到GitHub
            git push -u origin master

        更新代码后上传
        - git add .
        - git commit -m "Updated some files with new features"
        - git push origin master

    一、Kafka安装目录：
        /usr/local/kafka_2.13-3.7.0

    二、Zookeeper方式启动Kafka
        1. 启动Zookeeper
            ./zkServer.sh start
        2. 启动Kafka
            ./kafka-server-start.sh ../config/server.properties &
        3. 关闭Kafka
            ./kafka-server-stop.sh ../config/server.properties
        4. 关闭Zookeeper
            ./zkServer.sh stop

    三、KRaft方式启动Kafka
		1. 生成Cluster UUID
			./kafka-storage.sh random-uuid
			执行完生成UUID的命令后，在/tmp/kraft-combined-logs目录会生成相关文件。
			其中UUID保存在/tmp/kraft-combined-logs/meta.properties文件中
		2. 格式化日志目录
			./kafka-storage.sh format -t UUID -c ../config/kraft/server.properties
			也可以跳过第一步，直接自定义UUID
			./kafka-storage.sh format -t shaoxuan -c ../config/kraft/server.properties
		3. 启动Kafka
			./kafka-server-start.sh ../config/kraft/server.properties &
			& 表示后台启动
			启动成功后可以通过 ps -ef | grep kafka 命令查看进程
			然后通过 netstat -nlpt 命令查看端口号
		4. 关闭Kafka
			./kafka-server-stop.sh ../config/kraft/server.properties

	四、kafka-topics.sh （主题）
		1. 介绍
			kafka-topics.sh脚本可以进行主题的增删改查
		2. 不带任何参数会告知脚本如何使用
			./kafka-topics.sh
		3. 常用参数：
			Option					Description
			--bootstrap-server		(REQUIRED)The Kafka server to connect to.
			--create				Create a new topic.
			--delete				Delete a topic.
			--alter					Update the configuration of an existing topic.
			--partitions			The number of partitions for the topic being created or altered.
			--describe				List details for the given topics.
			--list					List all available topics.
			--topic					The topic to create, alter, describe or delete.
		4. 创建主题
			./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic topic-shao
		5. 删除主题
			./kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic topic-shao
		6. 显示主题详细信息
			./kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic topic-shao
		7. 修改主题分区数量
		    ./kafka-topics.sh --bootstrap-server localhost:9092 --alter --topic topic-shao --partitions 3
		8. 查看所有主题
			./kafka-topics.sh --bootstrap-server localhost:9092 --list

	五、kafka-console-producer.sh （生产者）
		1. 介绍
			kafka-console-producer.sh可以与Kafka Brokers进行通信，向Topic（主题）发送Event（事件/消息）
			Kafka Brokers一旦接收到Event（事件/消息），就会将Event以持久和容错的方式存储起来（永久）。
		2. 不带任何参数会告知脚本如何使用
			./kafka-console-producer.sh
		3. 常用参数：
			Option					Description
			--bootstrap-server		(REQUIRED)The server to connect to.
			--topic					(REQUIRED)The topic id to produce messages to.
		4. Send Event
			./kafka-console-producer.sh --bootstrap-server localhost:9092 --topic topic-shao
			每一次换行是一个Event
			按CTRL+C退出，停止发送消息

	六、kafka-console-consumer.sh （消费者）
		1. 介绍
			kafka-console-consumer.sh可以与Kafka Brokers进行通信，读取某个Topic中的Event
		2. 不带任何参数会告诉脚本如何使用
			./kafka-console-consumer.sh
		3. 常用参数
			Option					Description
			--bootstrap-server		(REQUIRED)The server to connect to.
			--topic					The topic to consume on.
			--from-beginning		If the consumer
									does not already have an established offset to consume from,
									start with the earliest message present in the log
									rather than the latest message.
									（加上这个参数表示从Kafka的某个Topic最早的Event开始消费，
									没加这个参数表示从当前时刻开始消费）
		4. Consume Event
	        ./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic topic-shao --from-beginning
	        ./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic topic-xuan --from-beginning
			Event是持久存储在Kafka中的，所以Events可以被任意多次消费。

    七、kafka-consumer-groups.sh
        1. 介绍
            kafka-consumer-groups.sh可以监控和管理消费者组的健康状况、消费进度以及重平衡等操作。
        2. 不带任何参数会告诉脚本如何使用
            kafka-consumer-groups.sh
        3. 常用参数
            Option					Description
            --bootstrap-server      (REQUIRED)The server to connect to.
            --list                  List all consumer groups.
            --describe              Describe consumer group and
                                    list offset lag (number of messages not yet processed) related to given group.
                                    必须结合--groups/all-groups参数一起使用
            --group                 The consumer group we wish to act on.
            --state                 When specified with '--describe',includes the state of the group.
            --topic                 The topic whose consumer group information should be deleted
                                    or topic whose should be included in the reset offset process.
            --reset-offsets         Reset offsets of consumer group.
                                    You must choose one of the following reset specifications:
                                        --to-datetime,
                                        --by-duration,
                                        --to-earliest,
                                        --to-latest,
                                        --shift-by,
                                        --from-file,
                                        --to-current,
                                        --to-offset.
            --execute               Execute reset-offsets operation.
       4. 列出所有消费者组
            ./kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list
       5. 查看某个消费者组的偏移量
            ./kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group shao-group
       6. 查看某个消费者组的当前状态
            ./kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group shao-group --state
       7. 重置某个消费者对某个主题的偏移量
            ./kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group shao-group --topic topic-shao --reset-offsets --to-earliest --execute

	八、SpringBoot集成Kafka开发
	    1. 修改Kafka配置文件：/usr/local/kafka_2.13-3.7.0/config/kraft/server.properties
	        listeners=PLAINTEXT://0.0.0.0:9092
	        advertised.listeners=PLAINTEXT://192.168.101.131:9092
	    2. kafkaTemplate.send()和kafkaTemplate.sendDefault()的区别？
	        主要区别是发送消息到Kafka时是否每次都需要指定主题Topic
	        kafkaTemplate.send()方法需要明确地指定要发送消息的目标主题Topic，适用于需要根据业务逻辑或外部输入动态确定消息目标Topic的场景
	        kafkaTemplate.sendDefault()方法不需要指定要发送消息的目标主题Topic，适用于总是将消息发送到特定默认Topic的场景
	    3. 获取生产者发送消息结果
	        - 阻塞模式
	        - 非阻塞模式


