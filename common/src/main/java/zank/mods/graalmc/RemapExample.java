package zank.mods.graalmc;

import zank.mods.graalmc.api.MemberRemapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author ZZZank
 */
public class RemapExample implements MemberRemapper {

    public final int graal$field = 42;

    public String graal$method() {
        return "wow";
    }

    @Override
    public String remapField(Field field) {
        var name = field.getName();
        return name.startsWith("graal$")? name.substring("graal$".length()) : name;
    }

    @Override
    public String remapMethod(Method method) {
        var name = method.getName();
        return name.startsWith("graal$")? name.substring("graal$".length()) : name;
    }
}
