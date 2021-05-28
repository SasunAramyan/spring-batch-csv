package com.example.interview.demo.config;

import com.example.interview.demo.model.User;
import com.example.interview.demo.model.UserDTO;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserItemProcessor implements ItemProcessor<UserDTO, User> {

    public User process(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        String clearDateFormat = userDTO.getDate().replaceAll("(?<=\\d)(st|nd|rd|th)", "");
        LocalDate date = LocalDate.parse(clearDateFormat, DateTimeFormatter.ofPattern("MMMM d, yyyy"));
        user.setDate(date);
        return user;
    }

}