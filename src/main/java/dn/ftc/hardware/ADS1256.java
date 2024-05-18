package dn.ftc.hardware;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.spi.Spi;
import com.pi4j.io.spi.SpiConfig;
import com.pi4j.io.spi.SpiMode;
import dn.ftc.util.loging.Logger;

import java.nio.ByteBuffer;

/**
 * AD Converter on Waveshare High Precision AD/DA Board
 */
public class ADS1256 {
    private static final int CMD_WAKEUP = 0x00;
    private static final int CMD_RDATA = 0x01;
    private static final int CMD_RDATAC = 0x03;
    private static final int CMD_SDATAC = 0x0F;
    private static final int CMD_RREG = 0x10;
    private static final int CMD_WREG = 0x50;
    private static final int CMD_SELFCAL = 0xF0;
    private static final int CMD_SELFOCAL = 0xF1;
    private static final int CMD_SELFGCAL = 0xF2;
    private static final int CMD_SYNC = 0xFC;
    private static final int CMD_STANDBY = 0xFD;
    private static final int CMD_RESET = 0xFE;

    private Spi spi;
    private DigitalOutput cs;
    private DigitalOutput reset;
    private DigitalInput drdy;

    public ADS1256(Context pi4j, int spiChannel, int csPin, int resetPin, int drdyPin, int address) {
        // Initialize SPI
        SpiConfig config = Spi.newConfigBuilder(pi4j)
                .id("spi_ADS1256")
                .name("SPI ADS1256")
                .bus(spiChannel)
                .mode(SpiMode.MODE_1)
                .address(address)
                .build();
        this.spi = pi4j.spi().create(config);

        // Initialize CS pin
        this.cs = pi4j.dout().create(csPin);

        // Initialize RESET pin
        this.reset = pi4j.dout().create(resetPin);

        // Initialize DRDY pin
        this.drdy = pi4j.din().create(drdyPin);

        // Reset the ADC
        resetADC();
    }

    private void resetADC() {
        reset.low();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Logger.error(e);
        }
        reset.high();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Logger.error(e);
        }
    }

    public int readChannel(int channel) {
        // Validate the channel
        if (channel < 0 || channel > 7) {
            throw new IllegalArgumentException("Invalid channel: " + channel);
        }

        // Set the multiplexer to the desired channel
        int mux = (channel << 4) | 0x08;  // 0x08 to select AINCOM as the negative input
        writeRegister(0x01, mux);

        // Send SYNC and WAKEUP commands to initiate the conversion
        sendCommand(CMD_SYNC);
        sendCommand(CMD_WAKEUP);

        // Wait for DRDY to go low
        while (drdy.isHigh()) {
            // Busy-wait until DRDY is low
        }

        // Read the conversion result
        cs.low();
        sendCommand(CMD_RDATA);
        byte[] data = new byte[3];
        spi.read(data);
        cs.high();

        // Convert the 24-bit signed result to an integer
        int result = ((data[0] & 0xFF) << 16) | ((data[1] & 0xFF) << 8) | (data[2] & 0xFF);
        if ((result & 0x800000) != 0) {
            result |= 0xFF000000;  // Sign extend the result
        }

        return result;
    }

    private void writeRegister(int reg, int value) {
        cs.low();
        sendCommand(CMD_WREG | reg);
        sendCommand(0x00);  // Only one register to write
        sendCommand(value);
        cs.high();
    }

    private void sendCommand(int command) {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        buffer.put((byte) command);
        buffer.flip();
        spi.write(buffer);
    }
}

