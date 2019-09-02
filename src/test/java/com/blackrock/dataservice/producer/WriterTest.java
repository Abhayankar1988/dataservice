package com.blackrock.dataservice.producer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.concurrent.*;

public class WriterTest {
    Writer writerThread = null;

    BufferedReader bufferedReader = null;
    BlockingQueue<String> blockingQueue = null;

    @Before
    public void setUp() throws Exception {
        blockingQueue = new LinkedBlockingQueue<>();
        bufferedReader = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream ("sampledata.txt")));
    }

    @After
    public void tearDown() {
        blockingQueue = null;
        bufferedReader = null;
    }

    @Test
    public void testSizeOfQueue() throws InterruptedException {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        writerThread = new Writer(blockingQueue, bufferedReader);
        scheduler.scheduleAtFixedRate(writerThread, 1, 1, TimeUnit.SECONDS);
        scheduler.awaitTermination(2, TimeUnit.SECONDS);
        scheduler.shutdownNow();
        Assert.assertEquals(8, blockingQueue.size());
    }
}