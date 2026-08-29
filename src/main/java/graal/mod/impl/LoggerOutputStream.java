package graal.mod.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class LoggerOutputStream extends ByteArrayOutputStream {
    private final Logger logger;
    private final Level level;

    public LoggerOutputStream(Logger logger, Level level) {
        this.logger = logger;
        this.level = level;
    }

    @Override
    public synchronized void flush() {
        String content = toString(StandardCharsets.UTF_8);
        if (!content.isEmpty()) {
            // Split by newlines and log each line separately
            String[] lines = content.split("\\r?\\n", -1);
            for (String line : lines) {
                if (!line.isEmpty()) {
                    logger.log(level, line);
                }
            }
            reset();
        }
    }
}
