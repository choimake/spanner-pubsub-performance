package com.example.spannerpubsubperformance.controller;

import com.example.spannerpubsubperformance.controller.request.PublishMessageRequest;
import com.example.spannerpubsubperformance.controller.request.UserRequest;
import com.example.spannerpubsubperformance.service.PublishService;
import com.example.spannerpubsubperformance.service.SaveAndPublishService;
import com.example.spannerpubsubperformance.service.SaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class PerformanceEvaluationController {

  private final SaveService saveService;
  private final PublishService publishService;
  private final SaveAndPublishService saveAndPublishService;

  @Autowired
  public PerformanceEvaluationController(
      SaveService saveService,
      PublishService publishService,
      SaveAndPublishService saveAndPublishService) {
    this.saveService = saveService;
    this.publishService = publishService;
    this.saveAndPublishService = saveAndPublishService;
  }

  @PostMapping
  @RequestMapping("/users/save")
  public ResponseEntity<String> saveUser(@RequestBody UserRequest userRequest) {
    try {
      long startTime = System.currentTimeMillis(); // Start time
      saveService.execute(userRequest.getId(), userRequest.getName());
      long endTime = System.currentTimeMillis(); // End time
      long duration = endTime - startTime; // Calculate duration

      return new ResponseEntity<>(
          "User saved successfully. duration: " + duration + "ms", HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(
          "Error saving user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping
  @RequestMapping("/publish")
  public ResponseEntity<String> publishMessage(
      @RequestBody PublishMessageRequest publishMessageRequest) {

    long startTime = System.currentTimeMillis(); // Start time
    try {
      publishService.execute(publishMessageRequest.getMessage());

      long endTime = System.currentTimeMillis(); // End time
      long duration = endTime - startTime; // Calculate duration

      return new ResponseEntity<>(
          "Message published successfully. duration: " + duration + "ms", HttpStatus.OK);
    } catch (IOException e) {
      return new ResponseEntity<>(
          "Error publishing message: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping
  @RequestMapping("/users/save-and-publish")
  public ResponseEntity<String> saveAndPublishUser(@RequestBody UserRequest userRequest) {
    try {
      long startTime = System.currentTimeMillis(); // Start time
      saveAndPublishService.execute(userRequest.getId(), userRequest.getName());
      long endTime = System.currentTimeMillis(); // End time
      long duration = endTime - startTime; // Calculate duration

      return new ResponseEntity<>(
          "User saved and publish successfully. duration: " + duration + "ms", HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(
          "Error saving user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
