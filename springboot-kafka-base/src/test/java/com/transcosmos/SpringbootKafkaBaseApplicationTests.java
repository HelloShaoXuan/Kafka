package com.transcosmos;

import com.transcosmos.producer.EventProducer;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootKafkaBaseApplicationTests {

    @Resource
    private EventProducer eventProducer;

    @Test
    void eventProducerTest(){
        eventProducer.sendEvent();
    }

    @Test
    void testSend02(){
        eventProducer.sendEvent2();
    }

    @Test
    void testSend03(){
        eventProducer.sendEvent3();
    }

    @Test
    void testSend04(){
        eventProducer.sendEvent4();
    }

    @Test
    void testSend05(){
        eventProducer.sendEvent5();
    }

    @Test
    void testSend06(){
        eventProducer.sendEvent6();
    }

}
