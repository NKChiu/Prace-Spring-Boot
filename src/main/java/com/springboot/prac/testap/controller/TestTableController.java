package com.springboot.prac.testap.controller;

import com.springboot.prac.testap.dao.TestTableEnt1Dao;
import com.springboot.prac.testap.dao.TestTableEnt2Dao;
import com.springboot.prac.testap.entity.TestTableEnt1;
import com.springboot.prac.testap.entity.TestTableEnt2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class TestTableController {

    @Autowired
    private TestTableEnt1Dao testTableEnt1Dao;
    @Autowired
    private TestTableEnt2Dao testTableEnt2Dao;

    /**
     * @deescription from h2 default schema
     */
    @GetMapping(path = "/getTestTableEnt1All")
    @ResponseBody
    public List<TestTableEnt1> getTestTableEnt1All(){
        List<TestTableEnt1> all = testTableEnt1Dao.findAll();
        return all;
    }

    /**
     * @deescription from h2 customer schema
     */
    @GetMapping(path = "/getTestTableEnt2All")
    @ResponseBody
    public List<TestTableEnt2> getTestTableEnt2All(){
        List<TestTableEnt2> all = testTableEnt2Dao.findAll();
        return all;
    }
}
