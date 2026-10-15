package graal.mod.mixin;

import graal.mod.impl.FallbackTypeMappingHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author ZZZank
 */
@Mixin(targets = "com.oracle.truffle.host.HostClassDesc")
public class MixinHostClassDesc implements FallbackTypeMappingHolder {
    @Unique
    private Object[] graal$mappings;

    @Override
    public Object[] graal$getFallbackMappings(Supplier<Object[]> initializer) {
        var mappings = graal$mappings;
        if (mappings == null) {
            graal$mappings = mappings = Objects.requireNonNull(initializer.get());
            Arrays.sort(mappings);
        }
        return mappings;
    }
}
