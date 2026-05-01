package graal.mod.mixin;

import graal.mod.api.TypeMappingProvider;
import graal.mod.api.TypeMappingProviderRegistry;
import org.graalvm.polyglot.HostAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author ZZZank
 */
@Mixin(value = HostAccess.Builder.class, remap = false)
public abstract class MixinHostAccess_Builder implements TypeMappingProviderRegistry {

    @Unique
    private final List<TypeMappingProvider> graal$providers = new ArrayList<>();

    @Override
    public void graal$addProvider(TypeMappingProvider provider) {
        graal$providers.add(Objects.requireNonNull(provider));
    }

    @Override
    public List<TypeMappingProvider> graal$viewProviders() {
        return Collections.unmodifiableList(graal$providers);
    }

    @Inject(method = "build", at = @At("RETURN"))
    private void pushProvidersToHostAccess(CallbackInfoReturnable<Object> cir) {
        if (cir.getReturnValue() instanceof TypeMappingProviderRegistry registry) {
            for (var provider : graal$providers) {
                registry.graal$addProvider(provider);
            }
        }
    }
}
