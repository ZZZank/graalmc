package graal.mod.mixin;

import graal.mod.GraalMC;
import graal.mod.api.FallbackTypeMappingProvider;
import graal.mod.api.WithFallbackTypeMapping;
import graal.mod.impl.FallbackTypeMappingHolder;
import graal.mod.impl.RegistryImpl;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author ZZZank
 */
@Mixin(targets = "com.oracle.truffle.host.HostClassCache", remap = false)
public abstract class MixinHostClassCache {

    @Shadow
    @Final
    private ClassValue<FallbackTypeMappingHolder> descs;
    @Unique
    private static MethodHandle CTOR_HostTargetMapping;
    @Unique
    private static Object[] EMPTY_ARRAY_HostTargetMapping;

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void initConstants(CallbackInfo ci) {
        try {
            var lookup = MethodHandles.lookup();

            var c = lookup.findClass("com.oracle.truffle.host.HostTargetMapping");
            CTOR_HostTargetMapping = lookup.findConstructor(
                c,
                MethodType.methodType(
                    void.class,
                    Class.class,
                    Class.class,
                    Predicate.class,
                    Function.class,
                    HostAccess.TargetMappingPrecedence.class
                )
            );
            EMPTY_ARRAY_HostTargetMapping = (Object[]) Array.newInstance(c, 0);
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException e) {
            GraalMC.LOGGER.error("Error loading Graal constant", e);
            throw new IllegalStateException(e);
        }
    }

    @Unique
    private final List<FallbackTypeMappingProvider> graal$fallbackProviders = new ArrayList<>();

    @Inject(method = "<init>", at = @At("RETURN"))
    private void graal$setupProviders(
        AbstractPolyglotImpl.AbstractHostAccess polyglotAccess,
        AbstractPolyglotImpl.APIAccess apiAccess,
        Object hostAccess,
        CallbackInfo ci
    ) {
        if (hostAccess instanceof WithFallbackTypeMapping withFallbackTypeMapping) {
            this.graal$fallbackProviders.clear();
            this.graal$fallbackProviders.addAll(withFallbackTypeMapping.graal$viewProviders());
        }
    }

    @Inject(method = "getMappings", at = @At("RETURN"), cancellable = true)
    private <T> void graal$getFallbackMapping(Class<T> targetType, CallbackInfoReturnable<Object> cir) {
        var original = cir.getReturnValue();
        if (Array.getLength(original) != 0) {
            return;
        }

        var replacedBy = this.descs.get(targetType).graal$getFallbackMappings(() -> {
            var registry = new RegistryImpl<T>(CTOR_HostTargetMapping, EMPTY_ARRAY_HostTargetMapping);

            for (var provider : graal$fallbackProviders) {
                provider.provideMapping(targetType, registry);
            }

            return registry.build();
        });

        cir.setReturnValue(replacedBy);
    }
}
