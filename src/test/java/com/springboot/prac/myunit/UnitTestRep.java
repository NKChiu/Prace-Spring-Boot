package com.springboot.prac.myunit;

import com.google.gson.Gson;
import com.querydsl.core.BooleanBuilder;
import com.springboot.prac.PracApplication;
import com.springboot.prac.config.TestDataSourceConfig;
import com.springboot.prac.testap.dao.TestTableEbt1Spec;
import com.springboot.prac.testap.dao.TestTableEnt1Dao;
import com.springboot.prac.testap.entity.QTestTableEnt1;
import com.springboot.prac.testap.entity.TestTableEnt1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {PracApplication.class, TestDataSourceConfig.class})
public class UnitTestRep {

    @Autowired
    private TestTableEnt1Dao testTableEnt1Dao;

    @Test
    public void testRepUseSpecif0(){
        List<TestTableEnt1> list = testTableEnt1Dao.findAll((root, query, criteriaBuilder) -> {
            return criteriaBuilder.in(root.get("var1")).value(Arrays.asList("variable1-1", "variable2-1"));
        });
        System.out.println(new Gson().toJson(list));
    }

    @Test
    public void testRepUseSpecif1(){
        List<TestTableEnt1> list = testTableEnt1Dao.findAll(Specification.where(getFilter1()));
        System.out.println(new Gson().toJson(list));
    }

    @Test
    public void testRepUseSpecif2(){
        List<TestTableEnt1> list = testTableEnt1Dao.findAll(Specification.where(getFilter2()));
        System.out.println(new Gson().toJson(list));
    }

    @Test
    public void testRepUseSpecif3(){
        List<TestTableEnt1> list = testTableEnt1Dao.findAll(Specification.where(getFilter3()));
        System.out.println(new Gson().toJson(list));
    }

    @Test
    public void testRepUseSpecif4(){
        List<TestTableEnt1> list = testTableEnt1Dao.findAll(Specification.where(TestTableEbt1Spec.filter1()));
        System.out.println(new Gson().toJson(list));
    }

    @Test
    public void testRepUseQdsl(){
        QTestTableEnt1 qTestTableEnt1 = QTestTableEnt1.testTableEnt1;
        BooleanBuilder builder = new BooleanBuilder();
        builder = builder.and(qTestTableEnt1.var1
                .in(Arrays.asList("variable1-1", "variable2-1")));
        Iterable<TestTableEnt1> list = testTableEnt1Dao.findAll(builder);
        System.out.println(new Gson().toJson(list));
    }

    /***************************************************** private ********************************************************************************/

    private Specification getFilter1(){
        return (root, query, cb) -> cb.in(root.get("var1")).value(Arrays.asList("variable1-1", "variable2-1"));
    }

    private Specification getFilter2(){
        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.in(root.get("var1")).value(Arrays.asList("variable2-1", "variable3-1"));
            }
        };
    }

    private Specification getFilter3(){
        return (root, query, cb) -> {
            Predicate pre = cb.in(root.get("var1")).value(Arrays.asList("variable1-1", "variable3-1"));
            return cb.and(pre);
        };
    }


}
