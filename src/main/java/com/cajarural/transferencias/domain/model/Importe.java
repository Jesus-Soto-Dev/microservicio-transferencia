package com.cajarural.transferencias.domain.model;

import java.math.BigDecimal;

//Value Object --> representa cantidad del dinero
//Es inmutable, validandose en el construtor
public final class Importe {
	
	private final BigDecimal cantidad;
	private final String moneda;
	
	public Importe(BigDecimal cantidad, String moneda) {
		if (cantidad == null || cantidad.compareTo(BigDecimal.ZERO) <= 0) 
			throw new IllegalArgumentException("El importe debe ser positivo");
		if (moneda == null || moneda.isBlank())
			throw new IllegalArgumentException("La moneda es obligatoria");
		this.cantidad = cantidad;
		this.moneda = moneda;
	}
	
	public boolean esMayorQue(BigDecimal otraCantidad) {
		return this.cantidad.compareTo(otraCantidad) > 0;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public String getMoneda() {
		return moneda;
	}

}
