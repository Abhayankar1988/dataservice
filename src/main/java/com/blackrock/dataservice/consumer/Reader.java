package com.blackrock.dataservice.consumer;

import com.blackrock.dataservice.dao.InstrumentDao;
import com.blackrock.dataservice.entity.Instrument;
import com.blackrock.dataservice.repository.InstrumentRepository;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;


public class Reader implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(Reader.class.getName());

    private BlockingQueue<String> blockingQueue;
    private AtomicInteger timer;
    private ScheduledExecutorService scheduledExecutorService;
    private InstrumentRepository instrumentRepository;
    private InstrumentDao instrumentDao;


    public Reader(BlockingQueue<String> blockingQueue, ScheduledExecutorService scheduler, InstrumentRepository instrumentRepository) {
        this.blockingQueue = blockingQueue;
        this.scheduledExecutorService = scheduler;
        this.instrumentRepository = instrumentRepository;
        this.timer = new AtomicInteger(0);
        this.instrumentDao = new InstrumentDao();
    }

    public AtomicInteger getTimer() {
        return timer;
    }

    @Override
    public void run() {

        try {
            processInstrumentsFromQueue();

            if (timer.get() == 30) {
                generateReport();
                scheduledExecutorService.shutdown();
            }
        } catch (RuntimeException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            ex.printStackTrace();
        }
    }

    private void generateReport() {

        List<String> averagePrice = instrumentDao.displayAveragePriceForAllInstrumentsInLastTenSec();
        LOGGER.info("Average Price for all Instrument in last 10 sec ");
        averagePrice.forEach(LOGGER::info);

        List<String> secondHighestPrice = instrumentDao.displaySecondHighestPriceForAllInstruments();
        LOGGER.info("Second Highest Price for all Instruments ");
        secondHighestPrice.forEach(LOGGER::info);
    }

    private void processInstrumentsFromQueue() {

        timer.getAndIncrement();

        if(timer.get() % 3 ==0 || timer.get() % 5 ==0){

            return;
        }

        IntStream.range(0, 4).forEach(x -> {
            String value = null;
            String instrument[] = null;
            try {
                value = blockingQueue.take();
            } catch (InterruptedException ex) {
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
                ex.printStackTrace();
            }

            if (value != null) {
                instrument = value.split(":");
            } else {
                LOGGER.log(Level.WARNING, "Instrument is null", value);
                return;
            }

            LOGGER.info(timer.get() + " " + value);
           /* if (timer.get() % 3 == 0) {
                if (instrument[0].equals("GOOG") || instrument[0].equals("BP.L")) {
                    saveInstrument(instrument);
                }
            }

            if (timer.get() % 5 == 0) {
                if (instrument[0].equals("BT.L") || instrument[0].equals("VOD.L")) {
                    saveInstrument(instrument);
                }
            }*/
        });
    }

    private void saveInstrument(String instrument[]) {
        instrumentRepository.save(new Instrument(instrument[0], Double.parseDouble(instrument[1]), timer.intValue()));
        LOGGER.info(timer.get() + " " + instrument[0] + " updated");

    }


}
