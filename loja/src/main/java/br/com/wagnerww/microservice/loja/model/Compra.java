package br.com.wagnerww.microservice.loja.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Compra {

	@Id
	private Long pedidoId;
	private Integer tempoDePreparo;
	private String enderecoDestino;
	private Long voucher;
	private LocalDate dataParaEntrega;

	public LocalDate getDataParaEntrega() {
		return dataParaEntrega;
	}

	public void setDataParaEntrega(LocalDate dataParaEntrega) {
		this.dataParaEntrega = dataParaEntrega;
	}

	public Long getVoucher() {
		return voucher;
	}

	public void setVoucher(Long voucher) {
		this.voucher = voucher;
	}

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public String getEnderecoDestino() {
		return enderecoDestino;
	}

	public void setEnderecoDestino(String enderecoDestino) {
		this.enderecoDestino = enderecoDestino;
	}

	public Integer getTempoDePreparo() {
		return tempoDePreparo;
	}

	public void setTempoDePreparo(Integer tempoDePreparo) {
		this.tempoDePreparo = tempoDePreparo;
	}

}
