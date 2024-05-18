package dn.ftc.hardware;

public class LengthSensor extends Sensor {

    private final WaveShareADDA analogIO;
    private final int channel;

    public LengthSensor(WaveShareADDA analogIO, float calibrationFactor, int channel) {
        super(calibrationFactor);
        this.analogIO = analogIO;
        this.channel = channel;
    }

    @Override
    protected int readADC() {
        return analogIO.readADCChannel(channel);
    }
}
