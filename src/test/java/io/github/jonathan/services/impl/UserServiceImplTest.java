package io.github.jonathan.services.impl;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.jonathan.domain.Usuario;
import io.github.jonathan.domain.DTO.UserDTO;
import io.github.jonathan.repository.UserRepository;
import io.github.jonathan.services.exceptions.DataIntegratyViolationException;
import io.github.jonathan.services.exceptions.ObjectNotFoundException;

@SpringBootTest
class UserServiceImplTest {
	
	private static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado!";

	private static final String EMAIL_JA_CADASTRADO_NO_SISTEMA = "Email já cadastrado no sistema!";

	private static final String PASSWORD = "123";

	private static final String EMAIL = "valdir@email.com";

	private static final String NOME = "valdir";

	private static final Integer ID = 1;

	@InjectMocks 
	private UserServiceImpl service;
	
	@Mock
	private UserRepository repository;
	
	@Mock
	private ModelMapper mapper;

	private Usuario user;
	
	private UserDTO userDTO;
	
	private Optional<Usuario> optionalUser;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		startUser();
	}

	@Test
	void whenFindByIdThenReturnAnUserInstance() {
		when(repository.findById(anyInt())).thenReturn(optionalUser);
		Usuario response = service.findById(ID);
		
		assertNotNull(response);
		assertEquals(Usuario.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NOME, response.getName());
		assertEquals(EMAIL, response.getEmail());

	}

	@Test
	void whenFindByIdThenReturnObjectNotFoundException() {
		when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));
		try {
			service.findById(ID);
		}catch(Exception ex) {
			assertEquals(ObjectNotFoundException.class, ex.getClass());
			assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());

		}
	}
	
	@Test
	void whenFindAllThenReturnAnListOfUsers() {
		when(repository.findAll()).thenReturn(List.of(user));
		List<Usuario> response = service.findAll();
		assertNotNull(response);
		assertEquals(1, response.size());
		assertEquals(Usuario.class, response.get(0).getClass());
		assertEquals(ID, response.get(0).getId());
		assertEquals(NOME, response.get(0).getName());
		assertEquals(EMAIL, response.get(0).getEmail());
		assertEquals(PASSWORD, response.get(0).getPassword());

	}
	
	@Test
	void whenCreateThenReturnSuccess() {
		when(repository.save(any())).thenReturn(user);
		Usuario response = service.create(userDTO);
		assertNotNull(response);
		assertEquals(Usuario.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NOME, response.getName());
		assertEquals(EMAIL, response.getEmail());
		assertEquals(PASSWORD, response.getPassword());
	}
	
	@Test
	void whenCreateThenReturnAndDataIntegratyViolationException() {
		when(repository.findByEmail(anyString())).thenReturn(optionalUser);
		try {
			optionalUser.get().setId(2);
			service.create(userDTO);
		}catch(Exception ex) {
			assertEquals(DataIntegratyViolationException.class, ex.getClass());
			assertEquals(EMAIL_JA_CADASTRADO_NO_SISTEMA, ex.getMessage());
		}
		
	}
	
	@Test
	void whenUpdateThenReturnSuccess() {
		when(repository.save(any())).thenReturn(user);
		Usuario response = service.update(userDTO);
		assertNotNull(response);
		assertEquals(Usuario.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NOME, response.getName());
		assertEquals(EMAIL, response.getEmail());
		assertEquals(PASSWORD, response.getPassword());
	}
	
	
	@Test
	void whenUpdateThenReturnAndDataIntegratyViolationException() {
		when(repository.findByEmail(anyString())).thenReturn(optionalUser);
		try {
			optionalUser.get().setId(2);
			service.create(userDTO);
		}catch(Exception ex) {
			assertEquals(DataIntegratyViolationException.class, ex.getClass());
			assertEquals(EMAIL_JA_CADASTRADO_NO_SISTEMA, ex.getMessage());
		}
		
	}
	
	@Test
	void deleteWithSuccess() {
		when(repository.findById(anyInt())).thenReturn(optionalUser);
		doNothing().when(repository).deleteById(anyInt());
		service.delete(ID);
		verify(repository, times(1)).deleteById(anyInt());;
	}
	
	@Test
	void deleteWithObjectNotFoundException() {
		when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));
		try {
			service.delete(ID);
			
		}catch(Exception ex) {
			assertEquals(ObjectNotFoundException.class, ex.getClass());
			assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
		}
	}
	
	private void startUser() {
		user = new Usuario(ID, NOME, EMAIL, PASSWORD);
		userDTO = new UserDTO(ID, NOME, EMAIL, PASSWORD);
		optionalUser = Optional.of(new Usuario(ID, NOME, EMAIL, PASSWORD));

	}
}
