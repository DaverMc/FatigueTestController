package dn.ftc.data;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class DataStream {

    private static DataStream instance;

    private final Deque<DataPack> dataPackQueue;
    private final List<DataReceiver> receiverList;
    private final int threadCount;

    private volatile boolean empty = true;
    private volatile boolean running = false;

    private ScheduledExecutorService service;

    private DataStream(int threadCount) {
        this.dataPackQueue = new LinkedList<>();
        this.receiverList = new ArrayList<>();
        this.threadCount = threadCount;
    }

    private DataStream() {
        this(5);
    }

    public void publish(DataPack dataPack) {
        dataPackQueue.add(dataPack);
        empty = false;
    }

    public void startHandling() {
        if(running) return;
        running = true;
        service = Executors.newScheduledThreadPool(threadCount);
        service.submit(this::handle);
    }

    public void stopHandling() {
        if(!running) return;
        running = false;
        if(service != null) service.shutdownNow();
    }

    public void handle() {
        while(running) {
            while (!empty) {
                DataPack pack = dataPackQueue.remove();
                receiverList.forEach(dataReceiver -> dataReceiver.onData(pack));
                empty = dataPackQueue.isEmpty();
            }
        }
    }

    public static DataStream instance() {
        if(instance == null) instance = new DataStream();
        return instance;
    }
}
