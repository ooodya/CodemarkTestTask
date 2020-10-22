package com.zaytsev.codemarkTestTask.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO
{
	@NotBlank(message = "{validation.user.name.empty}")
	private String name;
	
	@NotBlank(message = "{validation.user.login.empty}")
	private String login;
	
	@NotBlank(message = "{validation.user.password.empty}")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).+$", message = "{validation.user.password.constraints}")
	private String password;
	
	private Set<RoleDTO> roleDTOs;

}
