package br.com.wagnerww.microservice.loja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wagnerww.microservice.loja.client.FornecedorClient;
import br.com.wagnerww.microservice.loja.controller.dto.CompraDTO;
import br.com.wagnerww.microservice.loja.controller.dto.InfoFornecedorDTO;
import br.com.wagnerww.microservice.loja.dto.InfoPedidoDTO;
import br.com.wagnerww.microservice.loja.model.Compra;

@Service
public class CompraService {

	@Autowired
	private FornecedorClient fornecedorClient;

	public Compra realizaCompra(CompraDTO compra) {

		InfoFornecedorDTO info = fornecedorClient.getInfoPorEstado(compra.getEndereco().getEstado());

		InfoPedidoDTO pedido = fornecedorClient.realizaPedido(compra.getItens());

		Compra novaCompra = new Compra();
		novaCompra.setPedidoId(pedido.getId());
		novaCompra.setTempoDePreparo(pedido.getTempoDePreparo());
		novaCompra.setEnderecoDestino(compra.getEndereco().toString());

		return novaCompra;

	}

}
