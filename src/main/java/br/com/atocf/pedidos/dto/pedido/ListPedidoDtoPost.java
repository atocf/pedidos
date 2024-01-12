package br.com.atocf.pedidos.dto.pedido;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlRootElement(name = "pedidos")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ListPedidoDtoPost {
	
	@XmlElement(name = "pedido")
	ArrayList<PedidoDtoPost> pedidos;
}
