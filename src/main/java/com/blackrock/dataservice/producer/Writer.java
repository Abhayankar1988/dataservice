package com.blackrock.dataservice.producer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Writer implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(Writer.class.getName());

    protected BlockingQueue<String> blockingQueue = null;
    protected BufferedReader bufferedReader = null;
    private AtomicBoolean atomicBoolean = null;

    public Writer(BlockingQueue<String> blockingQueue, BufferedReader bufferedReader) {
        this.blockingQueue = blockingQueue;
        this.bufferedReader = bufferedReader;
        this.atomicBoolean = new AtomicBoolean();
    }

    @Override
    public void run() {
        try {
            String buffer = null;
            atomicBoolean.set(false);
            int k = 0;
            while (k < 4 && (buffer = bufferedReader.readLine()) != null) {
                blockingQueue.put(buffer);
                atomicBoolean.set(true);
                ++k;
            }
            if (!atomicBoolean.get()) {
                bufferedReader = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("sampledata.txt")));
            }

        } catch (InterruptedException | IOException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            ex.printStackTrace();
        }

    }

}
