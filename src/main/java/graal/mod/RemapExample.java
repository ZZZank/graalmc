package graal.mod;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import graal.mod.api.MemberRemapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author ZZZank
 */
public class RemapExample implements MemberRemapper {

    public final int graal$field = 42;

    public String graal$method() {
        return """
            I dont know if anyone actually bother to download source jar of GraalMC
            So let me just provide an example here""";
    }

    static void example() {
        MemberRemapper.GLOBAL.set(new RemapExample());
        var access = HostAccess.newBuilder().allowPublicAccess(true).build();
        try (var context = Context.newBuilder("js")
            .allowHostClassLookup("zank.mods.graalmc.RemapExample"::equals)
            .allowHostAccess(access)
            .build()
        ) {
            context.eval(
                "js", """
                    let RemapExample = Java.type("zank.mods.graalmc.RemapExample")
                    let example = new RemapExample()
                    console.log(example.method())
                    console.log(example.field)
                    """
            );
        }
    }

    @Override
    public String remapField(Field field) {
        var name = field.getName();
        return name.startsWith("graal$") ? name.substring("graal$".length()) : name;
    }

    @Override
    public String remapMethod(Method method) {
        var name = method.getName();
        return name.startsWith("graal$") ? name.substring("graal$".length()) : name;
    }
}
