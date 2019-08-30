package com.gm.box;

import com.gm.box.Box;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxRepository extends CrudRepository<Box, Long> {
}
