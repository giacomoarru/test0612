package com.gmail.giacomo.backend.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmail.giacomo.backend.data.Role;
import com.gmail.giacomo.backend.data.entity.AbstractEntity;
import com.gmail.giacomo.backend.data.entity.CreditCard;
import com.gmail.giacomo.backend.data.entity.User;

public interface CrudService<T extends AbstractEntity> {

	JpaRepository<T, Long> getRepository();

	default T save(User currentUser, T entity) {
		if (entity != null
				&& (entity instanceof CreditCard
				&& ((CreditCard) entity).getUser().equals(currentUser))
				|| currentUser.getRole().equals(Role.ADMIN))
			return getRepository().saveAndFlush(entity);
		else
			throw new EntityNotFoundException();
	}
	
	default void delete(User currentUser, T entity) {
		if (entity == null) {
			throw new EntityNotFoundException();
		}
		if (currentUser.getRole().equals(Role.ADMIN) ||
				(entity instanceof CreditCard 
						&& ((CreditCard) entity).getUser().equals(currentUser) ))
				getRepository().delete(entity);
	}

	default void delete(User currentUser, long id) {
		T entity = getRepository().findById(id).orElse(null);
		if (entity != null
				&& (entity instanceof CreditCard
				&& ((CreditCard) entity).getUser().equals(currentUser))
				|| currentUser.getRole().equals(Role.ADMIN))
			delete(currentUser, load(currentUser, id));
	}

	default long count() {
		return getRepository().count();
	}

	default T load(User currentUser, long id) {
		T entity = getRepository().findById(id).orElse(null);
		if (entity == null || (entity instanceof CreditCard
				&& !((CreditCard) entity).getUser().equals(currentUser))
				&& !currentUser.getRole().equals(Role.ADMIN)) {
			throw new EntityNotFoundException();
		}
		return entity;
	}

	T createNew(User currentUser);
}
