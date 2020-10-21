package com.zaytsev.codemarkTestTask.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.zaytsev.codemarkTestTask.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Long>
{
	Optional<Role> findByName(String name);
}
