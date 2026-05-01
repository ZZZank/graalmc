package graal.mod.api;

import org.graalvm.polyglot.HostAccess;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author ZZZank
 */
public interface TypeMappingProvider {

    <T> void provideMapping(Class<T> objectType, ProviderRegistry<T> registry);

    interface ProviderRegistry<T> {
        default <S> void register(Class<S> sourceType, Class<T> targetType, Predicate<S> accepts, Function<S, T> converter) {
            register(sourceType, targetType, accepts, converter, HostAccess.TargetMappingPrecedence.LOW);
        }

        <S> void register(
            Class<S> sourceType,
            Class<T> targetType,
            Predicate<S> accepts,
            Function<S, T> converter,
            HostAccess.TargetMappingPrecedence precedence
        );
    }
}
