package io.github.jonathan.resources;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.github.jonathan.domain.Usuario;
import io.github.jonathan.domain.DTO.UserDTO;
import io.github.jonathan.services.impl.UserServiceImpl;

@SpringBootTest
class UserResourceTest {
	
	@InjectMocks
	private UserResource resource;

	@Mock
	private UserServiceImpl service;
	
	@Mock
	private ModelMapper mapper;
	
	private Usuario user;
	
	private UserDTO userDTO;
	
	private static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado!";

	private static final String EMAIL_JA_CADASTRADO_NO_SISTEMA = "Email já cadastrado no sistema!";

	private static final String PASSWORD = "123";

	private static final String EMAIL = "valdir@email.com";

	private static final String NOME = "valdir";

	private static final Integer ID = 1;

	
	@BeforeEach
	void setup() throws Exception {
		MockitoAnnotations.openMocks(this);
		startUser();
	}
	
	@Test
	void whenFindByIdThenReturnSuccess() {
		when(service.findById(Mockito.anyInt())).thenReturn(user);
		when(mapper.map(any(), any())).thenReturn(userDTO);
		
		ResponseEntity<UserDTO> response = resource.findById(ID);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(UserDTO.class, response.getBody().getClass());
		assertEquals(ID, response.getBody().getId());
		assertEquals(NOME, response.getBody().getName());
		assertEquals(EMAIL, response.getBody().getEmail());
		assertEquals(PASSWORD, response.getBody().getPassword());

	}

	@Test
	void whenFindAllThenReturnAlistOfUserDTO() {
		when(service.findAll()).thenReturn(List.of(user));
		when(mapper.map(any(), any())).thenReturn(userDTO);
		
		ResponseEntity<List<UserDTO>> response = resource.findAll();
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(ArrayList.class, response.getBody().getClass());
		assertEquals(UserDTO.class, response.getBody().get(0).getClass());
		
		assertEquals(ID, response.getBody().get(0).getId());
		assertEquals(NOME, response.getBody().get(0).getName());
		assertEquals(EMAIL, response.getBody().get(0).getEmail());
		assertEquals(PASSWORD, response.getBody().get(0).getPassword());
	}
	
	@Test
	void test() {
		fail("Not yet implemented");
	}

	private void startUser() {
		user = new Usuario(ID, NOME, EMAIL, PASSWORD);
		userDTO = new UserDTO(ID, NOME, EMAIL, PASSWORD);
	}
}
