package com.cajarural.transferencias.infraestructure.in.rest;

import java.math.BigDecimal;

public record TransferenciaRequest ( //DTO de entrada
	String ibanOrigen,
	String ibanDestino,
	BigDecimal cantidad,
	String moneda
) {}
