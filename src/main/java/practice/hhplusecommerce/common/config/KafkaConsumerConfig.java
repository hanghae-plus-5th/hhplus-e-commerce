package practice.hhplusecommerce.common.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import practice.hhplusecommerce.order.business.event.DataPlatformEvent;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String> dataPlatformListenerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConcurrency(1);
    factory.getContainerProperties().setAckTime(3000);
    factory.getContainerProperties().setPollTimeout(500);
    factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(getConsumerConfig("data_platform_group")));
    factory.getContainerProperties().setLogContainerConfig(true);
    factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
    return factory;
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String> dataPlatformOutBoxListenerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConcurrency(1);
    factory.getContainerProperties().setAckTime(3000);
    factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(getConsumerConfig("outbox_group")));
    factory.getContainerProperties().setPollTimeout(500);
    factory.getContainerProperties().setLogContainerConfig(true);
    factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
    return factory;
  }

  private Map<String, Object> getConsumerConfig(String group) {
    Map<String, Object> consumerConfig = new HashMap<>();
    consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, group);
    consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    consumerConfig.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // Adjust this as needed
    consumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    consumerConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
    return consumerConfig;
  }
}
