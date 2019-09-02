package com.blackrock.dataservice.repository;

import com.blackrock.dataservice.entity.Instrument;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentRepository extends CrudRepository<Instrument, Long> {
}
