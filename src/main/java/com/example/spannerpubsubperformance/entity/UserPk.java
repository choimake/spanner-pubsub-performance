package com.example.spannerpubsubperformance.entity;

import lombok.*;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserPk implements Serializable {
    private String idHash;
    private String id;

    public UserPk(String id) {
        this.idHash = computeHash(id);
        this.id = id;
    }

    private String computeHash(String id) {
        return Hex.encodeHexString(DigestUtils.sha256(id));
    }
}
