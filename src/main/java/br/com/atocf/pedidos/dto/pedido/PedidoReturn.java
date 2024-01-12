package br.com.atocf.pedidos.dto.pedido;

import java.util.List;

import br.com.atocf.pedidos.entity.Pedido;
import br.com.atocf.pedidos.exception.error.ErrorObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PedidoReturn {
	
	List<Pedido> pedidos;
	List<ErrorObject> errors;
}
