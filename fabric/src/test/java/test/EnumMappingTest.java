package test;

import graal.graalvm.polyglot.Context;
import graal.graalvm.polyglot.HostAccess;
import graal.mod.api.TypeMappingProviderRegistry;
import graal.mod.example.EnumTypeMappingProvider;
import impl.Patch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnumMappingTest {

    @Test
    public void test() {
        Patch.setContextClassLoader();

        var builder = HostAccess.newBuilder()
            .allowPublicAccess(true);

        TypeMappingProviderRegistry.cast(builder).graal$addProvider(new EnumTypeMappingProvider());

        var cx = Context.newBuilder("js")
            .allowHostAccess(builder.build())
            .allowHostClassLookup(EnumExample.class.getName()::equals)
            .logHandler(System.err)
            .build();

        try (cx) {
            var result = cx.eval(
                "js", """
                    const Enum = Java.type("%s");
                    
                    [Enum.acceptsEnum("A"), Enum.acceptsEnum("C"), Enum.acceptsEnum("B")]
                    """.formatted(EnumExample.class.getName())
            );
            Assertions.assertArrayEquals(new int[]{0, 2, 1}, result.as(int[].class));
        }
    }

    public enum EnumExample {
        A,
        B,
        C;

        public static int acceptsEnum(EnumExample e) {
            return e.ordinal();
        }
    }
}
