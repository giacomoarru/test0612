package com.gmail.giacomo.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gmail.giacomo.app.security.CurrentUser;
import com.gmail.giacomo.backend.data.entity.CreditCard;
import com.gmail.giacomo.backend.data.entity.User;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

	Page<CreditCard> findBy(Pageable page);

	Page<CreditCard> findByNumberLikeIgnoreCase(String name, Pageable page);

	int countByNumberLikeIgnoreCase(String name);

	Page<CreditCard> findByNumberLikeIgnoreCaseAndUserEquals(String name, User user, Pageable page);

	int countByNumberLikeIgnoreCaseAndUserEquals(String name, User user);
}
