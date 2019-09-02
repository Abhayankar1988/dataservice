package com.blackrock.dataservice.service;

import com.blackrock.dataservice.repository.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class InstrumentServiceImpl implements InstrumentService {

    private static final Logger LOGGER = Logger.getLogger(InstrumentServiceImpl.class.getName());

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Override
    @PostConstruct
    public void processInstrumentPrice() {

        InstrumentPriceOrchestrator instrumentPriceOrchestrator = new InstrumentPriceOrchestrator();
        //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream ("sampledata.txt")));
        // BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("sampledata.txt")));
        File file = null;
        //BufferedReader bufferedReader = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("sampledata.txt")));
        instrumentPriceOrchestrator.runInstrumentPriceProducer(bufferedReader);
        instrumentPriceOrchestrator.runInstrumentPriceConsumer(instrumentRepository);
        instrumentPriceOrchestrator.runInstrumentPriceConsumerAt3Seconds(instrumentRepository);
        instrumentPriceOrchestrator.runInstrumentPriceConsumerAt5Seconds(instrumentRepository);


    }
}
