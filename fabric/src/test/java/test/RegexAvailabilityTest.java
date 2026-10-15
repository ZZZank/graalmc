package test;

import graal.graalvm.polyglot.Context;
import impl.Patch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author ZZZank
 */
public class RegexAvailabilityTest {

    @Test
    public void test() {
        Patch.setContextClassLoader();

        try (var cx = Context.create("js")) {
            var evalResult = cx.eval("js", "/^minecraft/.test('ok')");
            Assertions.assertFalse(evalResult.asBoolean(), "/^minecraft/.test('ok') == true ?!");
        }
    }
}
