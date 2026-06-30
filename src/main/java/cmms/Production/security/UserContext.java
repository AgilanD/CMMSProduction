package cmms.Production.security;


public class UserContext {

    private static final ThreadLocal<Long> currentUserId = new ThreadLocal<>();

    public static void setUserId(Long userId) {
        currentUserId.set(userId);
    }

    public static Long getUserId() {
        return currentUserId.get() != null ? currentUserId.get() : 1L;
    }

    public static void clear() {
        currentUserId.remove();
    }

}
