package com.cajarural.transferencias.domain.port.in;

import java.math.BigDecimal;

import com.cajarural.transferencias.domain.model.Transferencia;

//Puerto de entrada -> define qué se puede hacer con el dominio -desde el exterior-
public interface EjecutarTransferenciaUseCase {
	Transferencia ejectuar(String ibanOrigen, String ibanDestindo, BigDecimal cantidad, String moneda); 
}
