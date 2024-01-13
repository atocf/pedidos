package br.com.atocf.pedidos.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.atocf.pedidos.entity.Cliente;
import br.com.atocf.pedidos.exception.error.ErrorObject;
import br.com.atocf.pedidos.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;

	private static final Logger log = LoggerFactory.getLogger(ClienteService.class);

	public Page<Cliente> findAll(int page, int size) {
		log.debug("Buscando a/o cliente ordenando pelo nome");
		Pageable paging = PageRequest.of(page, size, Sort.by("name").ascending());
		return repository.findAll(paging);
	}

	public Optional<Cliente> findById(Long id) {
		log.debug("Buscando a/o cliente por id: {}", id);
		return repository.findById(id);
	}
	
	public Optional<Cliente> findByName(String name) {
		log.debug("Buscando a/o cliente pelo nome: {}", name);
		return repository.findByName(name);
	}
		
	public Cliente save(Cliente x) {
		log.debug("Salvando ou Atualização a/o cliente {}", x.getName());
		return repository.save(x);
	}

	public void deleteById(Long id) {
		log.debug("Deletando a/o Cliente por id: {}", id);
		repository.deleteById(id);
	}
	
	public ErrorObject returnErrorExist(Long id) {
		return new ErrorObject("Cliente já existe", "id", id);
	}
	
	public ErrorObject returnErrorNotExiste(Long id) {
		return new ErrorObject("Cliente não cadastrado", "id", id);
	}
}
