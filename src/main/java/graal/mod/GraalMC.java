package graal.mod;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.SandboxPolicy;
import graal.mod.impl.LoggerOutputStream;

public final class GraalMC {
    public static final String MOD_ID = "graalmc";
    public static final Logger LOGGER = LogManager.getLogger("graal");
    private static final String INIT_SCRIPT_KEY = "graal.dev.on_init_script";

    public static void init() {
        var onInitScript = System.getenv(INIT_SCRIPT_KEY);
        if (onInitScript != null) {
            LOGGER.info("Found '{}' system property, running in constrained environment", INIT_SCRIPT_KEY);

            // see https://www.graalvm.org/sdk/javadoc/org/graalvm/polyglot/SandboxPolicy.html#CONSTRAINED
            // TLDR: SandboxPolicy.CONSTRAINED can block basically all access outside JS context
            var context = Context.newBuilder("js")
                .sandbox(SandboxPolicy.CONSTRAINED)
                .out(new LoggerOutputStream(LOGGER, Level.INFO))
                .err(new LoggerOutputStream(LOGGER, Level.ERROR))
                .build();

            try {
                var result = context.eval("js", onInitScript).toString();
                context.close();
                LOGGER.info("'{}' script evaluated, result: {}", INIT_SCRIPT_KEY, result);
            } catch (Exception e) {
                LOGGER.error("Error when trying to load '{}' script", INIT_SCRIPT_KEY, e);
            }
        }
    }
}
