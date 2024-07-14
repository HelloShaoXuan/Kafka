package com.transcosmos.producer;

import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Author：ShaoXuan
 * Time：2024-07-13 21:03
 * Content：事件生产者
 */
@Slf4j
@Component
public class EventProducer {

    @Resource
    private KafkaTemplate<String,String> kafkaTemplate;

    /**
     * Send Event To Kafka
     */
    public void sendEvent(){
        kafkaTemplate.send("topic-shao","SpringBoot-Kafka");
        log.info("Send event success.");
    }

    public void sendEvent2(){
        // 通过建造者模式创建Message对象
        Message<String> message = MessageBuilder.withPayload("HelloKafka")
                .setHeader(KafkaHeaders.TOPIC,"topic-xuan").build();
        kafkaTemplate.send(message);
    }

    public void sendEvent3(){
        Headers headers = new RecordHeaders();
        headers.add("header1","I'm header1".getBytes(StandardCharsets.UTF_8));
        headers.add("header2","I'm header2".getBytes(StandardCharsets.UTF_8));
        // 发送ProducerRecord对象消息
        // String topic, Integer partition, Long timestamp, K key, V value, Iterable<Header> headers
        ProducerRecord<String,String> producerRecord = new ProducerRecord<>("topic-xuan",
                                                            0,
                                                                    System.currentTimeMillis(),
                                                            "key1",
                                                            "sendMessageByProducerRecord",
                                                            headers);
        kafkaTemplate.send(producerRecord);
    }

    public void sendEvent4(){
        //String topic, Integer partition, Long timestamp, K key, V data
        kafkaTemplate.send("topic-xuan",0,System.currentTimeMillis(),"key1","key1HelloWorld");
    }

    public void sendEvent5(){
        // Integer partition, Long timestamp, K key, V data
        kafkaTemplate.sendDefault(0,System.currentTimeMillis(),"key1","key1HelloWorld2222354");
    }

    // 阻塞模式获取生产者发送消息的结果
    public void sendEvent6(){
        Headers headers = new RecordHeaders();
        headers.add("header01","header01Message".getBytes(StandardCharsets.UTF_8));
        // String topic, Integer partition, Long timestamp, K key, V value, Iterable<Header> headers
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("topic-shao",0,System.currentTimeMillis(),
                "keyKey","ShaoSir",headers);
        // 发消息
        CompletableFuture<SendResult<String, String>> completableFuture = kafkaTemplate.send(producerRecord);
        try {
            // Waits if necessary for this future to complete, and then returns its result.
            // 阻塞模式获取生产者发送消息的结果
            // 等待Kafka处理完消息后sendResult才会有结果，代码才会继续往下执行
            log.info("Get result start:"+String.valueOf(System.currentTimeMillis()));
            SendResult<String, String> sendResult = completableFuture.get();
            log.info("Get result end:"+System.currentTimeMillis());
            // 通过sendResult可以获取消息
            ProducerRecord<String, String> producerRecord1 = sendResult.getProducerRecord();
            log.info("ProducerRecord:"+producerRecord1.toString());
            // 通过获取元数据来判断Kafka是否成功收到消息
            RecordMetadata recordMetadata = sendResult.getRecordMetadata();
            if (recordMetadata != null){
                log.info("Kafka:Receive message success!");
                log.info(recordMetadata.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
