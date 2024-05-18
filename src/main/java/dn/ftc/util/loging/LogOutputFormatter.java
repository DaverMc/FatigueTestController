package dn.ftc.util.loging;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.LogRecord;

public class LogOutputFormatter extends java.util.logging.Formatter {

    private static final int STACKTRACE_DEPTH;
    private static final DateTimeFormatter dtf;

    static {
        STACKTRACE_DEPTH = 10;
        dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        Logger.config("Log Stacktrace Depth: " + STACKTRACE_DEPTH);
    }

    @Override
    public String format(LogRecord record) {
        Throwable throwable = record.getThrown();
        if(throwable != null) {
            return formatException(record);
        }
        return prefix(record) + record.getMessage() + "\n";
    }

    private String prefix(LogRecord record) {
        LocalDateTime dateTime = LocalDateTime.now();
        return String.format("[%s][%s]: ",
                dtf.format(dateTime),
                Level.nameOfJavaLevel(record.getLevel()));
    }

    private String formatException(LogRecord record) {
        Throwable throwable = record.getThrown();
        StringBuilder line = new StringBuilder(prefix(record) + throwable.toString() + "\n");
        int count = 0;
        for(var element : throwable.getStackTrace()) {
            line.append("\t").append(element.toString()).append("\n");
            count++;
            if(count > STACKTRACE_DEPTH) {
                int left = throwable.getStackTrace().length - count;
                line.append("\t").append(left).append(" lines left...\n");
                break;
            }
        }
        return line.toString();
    }


}
