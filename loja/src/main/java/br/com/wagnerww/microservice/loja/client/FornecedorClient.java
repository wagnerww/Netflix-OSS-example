package br.com.wagnerww.microservice.loja.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.wagnerww.microservice.loja.controller.dto.InfoFornecedorDTO;
import br.com.wagnerww.microservice.loja.controller.dto.ItemCompraDTO;
import br.com.wagnerww.microservice.loja.dto.InfoPedidoDTO;

@FeignClient("fornecedor")
public interface FornecedorClient {

	@RequestMapping("/info/{estado}")
	InfoFornecedorDTO getInfoPorEstado(@PathVariable String estado);

	@RequestMapping(value = "/pedido", method = RequestMethod.POST)
	InfoPedidoDTO realizaPedido(List<ItemCompraDTO> itens);

}
