package dn.ftc.util.loging;

public enum Level {

    ALERT(java.util.logging.Level.SEVERE),
    ERROR(java.util.logging.Level.SEVERE),

    WARNING(java.util.logging.Level.WARNING),
    INFO(java.util.logging.Level.INFO),
    CONFIG(java.util.logging.Level.CONFIG),
    DEBUG(java.util.logging.Level.FINE),
    DETAIL(java.util.logging.Level.FINER),
    DATA(java.util.logging.Level.FINEST),

    OFF(java.util.logging.Level.OFF),
    ALL(java.util.logging.Level.ALL);

    private final java.util.logging.Level javaLevel;

    Level(java.util.logging.Level javaLevel) {
        this.javaLevel = javaLevel;
    }

    java.util.logging.Level toJavaLevel() {
        return this.javaLevel;
    }

    static String nameOfJavaLevel(java.util.logging.Level level) {
        for(Level l : Level.values()) if(l.javaLevel.equals(level)) return l.name();
        return "UNKNOWN";
    }

}
