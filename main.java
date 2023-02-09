import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConsumerExample {
    public static void main(String[] args) {
        // String bootstrap_server = "kafka.apps.proddc.customs.go.id:443";
        // String consumer_group = "my-group";
        String topic = "test";
        String username = "kafka";
        String password = "kafka-secret";
        
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka.apps.proddc.customs.go.id:443");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
        props.put("sasl.mechanism", "PLAIN");
        props.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=kafka password=kafka-secret;");
        // props.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=" + username + "password="password +";");
        props.put("ssl.endpoint.identification.algorithm", "HTTPS");
        // props.put("ssl.truststore.location", "/path/to/truststore.jks");
        // props.put("ssl.truststore.password", "truststore-password");
        // props.put("ssl.keystore.location", "/path/to/keystore.jks");
        // props.put("ssl.keystore.password", "keystore-password");
        // props.put("ssl.key.password", "key-password");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("offset = " + record.offset() + ", key = " + record.key() + ", value = " + record.value());
            }
        }
    }
}
