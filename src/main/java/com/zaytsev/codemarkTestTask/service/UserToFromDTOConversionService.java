package com.zaytsev.codemarkTestTask.service;

import java.util.List;

import com.zaytsev.codemarkTestTask.domain.User;
import com.zaytsev.codemarkTestTask.domain.UserDTO;

public interface UserToFromDTOConversionService
{
	User convertToUser(UserDTO userDTO);
	
	UserDTO convertToDTO(User user);
	
	List<UserDTO> convertToListOfDTO(List<User> users);
}
