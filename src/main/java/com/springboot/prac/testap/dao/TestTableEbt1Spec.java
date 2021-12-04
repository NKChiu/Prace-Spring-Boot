package com.springboot.prac.testap.dao;

import com.springboot.prac.testap.entity.TestTableEnt1;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;

public class TestTableEbt1Spec {

    public static Specification<TestTableEnt1> filter1(){
        return new Specification<TestTableEnt1>() {
            @Override
            public Predicate toPredicate(Root<TestTableEnt1> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.in(root.get("var1")).value(Arrays.asList("variable1-1", "variable2-1", "variable3-1"));
            }
        };
    }
}
