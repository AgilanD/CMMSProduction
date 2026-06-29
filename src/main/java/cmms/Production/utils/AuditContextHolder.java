//package cmms.Production.utils;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class AuditContextHolder {
//
//    private static final ThreadLocal<Long> currentUser = new ThreadLocal<>();
//
//    public static void setCurrentUserId(Long userId) {
//        currentUser.set(userId);
//    }
//
//    public static Long getCurrentUserId() {
//        Long userId = currentUser.get();
//        log.info("<===============================> From Productions ");
//        log.info(String.valueOf(userId));
//        return (userId != null) ? userId : 1L;
//    }
//
//    public static void clear() {
//        currentUser.remove();
//    }
//}
package cmms.Production.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
public class AuditContextHolder {

    private static final ThreadLocal<Long> currentUser = new ThreadLocal<>();

    public static void setCurrentUserId(Long userId) {
        currentUser.set(userId);
    }

    public static Long getCurrentUserId() {
        Long userId = currentUser.get();
        if (userId == null) {
            log.info(" Thread context empty. Defaulting to fallback ID: 1");
            return 1L;
        }
        return userId;
    }

    public static void clear() {
        currentUser.remove();
    }

}
