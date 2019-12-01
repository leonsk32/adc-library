package com.example.library.domain.user;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public class User {
    @Getter
    private final String userId;
    @Getter
    private final String email;
    @Getter
    private String simei;
    @Getter
    private String namae;

    public User(String userId, String email, String simei, String namae) {
        if(userId.length() > 7) {
            throw new RuntimeException("userIdは７桁");
        }
        this.userId = userId;
        this.email = email;
        this.simei = simei;
        this.namae = namae;
    }

    public User(String userId, String email) {
        if(userId.length() > 7) {
            throw new RuntimeException("userIdは７桁");
        }
        this.userId = userId;
        this.email = email;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof User)) throw new RuntimeException();
        User otherUser = (User) other;
        return this.userId.equals(otherUser.userId);
    }

    public String getFullName() {
        return StringUtils.trimToEmpty(this.simei) + StringUtils.trimToEmpty(this.namae);
    }
}
