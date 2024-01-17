package com.jt.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jt.model.auth.Role;
import com.jt.model.auth.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
	User findByRole(Role role) ;
	Optional<User> findByUserName(String email) ;
}
