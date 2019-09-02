package com.blackrock.dataservice.service;

import com.blackrock.dataservice.consumer.Reader;
import com.blackrock.dataservice.producer.Writer;
import com.blackrock.dataservice.repository.InstrumentRepository;

import java.io.BufferedReader;
import java.util.concurrent.*;

public class InstrumentPriceOrchestrator {

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
    private BlockingQueue<String> instrumentsQueue = new LinkedBlockingQueue<>();


    public void runInstrumentPriceProducer(BufferedReader bufferedReader) {
        scheduledExecutorService.scheduleAtFixedRate(new Writer(instrumentsQueue, bufferedReader), 1, 1, TimeUnit.SECONDS);
    }

    public void runInstrumentPriceConsumer(InstrumentRepository instrumentRepository) {
        scheduledExecutorService.scheduleAtFixedRate(new Reader(instrumentsQueue, scheduledExecutorService, instrumentRepository), 1, 1, TimeUnit.SECONDS);
    }

    public void runInstrumentPriceConsumerAt3Seconds(InstrumentRepository instrumentRepository) {
        scheduledExecutorService.scheduleAtFixedRate(new Reader(instrumentsQueue, scheduledExecutorService, instrumentRepository), 1, 1, TimeUnit.SECONDS);
    }

    public void runInstrumentPriceConsumerAt5Seconds(InstrumentRepository instrumentRepository) {
        scheduledExecutorService.scheduleAtFixedRate(new Reader(instrumentsQueue, scheduledExecutorService, instrumentRepository), 1, 1, TimeUnit.SECONDS);
    }
}
