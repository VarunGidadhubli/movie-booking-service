package com.movie.booking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.movie.booking.dto.RoleRequest;
import com.movie.booking.dto.RoleResponse;
import com.movie.booking.service.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

	private final RoleService roleService;

	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RoleResponse createRole(@Valid @RequestBody RoleRequest roleRequest) {
		return roleService.createRole(roleRequest);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteRole(@PathVariable Long id) {
		roleService.deleteRole(id);
	}
	
	@GetMapping
	public List<RoleResponse> getAllRoles(){
		return roleService.getAllRoles();
	}
	
	@GetMapping("/{id}")
	public RoleResponse getRoleById(@PathVariable Long id) {
		return roleService.getRolebyId(id);
	}
	
	@PutMapping("/{id}")
	public RoleResponse updateRole(@PathVariable Long id, @Valid @RequestBody RoleRequest roleRequest) {
		return roleService.updateRole(id, roleRequest);
	}
}