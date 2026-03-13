package com.cajarural.transferencias.domain.port.out;

import java.math.BigDecimal;
//Puerto de salida -> Lo que necesita el dominio del banco, sin saber del CXF ni SOAP
public interface CuentaBancariaPort {
	BigDecimal consultarSaldoDisponible(String iban);
	boolean cuentaEstaActiva(String iban);
}
