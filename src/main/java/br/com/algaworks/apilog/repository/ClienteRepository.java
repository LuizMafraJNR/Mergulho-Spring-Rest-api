package br.com.algaworks.apilog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.algaworks.apilog.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	public Cliente findByNome();
}