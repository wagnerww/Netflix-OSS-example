package br.com.wagnerww.microservice.loja.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wagnerww.microservice.loja.client.FornecedorClient;
import br.com.wagnerww.microservice.loja.controller.dto.CompraDTO;
import br.com.wagnerww.microservice.loja.controller.dto.InfoFornecedorDTO;
import br.com.wagnerww.microservice.loja.dto.InfoPedidoDTO;
import br.com.wagnerww.microservice.loja.model.Compra;

@Service
public class CompraService {

	private static final Logger Log = LoggerFactory.getLogger(CompraService.class);

	@Autowired
	private FornecedorClient fornecedorClient;

	public Compra realizaCompra(CompraDTO compra) {
		
		final String estado = compra.getEndereco().getEstado();

		Log.info("Buscando informações do fornecedor de {}", estado);
		InfoFornecedorDTO info = fornecedorClient.getInfoPorEstado(estado);

		Log.info("Realizando um pedido");
		InfoPedidoDTO pedido = fornecedorClient.realizaPedido(compra.getItens());

		Compra novaCompra = new Compra();
		novaCompra.setPedidoId(pedido.getId());
		novaCompra.setTempoDePreparo(pedido.getTempoDePreparo());
		novaCompra.setEnderecoDestino(compra.getEndereco().toString());

		return novaCompra;

	}

}
