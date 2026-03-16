package com.cajarural.transferencias.infraestructure.out.persistence;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="transferencias")
public class TransferenciaJpaEntity {
	
	@Id
	@Column(name="id")
	private String id;
	
	@Column(name="iban_origen")
	private String ibanOrigen;
	
	@Column(name="iban_destino")
	private String ibanDestino;
	
	@Column(name="cantidad")
	private BigDecimal cantidad;
	
	@Column(name="moneda")
	private String moneda;
	
	@Column(name="estado")
	private String estado;
	
	public TransferenciaJpaEntity() {}

	public TransferenciaJpaEntity(String id, String ibanOrigen, String ibanDestino, BigDecimal cantidad, String moneda,
			String estado) {
		super();
		this.id = id;
		this.ibanOrigen = ibanOrigen;
		this.ibanDestino = ibanDestino;
		this.cantidad = cantidad;
		this.moneda = moneda;
		this.estado = estado;
	}

	public String getId() {
		return id;
	}

	public String getIbanOrigen() {
		return ibanOrigen;
	}

	public String getIbanDestino() {
		return ibanDestino;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public String getMoneda() {
		return moneda;
	}

	public String getEstado() {
		return estado;
	}
	
	

}
