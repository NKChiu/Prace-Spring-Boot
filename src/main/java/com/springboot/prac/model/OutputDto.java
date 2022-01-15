package com.springboot.prac.model;

import lombok.Data;

@Data
public class OutputDto {
    private boolean isSuccess;
    private String returnMessage;
}
