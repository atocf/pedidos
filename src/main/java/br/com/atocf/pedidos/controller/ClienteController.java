package br.com.atocf.pedidos.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.atocf.pedidos.dto.cliente.ClienteDtoPost;
import br.com.atocf.pedidos.dto.cliente.ClienteDtoPut;
import br.com.atocf.pedidos.entity.Cliente;
import br.com.atocf.pedidos.exception.RestExceptionCustom;
import br.com.atocf.pedidos.exception.error.ErrorObject;
import br.com.atocf.pedidos.services.ClienteServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/cliente")
@Api(tags = "Cliente", description = "Endpoint´s incluir, atualizar, deletar ou buscar uma/um Cliente.")
public class ClienteController {
	
	@Autowired
	private ClienteServices service;

	@GetMapping
	@Cacheable(value = "listCliente")
	@ApiOperation("Retorna a lista de Clientes.")
	public Page<Cliente> findAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "50") int size) {
		return service.findAll(page, size);
	}

	@GetMapping(path = { "/{id}" })
	@ApiOperation("Retorna a/o cliente pelo {id}.")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		return service.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	@CacheEvict(value = "listCliente", allEntries = true)
	@ApiOperation("Cria um/uma nova/novo cliente.")
	public ResponseEntity<?> create(@RequestBody @Valid ClienteDtoPost clienteDtoPost) {
		Optional<Cliente> cliente = service.findByName(clienteDtoPost.getName());
		if(!cliente.isPresent()) {
			return new ResponseEntity<Cliente>(service.save(clienteDtoPost.converter()), HttpStatus.CREATED);
		}
		List<ErrorObject> errors = new ArrayList<ErrorObject>();
		errors = RestExceptionCustom.setListError(errors, service.returnErrorExist(cliente.get().getId()));
		return ResponseEntity.badRequest()
				.body(RestExceptionCustom.getErrorResponse("ClienteDtoPost", HttpStatus.BAD_REQUEST, errors));
	}

	@PutMapping(value = "/{id}")
	@CacheEvict(value = "listCliente", allEntries = true)
	@ApiOperation("Atualiza uma/um cliente pelo {id}.")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid ClienteDtoPut clienteDtoPut) {
		return service.findById(id).map(record -> {
			record.setName(clienteDtoPut.getName());
			Cliente updated = service.save(record);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping(path = { "/{id}" })
	@CacheEvict(value = "listCliente", allEntries = true)
	@ApiOperation("Deleta a/o cliente pelo {id}.")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		return service.findById(id).map(record -> {
			service.deleteById(id);
			return ResponseEntity.accepted().build();
		}).orElse(ResponseEntity.notFound().build());
	}
}
