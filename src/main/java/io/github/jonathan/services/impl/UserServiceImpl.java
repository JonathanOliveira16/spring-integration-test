package io.github.jonathan.services.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.jonathan.domain.Usuario;
import io.github.jonathan.domain.DTO.UserDTO;
import io.github.jonathan.repository.UserRepository;
import io.github.jonathan.services.UserService;
import io.github.jonathan.services.exceptions.DataIntegratyViolationException;
import io.github.jonathan.services.exceptions.ObjectNotFoundException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public Usuario findById(Integer id) {
		Optional<Usuario> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
	}

	public List<Usuario> findAll(){
		return repository.findAll();
	}

	@Override
	public Usuario create(UserDTO obj) {
		findByEmail(obj);
		return repository.save(mapper.map(obj, Usuario.class));
	}
	
	private void findByEmail(UserDTO obj) {
		Optional<Usuario> user = repository.findByEmail(obj.getEmail());
		if(user.isPresent() && !user.get().getId().equals(obj.getId())) {
			throw new DataIntegratyViolationException("Email já cadastrado no sistema!");
		}
	}

	@Override
	public Usuario update(UserDTO obj) {
		findByEmail(obj);
		return repository.save(mapper.map(obj, Usuario.class));
	}

	@Override
	public void delete(Integer id) {
		findById(id);
		repository.deleteById(id);
	}
}
