package com.blackrock.dataservice.repository;

import com.blackrock.dataservice.entity.Instrument;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InstrumentRepositoryTest {

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Test
    public void whenFindingCustomerById_thenCorrect() {
        instrumentRepository.save(new Instrument("GOOG", 144, 20));
        Assertions.assertThat(instrumentRepository.findById(1L)).isInstanceOf(Optional.class);
    }

    @Test
    public void whenFindingAllCustomers_thenCorrect() {
        instrumentRepository.save(new Instrument("VOD", 100.13, 30));
        instrumentRepository.save(new Instrument("BT.L", 100.13, 10));
        Assertions.assertThat(instrumentRepository.findAll()).isInstanceOf(List.class);
    }
}