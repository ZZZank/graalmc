package graal.mod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import graal.mod.api.MemberRemapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author ZZZank
 */
@Mixin(targets = "com.oracle.truffle.host.HostClassDesc$Members", remap = false)
public abstract class MixinHostClassDesc_Members {

    @Redirect(method = "collectPublicFields", at = @At(value = "INVOKE", target = "Ljava/lang/reflect/Field;getName()Ljava/lang/String;"))
    private static String redirectFieldName(Field f) {
        return MemberRemapper.GLOBAL.get().remapField(f);
    }

    @Redirect(method = "collectPublicFields", at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private static <K, V> V hideFieldRemappedToNull(Map<K, V> instance, K key, V value) {
        if (key != null) {
            return instance.put(key, value);
        }
        return null;
    }

    @Redirect(method = "putMethod", at = @At(value = "INVOKE", target = "Ljava/lang/reflect/Method;getName()Ljava/lang/String;"))
    private static String redirectMethodName(Method f) {
        return MemberRemapper.GLOBAL.get().remapMethod(f);
    }

    @Redirect(method = "putMethod", at = @At(value = "INVOKE", target = "Ljava/util/Map;merge(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/function/BiFunction;)Ljava/lang/Object;"))
    private static <K, V> V hideMethodRemappedToNull(
        Map<K, V> instance,
        K key,
        V value,
        BiFunction<? super V, ? super V, ? extends V> remappingFunction
    ) {
        if (key != null) {
            return instance.merge(key, value, remappingFunction);
        }
        return null;
    }
}
