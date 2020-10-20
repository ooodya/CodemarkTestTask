package com.zaytsev.codemarkTestTask.repository;

import org.springframework.data.repository.CrudRepository;

import com.zaytsev.codemarkTestTask.domain.User;

public interface UserRepository extends CrudRepository<User, Long>
{

}
