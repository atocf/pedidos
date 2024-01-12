package br.com.atocf.pedidos;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.atocf.pedidos.controller.ClienteController;
import br.com.atocf.pedidos.controller.PedidoController;

@SpringBootTest
class PedidosApplicationTests {
	
	@Autowired
	ClienteController clienteController;
	
	@Autowired
	PedidoController pedidoController;
	 
	@Test
	void contextLoads() {
		Assertions.assertThat(clienteController).isNotNull();
		Assertions.assertThat(pedidoController).isNotNull();
	}
}
