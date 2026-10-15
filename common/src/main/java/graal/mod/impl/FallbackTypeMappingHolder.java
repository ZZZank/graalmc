package graal.mod.impl;

import java.util.function.Supplier;

/**
 * @author ZZZank
 */
public interface FallbackTypeMappingHolder {

    Object[] graal$getFallbackMappings(Supplier<Object[]> initializer);
}
