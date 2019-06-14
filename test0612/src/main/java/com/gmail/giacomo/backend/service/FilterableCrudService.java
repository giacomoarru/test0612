package com.gmail.giacomo.backend.service;

import java.util.Optional;

import com.gmail.giacomo.app.security.CurrentUser;
import com.gmail.giacomo.backend.data.entity.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilterableCrudService<T extends AbstractEntity> extends CrudService<T> {

	Page<T> findAnyMatching(Optional<String> filter, CurrentUser currentUser, Pageable pageable);

	long countAnyMatching(Optional<String> filter, CurrentUser currentUser);

}
