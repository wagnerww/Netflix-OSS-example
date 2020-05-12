package br.com.wagnerww.microservice.loja.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.wagnerww.microservice.loja.dto.InfoEntregaDTO;
import br.com.wagnerww.microservice.loja.dto.VoucherDTO;

@FeignClient("transportador")
public interface TransportadorClient {

	@RequestMapping(path = "/entrega", method = RequestMethod.POST)
	public VoucherDTO reservaEntrega(@RequestBody InfoEntregaDTO infoEntregaDTO);

}
