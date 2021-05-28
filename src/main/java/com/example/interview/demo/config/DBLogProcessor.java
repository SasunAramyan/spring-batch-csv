package com.example.interview.demo.config;

import com.example.interview.demo.model.User;
import org.springframework.batch.item.ItemProcessor;

public class DBLogProcessor implements ItemProcessor<User, User>
{
    public User process(User employee) throws Exception
    {
        System.out.println("Inserting employee : " + employee);
        return employee;
    }
}