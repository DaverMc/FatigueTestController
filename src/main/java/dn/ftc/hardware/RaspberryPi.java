package dn.ftc.hardware;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import dn.ftc.util.Configuration;

import java.util.HashMap;
import java.util.Map;

public class RaspberryPi {

    private static RaspberryPi instance;

    private final Context pi4j;
    private final Map<String, HardwareIO> hardwareIOMap;
    private final WaveShareADDA addaBoard;

    private RaspberryPi(Configuration configuration) {
        this.pi4j = Pi4J.newAutoContext();
        this.hardwareIOMap = new HashMap<>();
        this.addaBoard = new WaveShareADDA(pi4j, new WaveShareADDA.Config(configuration));
    }

    public void addHardware(String id, HardwareIO hardware) {
        hardwareIOMap.put(id, hardware);
    }

    public <IO extends HardwareIO> IO getHardware(String id, Class<IO> ioClass) {
        return ioClass.cast(hardwareIOMap.get(id));
    }

    public Context getPi4j() {
        return pi4j;
    }

    public WaveShareADDA getADDABoard() {
        return this.addaBoard;
    }

    public static RaspberryPi init(Configuration configuration) {
        if(instance != null) return instance;
        instance = new RaspberryPi(configuration);
        return instance;
    }

    public static RaspberryPi get() {
        return instance;
    }

}
