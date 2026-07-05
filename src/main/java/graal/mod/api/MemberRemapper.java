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

    default String remapMethod(Method method) {
        return FALL_THROUGH;
    }

    default String remapField(Field field) {
        return FALL_THROUGH;
    }
}
