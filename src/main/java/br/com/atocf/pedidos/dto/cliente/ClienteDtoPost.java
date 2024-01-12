package br.com.atocf.pedidos.dto.cliente;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.atocf.pedidos.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteDtoPost {
	
	@NotNull
	@NotBlank
	@NotEmpty
	private String name;
	
	public Cliente converter() {		
		return new Cliente(name);
	}	
}
