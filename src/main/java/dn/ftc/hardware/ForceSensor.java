package dn.ftc.hardware;

import com.pi4j.context.Context;
import dn.ftc.util.Configuration;

public class ForceSensor extends Sensor implements HardwareIO{

    private final HX711 adConverter;

    public ForceSensor(Context pi4j, Config config) {
        super(config.calibrationFactor);
        this.adConverter = new HX711(pi4j, config.dataPin, config.clockPin, config.gain);
    }

    @Override
    protected int readADC() {
        return adConverter.read();
    }

    public record Config(int dataPin, int clockPin, float calibrationFactor, int gain) {

        public Config(Configuration configuration) {
            this(
                    configuration.getInt("dataPin"),
                    configuration.getInt("clockPin"),
                    configuration.getFloat("calibrationFactor"),
                    configuration.getInt("gain")
            );
        }
    }
}
