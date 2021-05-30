package com.example.interview.demo.config;

import com.example.interview.demo.model.entity.User;
import com.example.interview.demo.model.dto.UserDTO;
import com.example.interview.demo.model.entity.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserItemProcessor implements ItemProcessor<UserDTO, User> {

  @Autowired
  UserRepository userRepository;

  private final static Logger logger = LoggerFactory.getLogger(UserItemProcessor.class);


  public User process(UserDTO userDTO) {
    User user = new User();
    user.setFirstName(userDTO.getFirstName());
    user.setLastName(userDTO.getLastName());
    String clearDateFormat = userDTO.getDate().replaceAll("(?<=\\d)(st|nd|rd|th)", "");
    LocalDate date = LocalDate.parse(clearDateFormat, DateTimeFormatter.ofPattern("MMMM d, yyyy"));
    user.setDate(date);
    logger.info("saving user to db: {}", user);
    return user;
  }

}
