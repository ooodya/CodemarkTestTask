package com.zaytsev.codemarkTestTask.service;

import java.util.List;
import java.util.Optional;

import com.zaytsev.codemarkTestTask.dto.UserDTO;

public interface UserService
{
	List<UserDTO> findAll();
	void save(UserDTO userDTO);
	void delete(UserDTO userDTO);
	Optional<UserDTO> findByLogin(String login);
}
