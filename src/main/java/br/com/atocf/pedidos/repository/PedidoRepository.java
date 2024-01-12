package br.com.atocf.pedidos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import br.com.atocf.pedidos.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>, QueryByExampleExecutor<Pedido>  {
	
	Optional<Pedido> findByControlnumber(Long controlnumber);
}
