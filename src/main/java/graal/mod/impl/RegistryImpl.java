package graal.mod.impl;

import graal.mod.api.TypeMappingProvider;
import org.graalvm.polyglot.HostAccess;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author ZZZank
 */
public final class RegistryImpl<T> implements TypeMappingProvider.ProviderRegistry<T> {
    private final List<Object> registered = new ArrayList<>();
    private final MethodHandle ctor;
    private final Object[] referenceArray;

    public RegistryImpl(MethodHandle ctor, Object[] referenceArray) {
        this.ctor = ctor;
        this.referenceArray = referenceArray;
    }

    @Override
    public <S> void register(
        Class<S> sourceType,
        Class<T> targetType,
        Predicate<S> accepts,
        Function<S, T> converter,
        HostAccess.TargetMappingPrecedence precedence
    ) {
        try {
            var mapping = ctor.invoke(sourceType, targetType, accepts, converter, precedence);
            registered.add(mapping);
        } catch (Throwable ignored) {
        }
    }

    public Object[] build() {
        return registered.isEmpty() ? referenceArray : registered.toArray(referenceArray);
    }
}
