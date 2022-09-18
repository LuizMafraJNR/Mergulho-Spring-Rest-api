package br.com.algaworks.apilog.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.algaworks.apilog.model.Cliente;
import br.com.algaworks.apilog.repository.ClienteRepository;
import lombok.AllArgsConstructor;

/**
 * IMPLEMENTANDO DOMAIN SERVICE (Regra de negocio)
 * 
 * @author luizm
 *
 */

@AllArgsConstructor
@Service
public class CatalogoClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Transactional // - Ela declara que esse metodo deve ser executado dentro de uma
	public Cliente salvar(Cliente cliente) {
	// transição
		return clienteRepository.save(cliente);
	}
	
	@Transactional
	public void excluir(Long clienteID) {
		clienteRepository.deleteById(clienteID);
	}
}
