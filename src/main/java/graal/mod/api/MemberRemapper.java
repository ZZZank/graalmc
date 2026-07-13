package graal.mod.api;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author ZZZank
 */
public interface MemberRemapper {
    @Deprecated(since = "25.1.3.4: RemapperChain & MappingProvider", forRemoval = true)
    AtomicReference<MemberRemapper> GLOBAL = new AtomicReference<>(new MemberRemapper() {});
    MemberRemapperChain CHAIN = new MemberRemapperChain();

    String FALL_THROUGH = "";
    String HIDE_MEMBER = null;

    @Deprecated
    default String remapMethod(Method method) {
        return FALL_THROUGH;
    }

    /// @param clazz The class where the provided method is found, NOT always representing the same class as [Method#getDeclaringClass()]
    default String remapMethod(Method method, Class<?> clazz) {
        return remapMethod(method);
    }

    @Deprecated
    default String remapField(Field field) {
        return FALL_THROUGH;
    }

    /// @param clazz The class where the provided field is found, NOT always representing the same class as [Field#getDeclaringClass()]
    default String remapField(Field field, Class<?> clazz) {
        return remapField(field);
    }
}
