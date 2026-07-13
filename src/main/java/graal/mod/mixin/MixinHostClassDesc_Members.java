package graal.mod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import graal.mod.api.MemberRemapper;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author ZZZank
 */
@Mixin(targets = "com.oracle.truffle.host.HostClassDesc$Members", remap = false)
public abstract class MixinHostClassDesc_Members {
    @Unique
    private static final ThreadLocal<Class<?>> CURRENT_TYPE = new ThreadLocal<>();

    @Inject(method = "<init>", at = @At(value = "HEAD", unsafe = true))
    private static void graal$setCurrentType(@Coerce Object hostAccess, Class<?> type, CallbackInfo ci) {
        CURRENT_TYPE.set(type);
    }

    @Redirect(method = "collectPublicFields", at = @At(value = "INVOKE", target = "Ljava/lang/reflect/Field;getName()Ljava/lang/String;"))
    private static String remapField(Field f) {
        return MemberRemapper.CHAIN.remapField(f, CURRENT_TYPE.get());
    }

    @Redirect(method = "collectPublicInstanceFields", at = @At(value = "INVOKE", target = "Ljava/lang/reflect/Field;getName()Ljava/lang/String;"))
    private static String remapField2(Field f) {
        return MemberRemapper.CHAIN.remapField(f, CURRENT_TYPE.get());
    }

    @Redirect(method = "collectPublicFields", at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private static <K, V> V hideFieldRemappedToNull(Map<K, V> instance, K key, V value) {
        if (key != null) {
            return instance.put(key, value);
        }
        return null;
    }

    @Redirect(method = "collectPublicInstanceFields", at = @At(value = "INVOKE", target = "Ljava/util/Map;putIfAbsent(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private static <K, V> V hideFieldRemappedToNull2(Map<K, V> instance, K key, V value) {
        if (key != null) {
            return instance.putIfAbsent(key, value);
        }
        return null;
    }

    @Redirect(method = "putMethod", at = @At(value = "INVOKE", target = "Ljava/lang/reflect/Method;getName()Ljava/lang/String;"))
    private static String redirectMethodName(Method f) {
        return MemberRemapper.CHAIN.remapMethod(f, CURRENT_TYPE.get());
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
