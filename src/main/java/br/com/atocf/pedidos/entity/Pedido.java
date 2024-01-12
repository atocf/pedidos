package br.com.atocf.pedidos.entity;

import java.time.Instant;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	@Column(nullable =  false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP") 
	private Date dtregister = Date.from(Instant.now());
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idclient")
	private Cliente cliente;
	
	@Column(nullable = false, unique = false)
	private String name;
	
	@Column(nullable = false, unique = true)
	private Long controlnumber;
	
	@Column(nullable = false, unique = false, columnDefinition = "INT DEFAULT 1")
	private Integer qtd = 1;
	
	@Column(nullable = false, unique = false)
	private Double productvalue;
	
	@Column(nullable = false, unique = false)
	private Double pedidovalue;

	public Pedido(Date dtregister, Cliente cliente, String name, Long controlnumber, Integer qtd, Double productvalue, Double pedidovalue) {
		super();
		this.dtregister = dtregister;
		this.cliente = cliente;
		this.name = name;
		this.controlnumber = controlnumber;
		this.qtd = qtd;
		this.productvalue = productvalue;
		this.pedidovalue = pedidovalue;
	}
	
	public Pedido(Cliente cliente, String name, Long controlnumber, Integer qtd, Double productvalue, Double pedidovalue) {
		super();
		this.cliente = cliente;
		this.name = name;
		this.controlnumber = controlnumber;
		this.qtd = qtd;
		this.productvalue = productvalue;
		this.pedidovalue = pedidovalue;
	}	
}
