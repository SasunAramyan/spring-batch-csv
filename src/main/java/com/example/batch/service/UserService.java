package com.example.batch.service;

import com.example.batch.data.dto.UserDTO;
import java.util.List;

public interface UserService {

  List<UserDTO> getAll();

}
