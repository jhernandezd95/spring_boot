package com.example.crud.modules.store.services;

import com.example.crud.common.http_errors.BadRequestException;
import com.example.crud.common.http_errors.NotFoundException;
import com.example.crud.modules.auth.entities.Role;
import com.example.crud.modules.auth.entities.User;
import com.example.crud.modules.auth.services.AuthService;
import com.example.crud.modules.auth.services.UserService;
import com.example.crud.modules.store.dto.CreateStoreDto;
import com.example.crud.modules.store.entities.Store;
import com.example.crud.modules.store.repositories.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StoreService {

	private StoreRepository storeRepository;
	private UserService userService;
	private AuthService authService;

	public List<Store> getAll() {
		return storeRepository.findAll();
	}


	public Store getById(Long storeId) {
		Optional<Store> store = storeRepository.findById(storeId);

		if (store.isEmpty()) {
			throw new NotFoundException("Store not found with id " + storeId);
		}

		return store.get();
	}

	public void remove(Long storeId) {
		Store store = this.getById(storeId);

		storeRepository.delete(store);
	}

	public Store createStore(CreateStoreDto createStoreDto) {

		User createdBy = this.authService.getLoggedUser();
		User ownerUser = this.userService.getById(createStoreDto.getUserId());

		Role ownerRole = ownerUser.getRoles().stream()
				.filter(role -> role.getName().equals("owner"))
				.findAny()
				.orElse(null);

		if (ownerRole == null) {
			throw new BadRequestException("The user " + ownerUser.getEmail() + " doesn't have the role owner");
		}

		Store store = new Store(createStoreDto.getName(), createStoreDto.getDescription(), ownerUser, createdBy);

		storeRepository.save(store);

		return store;
	}
}
