package br.com.atocf.pedidos.dto.cliente;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteDtoPut {
	
	@NotNull
	@NotBlank
	@NotEmpty
	private String name;
}
