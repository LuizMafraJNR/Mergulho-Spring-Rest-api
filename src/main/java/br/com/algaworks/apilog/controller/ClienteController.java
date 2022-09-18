package br.com.algaworks.apilog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.algaworks.apilog.domain.service.CatalogoClienteService;
import br.com.algaworks.apilog.model.Cliente;
import br.com.algaworks.apilog.repository.ClienteRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private CatalogoClienteService catalogoClienteService;
	
	/**
	 * LISTA TODOS OS CLIENTE SEM NENHUM PARAMETRO.
	 * 
	 * @return
	 */
	@GetMapping
	public List<Cliente> listar(){
		return clienteRepository.findAll();
	}
	
	/**
	 * LISTA O CLIENTE PELO SEU ID PASSADO.
	 * 
	 */
	@GetMapping("/{clienteID}")
	public ResponseEntity<Cliente> buscaClienteId(@PathVariable Long clienteID) {
		return clienteRepository.findById(clienteID)
//				.map(cliente -> ResponseEntity.ok(cliente))
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
//		
//		if(cliente.isPresent()) {
//			return ResponseEntity.ok(cliente.get());
//		}
//		return ResponseEntity.notFound().build();
	}
	
	/**
	 * 
	 * BLOCO DE CODIGO PARA PODER ADICIONAR UM CLIENTE USANDO
	 * O CORPO DO POSTMAN NO BODY.
	 * 
	 * @param cliente
	 * @return
	 */
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		
		return catalogoClienteService.salvar(cliente);
	}
	
	/**
	 * BLOCO DE CODIGO QUE FAZ ALTERAÇÃO EM UM CLIENTE
	 * EXISTENTE, PODENDO MUDAR SEUS DADOS.
	 * CASO O CLIENTE NAO EXISTE IRÁ RETORNAR UM 404 NOT FOUND
	 * 
	 * 
	 * @param clienteID - ID DO CLIENTE
	 * @param cliente - DADOS NO BODY
	 * @return
	 */
	@PutMapping("/{clienteID}")
	public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteID, 
			@RequestBody Cliente cliente){
		if(!clienteRepository.existsById(clienteID)) {
			return ResponseEntity.notFound().build();
		}
		cliente.setId(clienteID); // Para não criar um cliente novo.
		cliente = catalogoClienteService.salvar(cliente);
		return ResponseEntity.ok(cliente);
	}
	
	/**
	 * BLOCO DE CODIGO QUE DELETE ALGUM CLIENTE
	 * PELO SEU ID.
	 * 
	 * @param clienteID
	 * @return
	 */
	@DeleteMapping("/{clienteID}")
	public ResponseEntity<Void> remover(@PathVariable Long clienteID){
		if(!clienteRepository.existsById(clienteID)) {
			return ResponseEntity.notFound().build();
		}
		catalogoClienteService.excluir(clienteID);
		return ResponseEntity.noContent().build();
	}
}
