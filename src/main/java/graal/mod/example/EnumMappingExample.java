package graal.mod.example;

import graal.mod.api.TypeMappingProviderRegistry;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;

/**
 * @author ZZZank
 */
enum EnumMappingExample {
    VAL,
    VAR,
    WOW
    ;

    public static int ord(EnumMappingExample example) {
        return example.ordinal();
    }

    public static void run() {
        var builder = HostAccess.newBuilder()
            .allowPublicAccess(true);

        TypeMappingProviderRegistry.cast(builder).graal$addProvider(new EnumTypeMappingProvider());

        var cx = Context.newBuilder("js")
            .allowHostAccess(builder.build())
            .allowHostClassLookup(EnumMappingExample.class.getName()::equals)
            .logHandler(System.err)
            .build();

        try {
            cx.eval("js", """
                const Example = Java.type("%s")

                console.log(Example.ord("VAL"))
                console.log(Example.ord("VAR"))
                console.log(Example.ord("WOW"))
                """.formatted(EnumMappingExample.class.getName()));
        } finally {
            cx.close();
        }
        throw new AssertionError("GOOD");
    }
}
