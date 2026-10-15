package graal.mod.mixin;

import graal.graalvm.home.Version;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/// When running Graal (mod) using GraalVM (JVM) with mismatched version, `TruffleCompilationSupport#getCompilerVersion()`
/// will give `null` value, resulting in confusing [NullPointerException] with no helpful message.
///
/// Known incompatibility: Graal 25.1.3 with GraalVM 25.0.2.
///
/// @author ZZZank
@Mixin(targets = "com.oracle.truffle.runtime.hotspot.HotSpotTruffleRuntimeAccess", remap = false)
public abstract class MixinHotSpotTruffleRuntimeAccess {

    /// Return a version smaller than [com.oracle.truffle.api.impl.TruffleVersions#MIN_COMPILER_VERSION] so that Graal
    /// will not try to enable compiler for incompatible GraalVM
    @Redirect(method = "getCompilerVersion", at = @At(value = "INVOKE", target = "Lgraal/graalvm/home/Version;parse(Ljava/lang/String;)Lgraal/graalvm/home/Version;"))
    private static Version inj(String versionString) {
        if (versionString == null) {
            // `MIN_COMPILER_VERSION` is `25.1.0`, 25.1.0 > 25.0.99
            return Version.create(25, 0, 99);
        }
        return Version.parse(versionString);
    }
}
