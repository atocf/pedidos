package br.com.atocf.pedidos.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.atocf.pedidos.dto.pedido.PedidoDtoPost;
import br.com.atocf.pedidos.dto.pedido.PedidoFiltro;
import br.com.atocf.pedidos.dto.pedido.PedidoReturn;
import br.com.atocf.pedidos.entity.Cliente;
import br.com.atocf.pedidos.entity.Pedido;
import br.com.atocf.pedidos.exception.RestExceptionCustom;
import br.com.atocf.pedidos.exception.error.ErrorObject;
import br.com.atocf.pedidos.repository.PedidoRepository;

@Service
public class PedidoServices {
	
	@Autowired
	private PedidoRepository repository;
	
	@Autowired
	private ClienteServices clienteServices;

	private static final Logger log = LoggerFactory.getLogger(PedidoServices.class);

	public Optional<Pedido> findById(Long id) {
		log.debug("Buscando um pedido por id: {}", id);
		return repository.findById(id);
	}
	
	public Optional<Pedido> findByControlnumber(Long controlnumber) {
		log.debug("Buscando um pedido pelo numero de controle: {}", controlnumber);
		return repository.findByControlnumber(controlnumber);
	}

	public void deleteById(Long id) {
		log.debug("Deletando um Pedido por id: {}", id);
		repository.deleteById(id);
	}
	
	public ErrorObject returnErrorFormato(String format) {
		return new ErrorObject("Os formatos aceitos são JSON ou XML.", "format", format);
	}
	
	public ErrorObject returnErrorControlNumber(Long controlnumber) {
		return new ErrorObject("Numero de controle já existe.", "controlnumber", controlnumber);
	}
	
	public ErrorObject returnErrorQtdPedidos(int qtd) {
		return new ErrorObject("A solicitação ou arquivo deve conter 1 ou mais pedidos, limitado a 10", "pedidos enviados", qtd);
	}

	public PedidoReturn savePedidos(@Valid List<PedidoDtoPost> pedidoDtoPost) {
		PedidoReturn pedidoReturn = new PedidoReturn();
		List<ErrorObject> errors = new ArrayList<ErrorObject>();
		List<Pedido> pedidos = new ArrayList<Pedido>();
		
		log.debug("Validando qtd de pedidos enviados.");
		if(pedidoDtoPost.size() == 0) {
			errors = RestExceptionCustom.setListError(errors, returnErrorQtdPedidos(pedidoDtoPost.size()));
		} else if(pedidoDtoPost.size() > 10) {
			errors = RestExceptionCustom.setListError(errors, returnErrorQtdPedidos(pedidoDtoPost.size()));
		} else {		
			log.debug("Iniciando leitura dos pedidos.");
			for(PedidoDtoPost p : pedidoDtoPost) {
				log.debug("Validando se o cliente {} existe.", p.getIdclient());
				Optional<Cliente> cliente = clienteServices.findById(p.getIdclient());
				if(!cliente.isPresent()) {
					errors = RestExceptionCustom.setListError(errors, clienteServices.returnErrorNotExiste(p.getIdclient()));
				} else {
					log.debug("Validando se o numero de controle existe {} existe.", p.getControlnumber());
					Optional<Pedido> controlnumber = findByControlnumber(p.getControlnumber());
					if(controlnumber.isPresent()) {
						errors = RestExceptionCustom.setListError(errors, returnErrorControlNumber(controlnumber.get().getControlnumber()));
					} else {
						log.debug("Verificando a qtd de produtos para aplicação do desconto.");
						if(p.getQtd() == null) {
							p.setQtd(1);
							
						} 
						double  v = p.getProductvalue() * p.getQtd();
						
						if(p.getQtd() > 5 && p.getQtd() < 10) {
							v = v - (v * 0.05);
						} else if(p.getQtd() >= 10) {
							v = v - (v * 0.10);
						}
						
						log.debug("Salvando o pedido com o numero de controle {}", p.getControlnumber());
						pedidos.add(repository.save(p.converter(cliente.get(), v)));
					}
				}
			}
			log.debug("Finalisando leitura dos pedidos.");
		}
		pedidoReturn.setErrors(errors);
		pedidoReturn.setPedidos(pedidos);
		return pedidoReturn;
	}

	public Page<Pedido> findAll(PedidoFiltro filtro, int page, int size) {
		Pageable paging = PageRequest.of(page, size, Sort.by("controlnumber").ascending());
		if(filtro.getIdclient() != null) {
			log.debug("Validando se o cliente {} existe.", filtro.getIdclient());
			Optional<Cliente> cliente = clienteServices.findById(filtro.getIdclient());
			if(cliente.isPresent()) {
				log.debug("Buscando pedidos ordenado pelo numero de controle");
				return repository.findAll(Example.of(filtro.converter(cliente.get(), filtro.getPedidovalue())), paging);
			} 
		} else {
			return repository.findAll(Example.of(filtro.converter(null, filtro.getPedidovalue())), paging);
		}
		return new PageImpl<>(null);
	}
}
