package br.com.wagnerww.microservice.loja.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.wagnerww.microservice.loja.model.Compra;

@Repository
public interface CompraRepository extends CrudRepository<Compra, Long> {

}
