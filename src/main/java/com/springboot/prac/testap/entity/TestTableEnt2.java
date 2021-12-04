package com.springboot.prac.testap.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@Entity
@Table(name="TEST_TABLE_ENTITY2")
public class TestTableEnt2 {

    @Id
    @Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
    @SequenceGenerator(name = "generator", sequenceName = "SEQ_TTBE2", allocationSize = 1)
    @GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "COL1", nullable = false)
    private String col1;

    @Column(name = "COL2", nullable = false)
    private String col2;
}
