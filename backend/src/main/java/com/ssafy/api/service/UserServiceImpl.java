package com.ssafy.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.UserSignUpPostReq;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public User createUser(UserSignUpPostReq req) {
		User user = new User();
		user.setAccountId(req.getAccountId());
		user.setNickname(req.getNickname());
		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
		user.setPassword(passwordEncoder.encode(req.getPassword()));
		user.setEmail(req.getEmail());
		user.setTel(req.getTel());
		return userRepository.save(user);
	}

	@Override
	public User findByAccountId(String accountId) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		return userRepository.findByAccountId(accountId);
	}
}
