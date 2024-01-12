package br.com.atocf.pedidos.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.atocf.pedidos.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	Page<Cliente> findAll(Pageable paging);

	Optional<Cliente> findByName(String name);
}
