package dn.ftc.hardware;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.PullResistance;

/**
 * AD Converter
 */
public class HX711 {
    public static final int GAIN_A_128 = 1; // Channel A gain factor 128
    public static final int GAIN_B_32 = 2;  // Channel B gain factor 32
    public static final int GAIN_A_64 = 3;  // Channel A gain factor 64

    private final DigitalInput dout;
    private final DigitalOutput sck;
    private final int gain;

    public HX711(Context pi4j, int doutPin, int sckPin, int gain) {
        this.gain = gain;

        // Initialize DOUT pin
        this.dout = pi4j.din().create(DigitalInput.newConfigBuilder(pi4j)
                .id("DOUT")
                .name("HX711 DOUT")
                .address(doutPin)
                .pull(PullResistance.PULL_UP)
                .provider("pigpio-digital-input")
                .build());

        // Initialize SCK pin
        this.sck = pi4j.dout().create(DigitalOutput.newConfigBuilder(pi4j)
                .id("SCK")
                .name("HX711 SCK")
                .address(sckPin)
                .initial(DigitalState.LOW)
                .provider("pigpio-digital-output")
                .build());

        setGain();
    }

    private void setGain() {
        // Set the gain factor by reading from the HX711 once
        read();
    }

    public boolean isReady() {
        return !dout.isHigh();
    }

    public int read() {
        while (!isReady()) {}

        int count = 0;
        for (int i = 0; i < 24; i++) {
            sck.high();
            count = count << 1;
            sck.low();
            if (dout.isHigh()) {
                count++;
            }
        }

        // Set the gain for the next read
        for (int i = 0; i < gain; i++) {
            sck.high();
            sck.low();
        }

        // Convert the 24-bit signed value
        count = count ^ 0x800000;
        return count;
    }
}
