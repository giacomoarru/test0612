package com.gmail.giacomo.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gmail.giacomo.backend.data.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsernameIgnoreCase(String username);

	Page<User> findBy(Pageable pageable);

	Page<User> findByUsernameLikeIgnoreCaseOrRoleLikeIgnoreCase(
			String usernameLike, String roleLike, Pageable pageable);

	long countByUsernameLikeIgnoreCaseOrRoleLikeIgnoreCase(
			String usernameLike, String roleLike);
}
