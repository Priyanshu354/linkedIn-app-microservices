package com.priyansu.linkedin.connection_service.auth;

public class UserContextHolder {

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
