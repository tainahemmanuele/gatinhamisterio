package com.gm.box;

import com.gm.box.Box;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableScan
public interface BoxRepository extends CrudRepository<Box, Long> {
}
