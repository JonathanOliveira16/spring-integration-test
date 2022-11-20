package io.github.jonathan.services;

import java.util.List;

import io.github.jonathan.domain.Usuario;
import io.github.jonathan.domain.DTO.UserDTO;

public interface UserService {

	Usuario findById(Integer id);
	
	List<Usuario> findAll();
	
	Usuario create(UserDTO obj);
	
	Usuario update (UserDTO obj);
	
	void delete(Integer id);
}
