package com.priyanshu.linkedin.notification_service.auth;

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
