package graal.mod.api;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author ZZZank
 */
public final class MemberRemapperChain implements MemberRemapper {
    private MemberRemapper[] remappers = new MemberRemapper[0];

    public void addRemapper(MemberRemapper remapper) {
        Objects.requireNonNull(remapper);

        var oldRemappers = remappers;
        remappers = Arrays.copyOf(oldRemappers, oldRemappers.length + 1);
        remappers[oldRemappers.length] = remapper;
    }

    @Override
    public String remapMethod(Method method) {
        for (var remapper : remappers) {
            var remapped = remapper.remapMethod(method);
            if (remapped == null) {
                return null;
            }
            if (remapped.isEmpty()) {
                continue;
            }
            return remapped;
        }
        return method.getName();
    }

    @Override
    public String remapField(Field field) {
        for (var remapper : remappers) {
            var remapped = remapper.remapField(field);
            if (remapped == null) {
                return null;
            }
            if (remapped.isEmpty()) {
                continue;
            }
            return remapped;
        }
        return field.getName();
    }
}
