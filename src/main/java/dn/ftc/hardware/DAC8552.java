package dn.ftc.hardware;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.spi.Spi;
import com.pi4j.io.spi.SpiConfig;
import com.pi4j.io.spi.SpiMode;

/**
 * DA Converter on Waveshare High Precision AD/DA Board
 */
public class DAC8552 {
    private Context pi4j;
    private Spi spi;
    private DigitalOutput cs;

    public DAC8552(Context pi4j, int spiChannel, int csPin, int address) {
        this.pi4j = pi4j;

        // Initialize SPI
        SpiConfig config = Spi.newConfigBuilder(pi4j)
                .id("spi_DAC8552")
                .name("SPI DAC8552")
                .bus(spiChannel)
                .mode(SpiMode.MODE_1)
                .address(address)
                .build();
        this.spi = pi4j.spi().create(config);

        // Initialize CS pin
        this.cs = pi4j.dout().create(csPin);
    }

    public void writeChannel(int channel, int value) {
        if (channel < 0 || channel > 1) {
            throw new IllegalArgumentException("Invalid channel: " + channel);
        }
        if (value < 0 || value > 65535) {
            throw new IllegalArgumentException("Invalid value: " + value);
        }

        cs.low();
        try {
            byte[] data = new byte[3];
            data[0] = (byte) (channel == 0 ? 0x10 : 0x12);  // Command byte
            data[1] = (byte) (value >> 8);  // High byte
            data[2] = (byte) value;         // Low byte

            spi.write(data);
        } finally {
            cs.high();
        }
    }
}
