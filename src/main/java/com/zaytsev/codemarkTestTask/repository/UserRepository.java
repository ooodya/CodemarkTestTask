package com.zaytsev.codemarkTestTask.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.zaytsev.codemarkTestTask.domain.User;

public interface UserRepository extends CrudRepository<User, String>
{
	Optional<User> findByLogin(String login);
}
