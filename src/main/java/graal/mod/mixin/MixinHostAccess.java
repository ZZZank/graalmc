package graal.mod.mixin;

import graal.mod.api.FallbackTypeMappingProvider;
import graal.mod.api.WithFallbackTypeMapping;
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
public abstract class MixinHostAccess implements WithFallbackTypeMapping {

    @Unique
    private final List<FallbackTypeMappingProvider> graal$providers = new ArrayList<>();

    @Override
    public void graal$addProvider(FallbackTypeMappingProvider provider) {
        graal$providers.add(Objects.requireNonNull(provider));
    }

    @Override
    public List<FallbackTypeMappingProvider> graal$viewProviders() {
        return Collections.unmodifiableList(graal$providers);
    }
}
