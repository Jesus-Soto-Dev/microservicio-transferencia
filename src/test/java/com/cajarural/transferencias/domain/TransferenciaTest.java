package com.cajarural.transferencias.domain;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.cajarural.transferencias.domain.model.EstadoTransferencia;
import com.cajarural.transferencias.domain.model.Importe;
import com.cajarural.transferencias.domain.model.Transferencia;

public class TransferenciaTest { //Test del dominio puro

	@Test
	void deberiaCrearseEnEstadoPendiente() {
		Transferencia t = Transferencia.crear("ES12 1234 5678", "ES90 1234 5678", new Importe(new BigDecimal("500"), "EUR"));
		assertEquals(EstadoTransferencia.PENDIENTE, t.getEstado());
		assertNotNull(t.getId());
	}
	
	@Test
	void deberiaCompletarseCorrectamente () {
		Transferencia t = Transferencia.crear("ES12 1234 5678", "ES90 1234 5678", new Importe(new BigDecimal("500"), "EUR"));
		t.completar();
		assertEquals(EstadoTransferencia.COMPLETADA, t.getEstado());
	}
	
	@Test
	void deberiaRechazarseCorrectamente() {
		Transferencia t = Transferencia.crear("ES12 1234 5678", "ES90 1234 5678", new Importe(new BigDecimal("500"), "EUR"));
		t.rechazar();
		assertEquals(EstadoTransferencia.RECHAZADA, t.getEstado());
	}
	
	@Test
	void noDeberiaPermitirMismoIban() {
		assertThrows(IllegalArgumentException.class, () ->
			Transferencia.crear("ES12 1234", "ES12 1234", new Importe(new BigDecimal("500"), "EUR"))
		);
	}
	
	@Test 
	void noDeberiaPermitirImporteNegativo() {
		assertThrows(IllegalArgumentException.class, () ->
			new Importe(new BigDecimal("-100"), "EUR")
		);
	}
	
	@Test
	void noDeberiaPoderCompletarTransaccionRechazada() {
		Transferencia t = Transferencia.crear("ES12 1234", "ES31 4567", new Importe(new BigDecimal("500"),"EUR"));
		t.rechazar();
		assertThrows(IllegalStateException.class, () -> t.completar());
	}
	
}
