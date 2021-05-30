package com.example.batch.config;

import com.example.batch.data.jpa.entity.User;
import com.example.batch.data.dto.UserCreateDTO;
import com.example.batch.data.jpa.repository.UserRepository;
import com.example.batch.util.DateExtractorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserItemProcessor implements ItemProcessor<UserCreateDTO, User> {

  @Autowired
  UserRepository userRepository;

  private final static Logger logger = LoggerFactory.getLogger(UserItemProcessor.class);


  public User process(UserCreateDTO userDTO) {
    User user = new User();
    user.setFirstName(userDTO.getFirstName());
    user.setLastName(userDTO.getLastName());
    LocalDate date = DateExtractorUtil.extractFromString(userDTO.getDate());
    user.setDate(date);
    logUser(user);
    return user;
  }

  private void logUser(User user) {
    logger.info("saving user to db: {}", user);
  }

}
