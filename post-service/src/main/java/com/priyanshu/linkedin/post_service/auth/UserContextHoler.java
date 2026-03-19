package com.priyanshu.linkedin.post_service.auth;

import lombok.AccessLevel;

public class UserContextHoler {

    private static final ThreadLocal<Long> currentUser = new ThreadLocal<>();


    public static Long getCurrentUserId() {
        return currentUser.get();
    }

    public static void setCurrentUser(Long userId) {
        currentUser.set(userId);
    }

    public static void clear() {
        currentUser.remove();
    }
}
