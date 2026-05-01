package graal.mod.api;

import org.graalvm.polyglot.HostAccess;

import java.util.List;

/**
 * @author ZZZank
 */
public interface WithFallbackTypeMapping {
    static WithFallbackTypeMapping cast(HostAccess.Builder object) {
        return (WithFallbackTypeMapping) (Object) object;
    }

    void graal$addProvider(FallbackTypeMappingProvider factory);

    List<FallbackTypeMappingProvider> graal$viewProviders();
}
