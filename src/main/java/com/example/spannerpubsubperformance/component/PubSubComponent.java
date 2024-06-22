package com.example.spannerpubsubperformance.component;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Component
public class PubSubComponent {

  public void publish(String projectId, String topicId, String message) throws IOException {

    Publisher publisher = null;
    try {
      var topicName = TopicName.of(projectId, topicId);
      publisher = Publisher.newBuilder(topicName).build();

      var byteMessage = ByteString.copyFromUtf8(message);
      PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(byteMessage).build();

      var future = publisher.publish(pubsubMessage);
      // Wait on any pending requests
      future.get();

    } catch (ExecutionException | InterruptedException e) {
      // handle exception
      throw new RuntimeException(e);
    } finally {
      // When finished with the publisher, make sure to shut down to free up resources.
      if (publisher != null) {
        publisher.shutdown();
      }
    }
  }
}
