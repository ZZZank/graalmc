package graal.mod.mixin;

import graal.mod.api.TypeMappingProvider;
import graal.mod.api.TypeMappingProviderRegistry;
import org.graalvm.polyglot.HostAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author ZZZank
 */
@Mixin(value = HostAccess.class, remap = false)
public abstract class MixinHostAccess implements TypeMappingProviderRegistry {

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
}
