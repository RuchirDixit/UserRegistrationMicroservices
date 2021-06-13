package com.bridgelabz.userregistrationproblem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.userregistrationproblem.entity.UserEntity;

public interface UserRegistrationRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByEmailId(String emailId);
}
