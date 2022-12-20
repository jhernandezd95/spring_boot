package com.example.crud.modules.store.controller;

import com.example.crud.modules.store.dto.CreateStoreDto;
import com.example.crud.modules.store.entities.Store;
import com.example.crud.modules.store.services.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Controller
@RequestMapping(path = "/v1")
@AllArgsConstructor
public class StoreController {

	private static final String PATH = "/store";
	private static final String PATH_ONE = PATH + "/{storeId}";
	private StoreService storeService;

	@PostMapping(path = PATH)
	@PreAuthorize("hasAuthority('owner')")
	public @ResponseBody Store createStore(@Validated @RequestBody CreateStoreDto createStoreDto) {
		return storeService.createStore(createStoreDto);
	}

	@GetMapping(path = PATH)
	@PreAuthorize("hasAuthority('admin')")
	public @ResponseBody Iterable<Store> getAllStores() {
		return storeService.getAll();
	}

	@GetMapping(path = PATH_ONE)
	@PreAuthorize("hasAuthority('owner')")
	public @ResponseBody Store getStoreById(@PathVariable @NotNull @DecimalMin("0") Long storeId) {
		return storeService.getById(storeId);
	}

	@DeleteMapping(path = PATH_ONE)
	@PreAuthorize("hasAuthority('owner')")
	public ResponseEntity removeStore(@PathVariable @NotNull @DecimalMin("0") Long storeId) {
		storeService.remove(storeId);
		return ResponseEntity.ok().build();
	}
}
