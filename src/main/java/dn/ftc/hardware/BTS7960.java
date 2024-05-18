package dn.ftc.hardware;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmType;

/**
 * Motor Driver
 */
public class BTS7960 {
    private Pwm pwmL;
    private Pwm pwmR;
    private DigitalOutput enL;
    private DigitalOutput enR;

    public BTS7960(Context pi4j, int pwmLPin, int pwmRPin, int enLPin, int enRPin) {
        // Initialize PWM for left and right motor control
        this.pwmL = pi4j.pwm().create(Pwm.newConfigBuilder(pi4j)
                .id("PWM_L")
                .name("PWM Left")
                .address(pwmLPin)
                .frequency(1000)
                .pwmType(PwmType.HARDWARE)
                .build());

        this.pwmR = pi4j.pwm().create(Pwm.newConfigBuilder(pi4j)
                .id("PWM_R")
                .name("PWM Right")
                .address(pwmRPin)
                .frequency(1000)
                .pwmType(PwmType.HARDWARE)
                .build());

        // Initialize EN (Enable) pins for left and right motor control
        this.enL = pi4j.dout().create(enLPin);
        this.enR = pi4j.dout().create(enRPin);

        // Initially disable both sides
        this.enL.low();
        this.enR.low();
    }

    public void setSpeed(int speed) {
        if (speed < -100 || speed > 100) {
            throw new IllegalArgumentException("Speed must be between -100 and 100");
        }

        if (speed > 0) {
            // Forward direction
            enL.high();
            enR.low();
            pwmL.on(speed);
            pwmR.off();
        } else if (speed < 0) {
            // Backward direction
            enL.low();
            enR.high();
            pwmL.off();
            pwmR.on(-speed);
        } else {
            // Stop
            enL.low();
            enR.low();
            pwmL.off();
            pwmR.off();
        }
    }
}

