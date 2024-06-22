package com.example.spannerpubsubperformance.service;

import com.example.spannerpubsubperformance.component.PubSubComponent;
import com.example.spannerpubsubperformance.entity.User;
import com.example.spannerpubsubperformance.helper.TransactionListener;
import com.example.spannerpubsubperformance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.IOException;

@Service
public class SaveAndPublishService {

  @Value("${pubsub.project-id}")
  private String projectId;

  @Value("${pubsub.topic-id}")
  private String topicId;

  private final UserRepository userRepository;

  private final PubSubComponent pubSubComponent;

  private final TransactionListener transactionListener;

  public SaveAndPublishService(
      UserRepository userRepository,
      PubSubComponent pubSubComponent,
      TransactionListener transactionListener) {
    this.userRepository = userRepository;
    this.pubSubComponent = pubSubComponent;
    this.transactionListener = transactionListener;
  }

  @Transactional()
  public void execute(String userId, String userName) throws IOException {

    // トランザクションリスナーを登録
    // トランザクションがcommitされたか、rollbackされたかを確認するために導入した
    TransactionSynchronizationManager.registerSynchronization(transactionListener);

    // この実装だと、
    // Spannerの保存に失敗 -> Pub/Subのpublishに成功した場合、
    // Spannerにはデータが保存されないが、Pub/Subにはメッセージが送信されるという挙動が発生する（動作確認した）
    // 理由は、トランザクションの実行タイミングがメソッド終了後になるため（ログの出力順による推定）
    var user = new User(userId, userName);
    userRepository.save(user);
    pubSubComponent.publish(projectId, topicId, "User saved: " + userId);
    System.out.println("execute end");
  }
}
