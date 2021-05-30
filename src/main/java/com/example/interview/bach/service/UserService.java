package com.example.interview.bach.service;

import com.example.interview.bach.data.dto.UserCreateDTO;
import com.example.interview.bach.data.dto.UserDTO;
import java.util.List;

public interface UserService {

  List<UserDTO> getAll();

}
