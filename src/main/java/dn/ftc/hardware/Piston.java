package dn.ftc.hardware;

import com.pi4j.context.Context;
import dn.ftc.util.Configuration;

public class Piston implements HardwareIO{

    public static final short UP = 1;
    public static final short DOWN = -1;
    public static final short STOP = 0;

    private final LengthSensor lengthSensor;
    private final Motor motor;
    private final float maxLength;

    public Piston(Context pi4j, WaveShareADDA adda, Config config) {
        this.lengthSensor = new LengthSensor(adda, config.calFactor, config.adChannel);
        this.motor = new Motor(pi4j, config.pwmLeft, config.pwmRight, config.enableLeft, config.enableRight);
        this.maxLength = config.maxLength;
    }

    public Motor motor() {
        return this.motor;
    }

    public LengthSensor lengthSensor() {
        return this.lengthSensor;
    }

    public void extend() {
        this.motor.drive(UP);
    }

    public void extend(float length) {
        extend();
        while(lengthSensor.read() <= length);
        stop();
    }

    public void retract() {
        this.motor.drive(DOWN);
    }

    public void retract(float length) {
        retract();
        while(lengthSensor.read() >= length);
        stop();
    }

    public void stop() {
        this.motor.stop();
    }

    public void center(float length) {
        float l = lengthSensor.read();
        if(l < length) extend(length);
        else if(l > length) retract(length);
    }

    public boolean isExtended() {
        return lengthSensor.read() >= maxLength;
    }

    public boolean isRetracted() {
        return lengthSensor.read() <= 0f;
    }

    public float getExtendedLength() {
        return lengthSensor.read();
    }

    public float getMaxLength() {
        return maxLength;
    }

    public record Config(float calFactor, int adChannel, int pwmLeft, int pwmRight, int enableLeft, int enableRight, float maxLength) {

        public Config(Configuration configuration) {
            this(
                    configuration.getFloat("calFactor"),
                    configuration.getInt("adChannel"),
                    configuration.getInt("pwmLeft"),
                    configuration.getInt("pwmRight"),
                    configuration.getInt("enableLeft"),
                    configuration.getInt("enableRight"),
                    configuration.getFloat("maxLength")
            );
        }

    }

}
