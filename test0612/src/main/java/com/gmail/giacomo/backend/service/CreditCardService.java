package com.gmail.giacomo.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.gmail.giacomo.app.security.CurrentUser;
import com.gmail.giacomo.backend.data.Role;
import com.gmail.giacomo.backend.data.entity.CreditCard;
import com.gmail.giacomo.backend.data.entity.User;
import com.gmail.giacomo.backend.repositories.CreditCardRepository;

@Service
public class CreditCardService implements FilterableCrudService<CreditCard> {

	private final CreditCardRepository creditCardRepository;

	@Autowired
	public CreditCardService(CreditCardRepository creditCardRepository) {
		this.creditCardRepository = creditCardRepository;
	}

	@Override
	public Page<CreditCard> findAnyMatching(Optional<String> filter, CurrentUser currentUser, Pageable pageable) {
		String repositoryFilter;
		if (filter.isPresent())
			repositoryFilter = "%" + filter.get() + "%";
		else
			repositoryFilter = "%";

		if (currentUser.getUser().getRole().equals(Role.ADMIN)) {
			return creditCardRepository.findByNumberLikeIgnoreCase(repositoryFilter, pageable);
		} else {
			return creditCardRepository.findByNumberLikeIgnoreCaseAndUserEquals(repositoryFilter, currentUser.getUser(), pageable);
		}
	}

	@Override
	public long countAnyMatching(Optional<String> filter, CurrentUser currentUser) {
		String repositoryFilter;
		if (filter.isPresent())
			repositoryFilter = "%" + filter.get() + "%";
		else
			repositoryFilter = "%";

		if (currentUser.getUser().getRole().equals(Role.ADMIN)) {
			return creditCardRepository.countByNumberLikeIgnoreCase(repositoryFilter);
		} else {
			return creditCardRepository.countByNumberLikeIgnoreCaseAndUserEquals(repositoryFilter, currentUser.getUser());
		}

	}

	public Page<CreditCard> find(Pageable pageable) {
		return creditCardRepository.findBy(pageable);
	}

	@Override
	public JpaRepository<CreditCard, Long> getRepository() {
		return creditCardRepository;
	}

	@Override
	public CreditCard createNew(User currentUser) {
		return new CreditCard();
	}

	@Override
	public CreditCard save(User currentUser, CreditCard entity) {
		if (entity.getUser() == null) {
			entity.setUser(currentUser);
		}
		
		try {
			return FilterableCrudService.super.save(currentUser, entity);
		} catch (DataIntegrityViolationException e) {
			throw new UserFriendlyDataException(
					"There is already a credit card with that number.");
		}

	}

}
