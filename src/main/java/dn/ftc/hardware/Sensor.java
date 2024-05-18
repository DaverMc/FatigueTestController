package dn.ftc.hardware;

import dn.ftc.util.loging.Logger;

public abstract class Sensor {

    private final float calFactor;

    private float offset;

    public Sensor(float calFactor) {
        this.calFactor = calFactor;
    }

    protected abstract int readADC();

    public float read() {
        return (readADC() - offset) * calFactor;
    }

    public float readAverage(int iterations) {
        float sum = 0;
        for (int i = 0; i < iterations; i++) sum += read();
        return sum / iterations;
    }

    public float calibrate(int realValue, int cycles) {
        long calADC = 0;
        float calFac = 0;
        int failed = 0;

        for(int i = 0; i < cycles; i++) {
            int adc = readADC();
            if(adc == 0 && failed > 5) {
                Logger.error("Calibration failed! Too many zero cycles!");
                return 0;
            } else if(adc == 0) {
                i--;
                failed++;
                continue;
            }
            calADC += adc;
            calFac = (float) realValue / calADC;
            Logger.data("Real: " + realValue + ", ADC: " + adc + ", Factor: " + calFac);
        }
        if(cycles < 0 || calADC == 0) return 0;
        else if(cycles == 1) return calFac;
        calFac = (float) realValue / calADC;
        Logger.data("Average over " + cycles + " iterations: Real: " + realValue + ", ADC: " + calADC + ", Factor: " + calFac);
        return calFac;
    }

    public void readOffset(int iterations) {
        this.offset = 0;
        this.offset = readAverage(iterations);
    }

}
