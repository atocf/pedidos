package br.com.atocf.pedidos;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import br.com.atocf.pedidos.controller.ClienteController;
import br.com.atocf.pedidos.controller.PedidoController;
import br.com.atocf.pedidos.entity.Cliente;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SystemTests {
	
	@Autowired
	ClienteController clienteController;
	
	@Autowired
	PedidoController pedidoController;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testCreateReadDelete() {
		String url = "http://localhost:"+port+"/cliente";

		Cliente clientePost = new Cliente("Teste Cliente 1");
		ResponseEntity<Cliente> entity = restTemplate.postForEntity(url, clientePost, Cliente.class);

		restTemplate.delete(url + "/" + entity.getBody().getId());
		Assertions.assertThat(ResponseEntity.accepted().build());
	}

	@Test
	public void testErrorHandlingReturnsBadRequest() {
		String url = "http://localhost:"+port+"/wrong";
		try {
			restTemplate.getForEntity(url, String.class);
		} catch (HttpClientErrorException e) {
			Assertions.assertThat(e.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		}
	}	
}
