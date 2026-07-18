package graal.mod.mixin;

import org.graalvm.home.Version;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * @author ZZZank
 */
@Mixin(targets = "com.oracle.truffle.runtime.hotspot.HotSpotTruffleRuntimeAccess", remap = false)
public abstract class MixinHotSpotTruffleRuntimeAccess {

    @Redirect(method = "getCompilerVersion", at = @At(value = "INVOKE", target = "Lorg/graalvm/home/Version;parse(Ljava/lang/String;)Lorg/graalvm/home/Version;"))
    private static Version inj(String versionString) {
        if (versionString == null) {
            throw new IllegalArgumentException("Got null compiler version. If you're using GraalVM JDK, update to 25.1 or newer!");
        }
        return Version.parse(versionString);
    }
}
