package com.example.spannerpubsubperformance.helper;

import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;

@Component
public class TransactionListener implements TransactionSynchronization {

  @Override
  public void afterCompletion(int status) {
    // トランザクションが完了した後に実行される
    if (status == STATUS_COMMITTED) {
      System.out.println("Transaction completed with commit");
    } else if (status == STATUS_ROLLED_BACK) {
      System.out.println("Transaction completed with rollback");
    }
  }
}
