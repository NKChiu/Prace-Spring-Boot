package com.springboot.prac.testap.dao;

import com.springboot.prac.testap.entity.TestTableEnt1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TestTableEnt1Dao extends JpaRepository<TestTableEnt1, Integer>, JpaSpecificationExecutor<TestTableEnt1> {

}
