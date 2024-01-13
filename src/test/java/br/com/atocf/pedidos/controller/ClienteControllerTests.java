package br.com.atocf.pedidos.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.atocf.pedidos.dto.cliente.ClienteDtoPost;
import br.com.atocf.pedidos.entity.Cliente;
import br.com.atocf.pedidos.services.ClienteService;

@ExtendWith(SpringExtension.class)
@DisplayName("Testes para o controller de cliente")
public class ClienteControllerTests {

	@InjectMocks
	private ClienteController controller;
	@Mock
	private static ClienteService service;
	
	private static Cliente cliente1;
	private static Optional<Cliente> dcliente1;
	
	private static ClienteDtoPost clienteDtoPost;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		
		cliente1 = new Cliente("TesteCliente1");
		
		dcliente1 = Optional.of(cliente1);
		dcliente1.get().setId(1);
			
		clienteDtoPost = new ClienteDtoPost("TesteCliente1");
	}

	@Test
	@DisplayName("Cadastrar cliente com sucesso.")
	void create_ComSucesso() {
		when(service.save(clienteDtoPost.converter())).thenReturn(cliente1);
		ResponseEntity<?> http = controller.create(clienteDtoPost);
		assertThat(HttpStatus.CREATED, is(http.getStatusCode()));
		assertThat(dcliente1.get(), is(http.getBody()));
	}
	
	@Test
	@DisplayName("Consultar cliente por ID.")
	void findById_ComSucesso() {
		Long idP1 = cliente1.getId();
		when(service.findById(idP1)).thenReturn(dcliente1);
		ResponseEntity<?> http = controller.findById(idP1);
		assertThat(http.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(dcliente1.get()).isEqualTo( http.getBody());
	}

	@Test
	@DisplayName("Retorna a lista de Clientes.")
	void findAll_ComSucesso() {
		
		List<Cliente> lista = new ArrayList<Cliente>();
		lista.add(cliente1);
		
		Page<Cliente> pageCliente = new PageImpl<>(lista);
				
		when(service.findAll(0,50)).thenReturn(pageCliente);
		
		ResponseEntity<Page<Cliente>> http = controller.findAll(0,50);
		assertThat(http.getStatusCode()).isEqualTo(HttpStatus.OK);		
		assertThat(pageCliente).isEqualTo(http.getBody());
	}
}
