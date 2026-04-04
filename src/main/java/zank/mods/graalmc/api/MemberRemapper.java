package zank.mods.graalmc.api;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author ZZZank
 */
public interface MemberRemapper {
    MemberRemapper JAVA = new MemberRemapper() {};
    AtomicReference<MemberRemapper> GLOBAL = new AtomicReference<>(JAVA);

    default String remapMethod(Method method) {
        return method.getName();
    }

    default String remapField(Field field) {
        return field.getName();
    }
}
