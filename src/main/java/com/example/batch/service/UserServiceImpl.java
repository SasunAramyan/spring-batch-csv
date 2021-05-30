package com.example.batch.service;

import com.example.batch.data.dto.UserDTO;
import com.example.batch.data.jpa.entity.User;
import com.example.batch.data.jpa.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<UserDTO> getAll() {
    List<User> userEntities = userRepository.findAll();
    return userEntities.stream().map(u -> {
      UserDTO userDTO = new UserDTO();
      userDTO.setDate(u.getDate().toString());
      userDTO.setFirstName(u.getFirstName());
      userDTO.setLastName(u.getLastName());
      userDTO.setId(u.getId());
      return userDTO;
    }).collect(Collectors.toList());
  }
}
