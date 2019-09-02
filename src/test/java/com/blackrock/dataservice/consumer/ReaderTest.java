package com.blackrock.dataservice.consumer;

import com.blackrock.dataservice.entity.Instrument;
import com.blackrock.dataservice.repository.InstrumentRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.BufferedReader;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Mockito.*;

public class ReaderTest {

    private Reader readerThread = null;

    private BufferedReader bufferedReader = null;
    private BlockingQueue<String> blockingQueue = null;

    private AtomicInteger atomicInteger;

    private InstrumentRepository instrumentRepository;

    private ScheduledExecutorService executor;

    @Before
    public void setUp() {
        blockingQueue = mock(BlockingQueue.class);
        instrumentRepository = mock(InstrumentRepository.class);
        atomicInteger = mock(AtomicInteger.class);
        executor = mock(ScheduledExecutorService.class);
        readerThread = new Reader(blockingQueue, executor, instrumentRepository);
    }

    @After
    public void tearDown() {
        blockingQueue = null;
        bufferedReader = null;
        atomicInteger = null;
        executor = null;
        readerThread = null;
    }


    @Test
    public void testQueueSizeAndInvocation() throws InterruptedException {

        when(blockingQueue.take()).thenReturn("BT.L:100");
        readerThread.run();
        Assert.assertEquals(1, readerThread.getTimer().get());
        Assert.assertEquals(0, blockingQueue.size());
        verify(blockingQueue, times(4)).take();
    }

    @Test
    public void testInvocationOfPriceRepository() throws InterruptedException {
        when(blockingQueue.take()).thenReturn("GOOG:100");
        when(blockingQueue.size()).thenReturn(12);
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        executorService.scheduleAtFixedRate(readerThread, 1, 1, TimeUnit.SECONDS);
        executorService.awaitTermination(4, TimeUnit.SECONDS);
        ArgumentCaptor<Instrument> captor = ArgumentCaptor.forClass(Instrument.class);
        verify(instrumentRepository, times(4)).save(captor.capture());

        Optional<Instrument> price = captor.getAllValues().stream().findAny();
        Assert.assertEquals(100, price.get().getPrice(), 0);
        Assert.assertEquals("GOOG", price.get().getProduct());
    }
}