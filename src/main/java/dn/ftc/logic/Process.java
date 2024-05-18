package dn.ftc.logic;

import dn.ftc.data.DataStream;
import dn.ftc.data.MeasurementData;
import dn.ftc.hardware.ForceSensor;
import dn.ftc.hardware.Piston;
import dn.ftc.hardware.RaspberryPi;
import dn.ftc.util.loging.Logger;

import java.util.concurrent.Executors;

public class Process {
    //Properties
    boolean minLengthSet = true;
    float minLength = -100f;
    boolean maxLengthSet = true;
    float maxLength = 100f;

    boolean minForceSet = true;
    float minForce = -100f;
    boolean maxForceSet = true;
    float maxForce = 100f;

    boolean moveUp = true;
    boolean moveDown = true;
    //Test Change
    int pistonCenter = 50;

    int offsetIterations = 10;
    int maxErrors = 10;

    public static void start() {
       Process process = new Process();
       Executors.newSingleThreadExecutor().execute(process::run);
    }

    private void run() {
        Logger.startUncaughtExceptionHandler();

        //Center Piston
        Piston piston = RaspberryPi.get().getHardware("piston", Piston.class);
        piston.lengthSensor().readOffset(offsetIterations);
        piston.center(pistonCenter);
        piston.lengthSensor().readOffset(offsetIterations);

        //Center Forcesensor
        ForceSensor forceSensor = RaspberryPi.get().getHardware("forceSensor", ForceSensor.class);
        forceSensor.readOffset(offsetIterations);

        //Check starting direction
        if(moveUp) piston.extend();
        else if(moveDown) piston.retract();
        else {
            Logger.error("No direction set!");
            return;
        }

        //Variablen
        float force;
        long i = 0;

        Logger.info("Starting Test...");
        do {
            float rawLength = piston.getExtendedLength();
            float centeredLength = rawLength - pistonCenter;
            force = forceSensor.read();
            long timestamp = System.nanoTime();

            DataStream.instance().publish(new MeasurementData(centeredLength, force, timestamp));

            //Check Length
            if(maxLengthSet && centeredLength >= maxLength) {
                piston.retract();
                i++;
            }else if(minLengthSet && centeredLength <= minLength) {
                piston.extend();
                i++;
            }

            //Check Force
            if(maxForceSet && force >= maxForce) {
                piston.retract();
                i++;
            }else if(minForceSet && force <= minForce) {
                piston.extend();
                i++;
            }

            //Check Piston Range
            if(piston.isExtended()) piston.retract();
            if(piston.isRetracted()) piston.extend();

        } while(checkIteration(i) || checkBroken(force));
        Logger.info("Test finished!");
    }

    private boolean checkIteration(long iteration) {
        boolean iterationsSet = true;
        long iterations = 100;
        if(!iterationsSet) return false;
        return iteration < iterations;
    }

    private boolean checkBroken(float force) {
        int errors = 0;
        if(force == 0) errors++;
        return errors >= maxErrors;
    }

}
