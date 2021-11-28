package com.springboot.prac.testap.dao;

import com.springboot.prac.testap.entity.TestTableEnt2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestTableEnt2Dao extends JpaRepository<TestTableEnt2, Integer> {
}
