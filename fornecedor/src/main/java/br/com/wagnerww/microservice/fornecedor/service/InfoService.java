package br.com.wagnerww.microservice.fornecedor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wagnerww.microservice.fornecedor.model.InfoFornecedor;
import br.com.wagnerww.microservice.fornecedor.repository.InfoRepository;

@Service
public class InfoService {

	@Autowired
	private InfoRepository infoRepository;

	public InfoFornecedor getInfoPorEstado(String estado) {
		System.out.println("chegou " + estado);
		return infoRepository.findByEstado(estado);
	}

}
