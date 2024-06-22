package com.example.spannerpubsubperformance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;


@Entity
@Table(name = "user")
@IdClass(UserPk.class)
@Getter
@Setter
@NoArgsConstructor
public class User implements Persistable<UserPk> {

    @Id
    @Column(name = "id_hash", nullable = false)
    private String idHash;

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    // memo:
    // @Transientをつけているので、このフィールドはDBには保存されない
    // 何故かは不明だが、isNewを使用する際にこのフィールドを用意し、isNew()で値を返すような実装にしないと
    // deleteAllでデータの全削除が機能しない挙動を示したので、このような実装にしている
    @Transient
    private boolean isNew;

    public User(String id, String name) {
        this.id = id;
        this.idHash = new UserPk(id).getIdHash();
        this.name = name;
        this.isNew = true; // upsertではなく、insertを使用する設定
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    @Override
    public UserPk getId() {
        return new UserPk(this.id);
    }

}
