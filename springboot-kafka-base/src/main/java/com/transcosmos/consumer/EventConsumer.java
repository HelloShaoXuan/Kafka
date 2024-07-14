package com.transcosmos.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Author：ShaoXuan
 * Time：2024-07-13 22:06
 * Content：事件消费者
 */
@Slf4j
@Component
public class EventConsumer {

    /**
     * 采用监听的方式接收事件
     * 默认情况：
     *  当启动一个新的消费者组时，它会从每个分区的最新偏移量（即该分区中最后一条消息的下一个位置）开始消费。
     *  即默认消费不到之前的消息
     *  如果希望从第一条消息开始消费，需要将消费者的spring.kafka.consumer.auto.offset.reset设置为earliest
     * 注意：
     *  如果之前已经用相同的消费者组ID消费过该主题，并且Kafka已经保存了该消费者组的偏移量，
     *  那么即使设置了spring.kafka.consumer.auto.offset.reset=earliest，该设置也不会生效。
     *  因为Kafka只会在找不到偏移量时使用这个配置。
     *  所以，在这种情况下需要手动重置偏移量或使用一个新的消费者组ID
     * @param event 事件（消息/数据）
     */
    @KafkaListener(topics = "topic-shao", groupId = "shao-group")
    public void onEvent(String event){
        log.info("Consume event:"+event);
    }

}
