package br.com.atocf.pedidos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

import br.com.atocf.pedidos.dto.pedido.ListPedidoDtoPost;
import br.com.atocf.pedidos.dto.pedido.PedidoFiltro;
import br.com.atocf.pedidos.dto.pedido.PedidoReturn;
import br.com.atocf.pedidos.entity.Pedido;
import br.com.atocf.pedidos.services.PedidoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/pedido")
@Api(tags = "Pedido", description = "Endpoint´s incluir, atualizar, deletar ou buscar uma/um Pedido.")

public class PedidoController {

	@Autowired
	private PedidoService service;

	@GetMapping(produces =  { MediaType.APPLICATION_JSON_VALUE })
	@Cacheable(value = "listPedido")
	@ApiOperation("Retorna a lista de Clientes.")
	public ResponseEntity<Page<Pedido>> findAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "50") int size,
			@RequestPayload PedidoFiltro filtro) {
		return ResponseEntity.ok(service.findAll(filtro, page, size));
	}

	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces =  { MediaType.APPLICATION_JSON_VALUE })
	@CacheEvict(value = "listPedido", allEntries = true)
	@ApiOperation("Cria um/uma nova/novo pedido.")
	public ResponseEntity<?> create(@RequestBody @Valid ListPedidoDtoPost pedidoDtoPost) {
		return new ResponseEntity<PedidoReturn>(service.savePedidos(pedidoDtoPost.getPedidos()), HttpStatus.CREATED);
	}

	@DeleteMapping(path = { "/{id}" }, produces =  { MediaType.APPLICATION_JSON_VALUE })
	@CacheEvict(value = "listPedido", allEntries = true)
	@ApiOperation("Deleta a/o pedido pelo {id}.")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		return service.findById(id).map(record -> {
			service.deleteById(id);
			return ResponseEntity.accepted().build();
		}).orElse(ResponseEntity.notFound().build());
	}
}
