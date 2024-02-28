package personal.example.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import personal.example.utils.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.count.partition}")
    private int countPartition;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        return props;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    //register topic
    @Bean
    public NewTopic lowTopic() {
        return TopicBuilder.name(Constant.LOW_BANDWIDTH).partitions(countPartition).replicas(1).build();
    }

    @Bean
    public NewTopic highTopic0() {
        return TopicBuilder.name(Constant.HIGH_BANDWIDTH  + "-" + 0).partitions(countPartition).replicas(1).build();
    }

    @Bean
    public NewTopic highTopic1() {
        return TopicBuilder.name(Constant.HIGH_BANDWIDTH  + "-" + 1).partitions(countPartition).replicas(1).build();
    }

    @Bean
    public NewTopic highTopic2() {
        return TopicBuilder.name(Constant.HIGH_BANDWIDTH  + "-" + 2).partitions(countPartition).replicas(1).build();
    }

    @Bean
    public NewTopic highTopic3() {
        return TopicBuilder.name(Constant.HIGH_BANDWIDTH  + "-" + 3).partitions(countPartition).replicas(1).build();
    }




}
