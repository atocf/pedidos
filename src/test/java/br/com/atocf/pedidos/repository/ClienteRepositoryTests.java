package br.com.atocf.pedidos.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.atocf.pedidos.entity.Cliente;

@DataJpaTest
@DisplayName("Teste para o repositório de clientes.")
public class ClienteRepositoryTests {

	@Autowired
	private ClienteRepository repository;

	private static Cliente cliente1;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);

		cliente1 = new Cliente("TesteCliente1");
	}

	@Test
	@DisplayName("Buscar cliente por nome com sucesso.")
	void findByName_ComSucesso() {
		Cliente clienteSalvada = repository.save(cliente1);

		Cliente clienteFind = repository.findByName(cliente1.getName()).get();

		assertThat(clienteFind).isNotNull();

		assertThat(clienteFind.getName()).isEqualTo(clienteSalvada.getName());
	}
}
