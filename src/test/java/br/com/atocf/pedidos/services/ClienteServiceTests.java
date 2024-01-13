package br.com.atocf.pedidos.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.atocf.pedidos.entity.Cliente;
import br.com.atocf.pedidos.repository.ClienteRepository;

@ExtendWith(SpringExtension.class)
@DisplayName("Testes para o serviço de cliente")
public class ClienteServiceTests {

	@InjectMocks
	private ClienteService service;

	@Mock
	private ClienteRepository repository;
	
	private static Cliente cliente1;
	private static Cliente cliente2;
	private static Optional<Cliente> dcliente1;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		
		cliente1 = new Cliente("TesteCliente1");
		cliente2 = new Cliente("TesteCliente2");
		
		dcliente1 = Optional.of(cliente1);
		dcliente1.get().setId(1);
	}

	@Test
	@DisplayName("Teste cadastrando cliente")
	void cadastrar_CadastroPessoa_ComSucesso() {
		when(repository.save(cliente1)).thenReturn(dcliente1.get());

		Cliente clienteS = service.save(cliente1);

		assertThat(clienteS).isNotNull();
		assertThat(dcliente1.get().getName()).isEqualTo(cliente1.getName());	
	}
	
	@Test
	@DisplayName("Consultar pessoa por ID.")
	void findById_ConsultarPessoaPorID_ComSucesso() {
		Long idP1 = 1l;
		when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(dcliente1);

		Optional<Cliente> infoCliente = service.findById(idP1);

		assertThat(infoCliente).isEqualTo(dcliente1);
	}
	
	@Test
	@DisplayName("Retorna a lista de Clientes.")
	void findAll_ComSucesso() {
		List<Cliente> lista = new ArrayList<Cliente>();
		lista.add(cliente1);
		lista.add(cliente2);
		
		Page<Cliente> pageCliente = new PageImpl<>(lista);
		
		when(repository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(pageCliente);
		
		Page<Cliente> listaClientes = service.findAll(0,50);
		
		assertThat(listaClientes.getContent()).hasSize(2);
	}
}
