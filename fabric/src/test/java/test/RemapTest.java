package test;

import graal.graalvm.polyglot.Context;
import graal.graalvm.polyglot.HostAccess;
import graal.mod.api.MemberRemapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author ZZZank
 */
public class RemapTest {

    public final int graal$field = 42;

    public String graal$method() {
        return "hi";
    }

    @Test
    public void test() {
        MemberRemapper.CHAIN.addRemapper(new ExampleRemapper());

        var access = HostAccess.newBuilder().allowPublicAccess(true).build();
        var context = Context.newBuilder("js")
            .allowHostClassLookup(RemapTest.class.getName()::equals)
            .allowHostAccess(access)
            .build();

        try (context) {
            context.getBindings("js").putMember("example", this);
            var result = context.eval("js", "({f: example.field, m: example.method()})");
            Assertions.assertEquals(
                Map.of("f", graal$field, "m", graal$method()),
                result.as(Map.class)
            );
        }
    }

    public static class ExampleRemapper implements MemberRemapper {

        @Override
        public String remapField(Field field, Class<?> clazz) {
            var name = field.getName();
            return name.startsWith("graal$") ? name.substring("graal$".length()) : name;
        }

        @Override
        public String remapMethod(Method method, Class<?> clazz) {
            var name = method.getName();
            return name.startsWith("graal$") ? name.substring("graal$".length()) : name;
        }
    }
}
