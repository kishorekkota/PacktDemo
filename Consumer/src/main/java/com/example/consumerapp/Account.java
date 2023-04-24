package com.example.consumerapp;

import lombok.Data;

import java.util.Date;

@Data
public class Account {

    String accountId;
    String name;
    String email;
    boolean active;
    String opendate;


}
