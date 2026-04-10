package graal.mod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import graal.mod.api.MemberRemapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author ZZZank
 */
@Mixin(targets = "com.oracle.truffle.host.HostClassDesc$Members", remap = false)
public abstract class MixinHostClassDesc_Members {

    @Redirect(method = "collectPublicFields", at = @At(value = "INVOKE", target = "Ljava/lang/reflect/Field;getName()Ljava/lang/String;"))
    private static String redirectFieldName(Field f) {
        return MemberRemapper.GLOBAL.get().remapField(f);
    }

    @Redirect(method = "putMethod", at = @At(value = "INVOKE", target = "Ljava/lang/reflect/Method;getName()Ljava/lang/String;"))
    private static String redirectMethodName(Method f) {
        return MemberRemapper.GLOBAL.get().remapMethod(f);
    }
}
