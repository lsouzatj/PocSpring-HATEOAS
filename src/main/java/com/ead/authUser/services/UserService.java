package com.ead.authUser.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.ead.authUser.models.UserModel;

public interface UserService {

	boolean existsUserByUserName(String userName);

	boolean existsByEmail(String email);

	void save(UserModel userModel);

	Optional<List<UserModel>> findAll();

	Optional<UserModel> findById(UUID userId);

	void delet(UserModel userModel);
	
	Page<UserModel> findAllPageable(Specification<UserModel> spec, Pageable pageable);

}
