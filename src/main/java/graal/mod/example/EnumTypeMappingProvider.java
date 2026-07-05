package graal.mod.example;

import graal.mod.api.TypeMappingProvider;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author ZZZank
 */
public class EnumTypeMappingProvider implements TypeMappingProvider {
    protected String toName(Enum<?> e) {
        return e.name();
    }

    @Override
    public <T> void provideMapping(Class<T> objectType, MappingRegistry<T> registry) {
        if (objectType.isEnum()) {
            var collected = Arrays.stream(objectType.getEnumConstants())
                .collect(Collectors.toMap(e -> toName((Enum<?>) e), Function.identity()));
            var constants = Map.copyOf(collected);
            registry.register(String.class, objectType, constants::containsKey, constants::get);
        }
    }
}
