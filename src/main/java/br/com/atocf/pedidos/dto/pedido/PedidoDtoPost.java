package br.com.atocf.pedidos.dto.pedido;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.atocf.pedidos.entity.Cliente;
import br.com.atocf.pedidos.entity.Pedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlRootElement(name = "pedido")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class PedidoDtoPost {
	
	@XmlElement
	private Date dtregister;
	
	@NotNull
	@XmlElement
	private Long idclient;
	
	@NotNull
	@NotBlank
	@NotEmpty
	@XmlElement
	private String name;
	
	@NotNull
	@XmlElement
	private Long controlnumber;
	
	@XmlElement
	private Integer qtd;
	
	@NotNull
	@XmlElement
	private Double productvalue;
	
	public Pedido converter(Cliente cliente, Double pedidovalue) {				
		if(dtregister != null) {
			return new Pedido(dtregister, cliente, name, controlnumber, qtd, productvalue, pedidovalue);
		}
		return new Pedido(cliente, name, controlnumber, qtd, productvalue, pedidovalue);
	}	
}
