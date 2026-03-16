package com.cajarural.transferencias.infraestructure.in.rest;

import java.math.BigDecimal;

public record TransferenciaReponse(
		String id,
		String ibanOrigen,
		String ibanDestino,
		BigDecimal cantidad,
		String moneda,
		String estado
		) {}
