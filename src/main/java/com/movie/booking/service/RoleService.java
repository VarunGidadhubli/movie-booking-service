package com.movie.booking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.movie.booking.dto.RoleRequest;
import com.movie.booking.dto.RoleResponse;
import com.movie.booking.entity.Role;
import com.movie.booking.exception.RoleAlreadyExistsException;
import com.movie.booking.exception.RoleNotFoundException;
import com.movie.booking.repository.RoleRepository;

import jakarta.transaction.Transactional;

@Service
public class RoleService {

	private final RoleRepository roleRepository;

	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	public RoleResponse createRole(RoleRequest roleRequest) {
		
		String name=roleRequest.getName().trim();
		if(roleRepository.existsByNameIgnoreCase(name)) {
			throw new RoleAlreadyExistsException("Role "+name+" already exists");
		}
		
		Role role=new Role(roleRequest.getName());
		Role createdRole=roleRepository.save(role);
		return new RoleResponse(createdRole.getId(), createdRole.getName());
	}
	
	@Transactional
	public void deleteRole(Long id) {
		Role role = roleRepository.findById(id).orElseThrow(()->new RoleNotFoundException("Role not found with id: " + id));
		roleRepository.delete(role);
	}
	
	public List<RoleResponse> getAllRoles(){
		return roleRepository.findAll().stream().map(role->new RoleResponse(role.getId(), role.getName())).toList();
	}
	
	public RoleResponse getRolebyId(Long id) {
		Role role=roleRepository.findById(id).orElseThrow(()->new RoleNotFoundException("Role not found with id: " + id));
		return new RoleResponse(role.getId(),role.getName());
	}
	
	public RoleResponse updateRole(Long id, RoleRequest roleRequest) {
		
		Role role=roleRepository.findById(id).orElseThrow(()->new RoleNotFoundException("Role not found with id: " + id));
		
		String name=roleRequest.getName().trim();
		if(roleRepository.existsByNameIgnoreCase(name)) {
			throw new RoleAlreadyExistsException("Role "+name+" already exists");
		}
		
		role.setName(roleRequest.getName());
		Role updatedRole=roleRepository.save(role);
		return new RoleResponse(updatedRole.getId(), updatedRole.getName());
	}
	
	
}