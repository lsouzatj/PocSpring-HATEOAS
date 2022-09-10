package com.ead.authUser.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ead.authUser.models.UserModel;
import com.ead.authUser.repositorys.UserRepository;
import com.ead.authUser.services.UserService;

@Service
public final class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public boolean existsUserByUserName(String userName) {
		// TODO Auto-generated method stub
		
		return userRepository.existsUserByUserName(userName);
	}

	@Override
	public boolean existsByEmail(String email) {
		// TODO Auto-generated method stub
		
		return userRepository.existsByEmail(email);
	}

	@Override
	public void save(UserModel userModel) {
		// TODO Auto-generated method stub
		
		userRepository.save(userModel);
	}

	@Override
	public Optional<List<UserModel>> findAll() {
		// TODO Auto-generated method stub
		
		return Optional.of(userRepository.findAll());
	}

	@Override
	public Optional<UserModel> findById(UUID userId) {
		// TODO Auto-generated method stub
		
		return userRepository.findById(userId);
	}

	@Override
	public void delet(UserModel userModel) {
		// TODO Auto-generated method stub
		
		userRepository.delete(userModel);
	}

	@Override
	public Page<UserModel> findAllPageable(Specification<UserModel> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		
		return userRepository.findAll(spec, pageable);
	}

}
