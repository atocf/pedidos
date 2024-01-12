package br.com.atocf.pedidos.dto.pedido;

import java.util.Date;

import br.com.atocf.pedidos.entity.Cliente;
import br.com.atocf.pedidos.entity.Pedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PedidoFiltro {
	
	private Date dtregister;
	private Long idclient;
	private String name;
	private Long controlnumber;
	private Integer qtd;
	private Double productvalue;
	private Double pedidovalue;
	
	public Pedido converter(Cliente cliente, Double pedidovalue) {				
		return new Pedido(dtregister, cliente, name, controlnumber, qtd, productvalue, pedidovalue);
	}	
}
