package com.example.spannerpubsubperformance.service;

import com.example.spannerpubsubperformance.component.PubSubComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PublishService {

  private final PubSubComponent pubSubComponent;

  @Value("${pubsub.project-id}")
  private String projectId;

  @Value("${pubsub.topic-id}")
  private String topicId;

  public PublishService(PubSubComponent pubSubComponent) {
    this.pubSubComponent = pubSubComponent;
  }

  public void execute(String message) throws IOException {
    pubSubComponent.publish(projectId, topicId, message);
  }
}
