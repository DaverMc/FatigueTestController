package dn.ftc.hardware;

import com.pi4j.context.Context;
import dn.ftc.util.Configuration;


public class WaveShareADDA {
    private final ADS1256 adc;
    private final DAC8552 dac;

    public WaveShareADDA(Context pi4j, Config config) {
        this.adc = new ADS1256(pi4j, config.spiChannel, config.csPinAdc, config.resetPinAdc, config.drdyPinAdc, config.addressAD);
        this.dac = new DAC8552(pi4j, config.spiChannel, config.csPinDac, config.addressDA);
    }

    public int readADCChannel(int channel) {
        return adc.readChannel(channel);
    }

    public void writeDACChannel(int channel, int value) {
        dac.writeChannel(channel, value);
    }

    public record Config(int spiChannel, int csPinAdc, int resetPinAdc, int drdyPinAdc, int csPinDac, int addressAD, int addressDA) {

        Config(Configuration configuration) {
            this(
                    configuration.getInt("spiChannel"),
                    configuration.getInt("csPinAdc"),
                    configuration.getInt("resetPinAdc"),
                    configuration.getInt("drdyPinAdc"),
                    configuration.getInt("csPinDac"),
                    configuration.getInt("addressAD"),
                    configuration.getInt("addressDA")
            );
        }

    }
}

