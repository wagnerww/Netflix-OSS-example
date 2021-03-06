package br.com.wagnerww.microservice.loja.service;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.wagnerww.microservice.loja.client.FornecedorClient;
import br.com.wagnerww.microservice.loja.client.TransportadorClient;
import br.com.wagnerww.microservice.loja.controller.dto.CompraDTO;
import br.com.wagnerww.microservice.loja.controller.dto.InfoFornecedorDTO;
import br.com.wagnerww.microservice.loja.dto.InfoEntregaDTO;
import br.com.wagnerww.microservice.loja.dto.InfoPedidoDTO;
import br.com.wagnerww.microservice.loja.dto.VoucherDTO;
import br.com.wagnerww.microservice.loja.model.Compra;
import br.com.wagnerww.microservice.loja.model.CompraState;
import br.com.wagnerww.microservice.loja.repository.CompraRepository;

@Service
public class CompraService {

	private static final Logger Log = LoggerFactory.getLogger(CompraService.class);

	@Autowired
	private FornecedorClient fornecedorClient;

	@Autowired
	private TransportadorClient transportadorClient;

	@Autowired
	private CompraRepository compraRepository;

	@HystrixCommand(fallbackMethod = "realizaCompraFallback", threadPoolKey = "realizaCompraThreadPool")
	public Compra realizaCompra(CompraDTO compra) {

		Compra novaCompra = new Compra();
		novaCompra.setState(CompraState.RECEBIDO);
		compraRepository.save(novaCompra);

		final String estado = compra.getEndereco().getEstado();

		Log.info("Buscando informações do fornecedor de {}", estado);
		InfoFornecedorDTO info = fornecedorClient.getInfoPorEstado(estado);

		Log.info("Realizando um pedido");
		InfoPedidoDTO pedido = fornecedorClient.realizaPedido(compra.getItens());

		novaCompra.setPedidoId(pedido.getId());
		novaCompra.setTempoDePreparo(pedido.getTempoDePreparo());
		novaCompra.setEnderecoDestino(compra.getEndereco().toString());
		novaCompra.setState(CompraState.PEDIDO_REALIZADO);
		compraRepository.save(novaCompra);
		compra.setCompraId(novaCompra.getId());

		InfoEntregaDTO entregaDto = new InfoEntregaDTO();

		entregaDto.setPedidoId(pedido.getId());
		entregaDto.setDataParaEntrega(LocalDate.now().plusDays(pedido.getTempoDePreparo()));
		entregaDto.setEnderecoOrigem(info.getEndereco());
		entregaDto.setEnderecoDestino(compra.getEndereco().toString());

		VoucherDTO voucher = transportadorClient.reservaEntrega(entregaDto);
		novaCompra.setDataParaEntrega(voucher.getPrevisaoParaEntrega());
		novaCompra.setVoucher(voucher.getNumero());
		novaCompra.setState(CompraState.RESERVA_ENTREGA_REALIZADA);
		compraRepository.save(novaCompra);

		return novaCompra;

	}

	public Compra realizaCompraFallback(CompraDTO compra) {
		
		if(compra.getCompraId() != null) {
			return compraRepository.findById(compra.getCompraId()).get();
		}
		
		Compra compraFallback = new Compra();
		compraFallback.setEnderecoDestino(compra.getEndereco().toString());

		return compraFallback;

	}

	@HystrixCommand(threadPoolKey = "getByIdThreadPool")
	public Compra getById(Long id) {
		return compraRepository.findById(id).orElse(new Compra());
	}

}
