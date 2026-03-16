package com.cajarural.transferencias.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cajarural.transferencias.domain.model.EstadoTransferencia;
import com.cajarural.transferencias.domain.model.Transferencia;
import com.cajarural.transferencias.domain.port.out.CuentaBancariaPort;
import com.cajarural.transferencias.domain.port.out.TransferenciaRepositoryPort;

@ExtendWith(MockitoExtension.class)
public class EjecutarTransferenciaServiceTest {
	
	@Mock
	CuentaBancariaPort cuentaBancariaPort;
	
	@Mock
	TransferenciaRepositoryPort repositoryPort;
	
	@InjectMocks
	EjecutarTransferenciaService service;
	
	@Test
	void deberiaCompletarSiHayFondos() {
		//Se simula --> cuenta activa, salgo 1000€
		when(cuentaBancariaPort.cuentaEstaActiva("ES12 1234")).thenReturn(true);
		when(cuentaBancariaPort.consultarSaldoDisponible("ES12 1234")).thenReturn(new BigDecimal("1000"));
		
		Transferencia resultado = service.ejectuar("ES12 1234", "ES98 4572", new BigDecimal("500"), "EUR");
		
		assertEquals(EstadoTransferencia.COMPLETADA, resultado.getEstado());
		
		verify(repositoryPort, times(1)).guardar(any()); //Verificar que se intentó guardar en BBDD 1 vez
		
	}

	@Test
	void deberiaRechazarFondosInsuficientes () {
		when(cuentaBancariaPort.cuentaEstaActiva("ES12 1234")).thenReturn(true);
		when(cuentaBancariaPort.consultarSaldoDisponible("ES12 1234")).thenReturn(new BigDecimal("100"));
		
		Transferencia resultado = service.ejectuar("ES12 1234", "ES98 4272", new BigDecimal("500"), "EUR");
		
		assertEquals(EstadoTransferencia.RECHAZADA, resultado.getEstado());
		verify(repositoryPort, times(1)).guardar(any());
	}
	
	@Test
	void deberiaRechazarCuentaBloqueada() {
		when(cuentaBancariaPort.cuentaEstaActiva("ES12 1234")).thenReturn(false);
		
		Transferencia resultado = service.ejectuar("ES12 1234", "ES45 7574", new BigDecimal("500"), "EUR");
		assertEquals(EstadoTransferencia.RECHAZADA, resultado.getEstado());
		
		//No debería llamarse al método de consultar saldo
		verify(cuentaBancariaPort, never()).consultarSaldoDisponible(anyString());
	}
}
