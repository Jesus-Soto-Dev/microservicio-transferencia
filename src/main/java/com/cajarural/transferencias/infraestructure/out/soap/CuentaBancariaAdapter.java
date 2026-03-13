package com.cajarural.transferencias.infraestructure.out.soap;

import java.math.BigDecimal;

import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

import com.cajarural.transferencias.domain.port.out.CuentaBancariaPort;
import com.cajarural.transferencias.infrastructure.out.soap.generated.ConsultarCuentaResponse;

@Component
public class CuentaBancariaAdapter implements CuentaBancariaPort{
	
	private final ProducerTemplate camelProducer; //Envia mensajes a una ruta desde código JAVA

	private CuentaBancariaAdapter(ProducerTemplate camelProducer) {
		super();
		this.camelProducer = camelProducer;
	}

	@Override
	public BigDecimal consultarSaldoDisponible(String iban) {
		ConsultarCuentaResponse response = camelProducer.requestBody(
				"direct:consultar-cuenta", //Definido en Camel Route
				iban,
				ConsultarCuentaResponse.class //Clase que se espera devuelva la llamada
				);
		return response.getSaldoDisponible();
	}

	@Override
	public boolean cuentaEstaActiva(String iban) {
		ConsultarCuentaResponse response = camelProducer.requestBody(
				"direct:consultar-cuenta",
				iban,
				ConsultarCuentaResponse.class
				);
		//WSDF devuelve "ACTIVA,BLOQUEADA,CANCELADA"
		return "ACTIVA".equals(response.getEstadoCuenta());
	}

}
