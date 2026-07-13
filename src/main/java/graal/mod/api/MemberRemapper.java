package graal.mod.api;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author ZZZank
 */
public interface MemberRemapper {
    MemberRemapperChain CHAIN = new MemberRemapperChain();

    String FALL_THROUGH = "";
    String HIDE_MEMBER = null;

    /// @param clazz The class where the provided method is found, NOT always representing the same class as [Method#getDeclaringClass()]
    default String remapMethod(Method method, Class<?> clazz) {
        return FALL_THROUGH;
    }

    /// @param clazz The class where the provided field is found, NOT always representing the same class as [Field#getDeclaringClass()]
    default String remapField(Field field, Class<?> clazz) {
        return FALL_THROUGH;
    }
}
