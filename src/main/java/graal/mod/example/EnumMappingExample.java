package graal.mod.example;

import graal.mod.api.TypeMappingProvider;
import graal.mod.api.TypeMappingProviderRegistry;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author ZZZank
 */
public enum EnumMappingExample {
    VAL,
    VAR,
    WOW
    ;

    public static int ord(EnumMappingExample example) {
        return example.ordinal();
    }

    private static void run() {
        var builder = HostAccess.newBuilder()
            .allowPublicAccess(true);

        TypeMappingProviderRegistry.cast(builder).graal$addProvider(new TypeMappingProvider() {
            @Override
            public <T> void provideMapping(Class<T> objectType, ProviderRegistry<T> registry) {
                if (objectType.isEnum()) {
                    var collected = Arrays.stream(objectType.getEnumConstants()).collect(Collectors.toMap(
                        e -> ((Enum<?>) e).name(),
                        Function.identity()
                    ));
                    var constants = Map.copyOf(collected);
                    registry.register(String.class, objectType, constants::containsKey, constants::get);
                }
            }
        });

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
