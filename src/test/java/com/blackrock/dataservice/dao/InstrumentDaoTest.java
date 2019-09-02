package com.blackrock.dataservice.dao;

import com.blackrock.dataservice.entity.Instrument;
import com.blackrock.dataservice.repository.InstrumentRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InstrumentDaoTest {

    @Autowired
    private InstrumentRepository instrumentRepository;

    private InstrumentDao instrumentDao = null;

    @Before
    public void setUp() throws Exception {
        instrumentRepository.save(new Instrument("GOOG", 100, 21));
        instrumentRepository.save(new Instrument("VOD.L", 85, 21));
        instrumentRepository.save(new Instrument("BP.L", 1450, 21));
        instrumentRepository.save(new Instrument("BT.L", 86.67, 21));

        instrumentRepository.save(new Instrument("GOOG", 333, 22));
        instrumentRepository.save(new Instrument("VOD.L", 143, 22));
        instrumentRepository.save(new Instrument("BP.L", 145, 22));
        instrumentRepository.save(new Instrument("BT.L", 189, 22));

        instrumentRepository.save(new Instrument("GOOG", 132, 23));
        instrumentRepository.save(new Instrument("VOD.L", 166, 23));
        instrumentRepository.save(new Instrument("BP.L", 4667, 23));
        instrumentRepository.save(new Instrument("BT.L", 5562, 23));

        instrumentDao = new InstrumentDao();

    }

    @After
    public void tearDown() {
        instrumentDao = null;
    }


    @Test
    public void displaySecondHighestPriceForAllInstruments() {
        List<String> secondHighestPrice = instrumentDao.displaySecondHighestPriceForAllInstruments();
        Assert.assertEquals(secondHighestPrice.get(0), "GOOG 132.0");
        Assert.assertEquals(secondHighestPrice.get(1), "VOD.L 143.0");
        Assert.assertEquals(secondHighestPrice.get(2), "BP.L 1450.0");
        Assert.assertEquals(secondHighestPrice.get(3), "BT.L 189.0");

    }

    @Test
    public void displayAveragePriceForAllInstrumentsInLastTenSec() {
        List<String> averagePrice = instrumentDao.displayAveragePriceForAllInstrumentsInLastTenSec();
        Assert.assertEquals(averagePrice.get(0), "GOOG 188.33");
        Assert.assertEquals(averagePrice.get(1), "VOD.L 131.33");
        Assert.assertEquals(averagePrice.get(2), "BP.L 2087.33");
        Assert.assertEquals(averagePrice.get(3), "BT.L 1945.89");

    }
}