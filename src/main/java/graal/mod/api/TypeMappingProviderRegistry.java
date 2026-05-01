package graal.mod.api;

import org.graalvm.polyglot.HostAccess;

import java.util.List;

/**
 * @author ZZZank
 */
public interface TypeMappingProviderRegistry {
    static TypeMappingProviderRegistry cast(HostAccess.Builder object) {
        return (TypeMappingProviderRegistry) (Object) object;
    }

    void graal$addProvider(TypeMappingProvider provider);

    List<TypeMappingProvider> graal$viewProviders();
}
