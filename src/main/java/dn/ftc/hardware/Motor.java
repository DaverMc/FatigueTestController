package dn.ftc.hardware;

import com.pi4j.context.Context;

public class Motor {

    private final BTS7960 driver;
    private int speed;
    private short direction;

    public Motor(Context pi4j, int pwmLeft, int pwmRight, int enableLeft, int enableRight) {
        this.driver = new BTS7960(pi4j, pwmLeft, pwmRight ,enableLeft, enableRight);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDirection(short direction) {
        this.direction = direction;
    }

    public void drive(int speed, short direction) {
        driver.setSpeed(speed * direction);
        this.speed = speed;
        this.direction = direction;
    }

    public void drive(int speed) {
        drive(speed, this.direction);
    }

    public void drive(short direction) {
        drive(this.speed, direction);
    }

    public void stop() {
        drive(0, (short) 0);
    }

    public int getCurrentSpeed() {
        return this.speed;
    }

    public short getCurrentDirection() {
        return this.direction;
    }
}
