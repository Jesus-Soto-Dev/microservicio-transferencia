package com.cajarural.transferencias.domain.model;

import java.util.UUID;

//Entidad raiz -del Aggregate?-
public class Transferencia { //Contiene toda la lógica de negocio
	
	private final UUID id;
	private final String ibanOrigen;
	private final String ibanDestino;
	private final Importe importe;
	private EstadoTransferencia estado;
	
	//Constuctor privado --> acceso mediante factory methods
	private Transferencia(UUID id, String ibanOrigen, String ibanDestino, Importe importe, EstadoTransferencia estado) {
		super();
		this.id = id;
		this.ibanOrigen = ibanOrigen;
		this.ibanDestino = ibanDestino;
		this.importe = importe;
		this.estado = estado;
	}
	
	// Factory method -> unica forma de crear una transferencia, siempre es pendiente
	public static Transferencia crear(String ibanOrigen, String ibanDestino, Importe importe) {
		if (ibanOrigen.equals(ibanDestino)) throw new IllegalArgumentException (
				"La cuenta de orgin y destino no puede ser la misma");
		return new Transferencia (UUID.randomUUID(), ibanOrigen, ibanDestino, importe, EstadoTransferencia.PENDIENTE);
	}
	
	// Completar solo si esta pendiente
	public void completar() {
		if (this.estado != EstadoTransferencia.PENDIENTE) throw new IllegalArgumentException (
				"Solo se pueden completar transferencias pendientes");
		this.estado = EstadoTransferencia.COMPLETADA;
	}
	
	// Rechazar solo si está pendiente
	public void rechazar() {
		if (this.estado != EstadoTransferencia.PENDIENTE) throw new IllegalArgumentException (
				"Solo se pueden rechazar transferencias pendientes");
		this.estado = EstadoTransferencia.RECHAZADA;
	}

	public EstadoTransferencia getEstado() {
		return estado;
	}

	public void setEstado(EstadoTransferencia estado) {
		this.estado = estado;
	}

	public UUID getId() {
		return id;
	}

	public String getIbanOrigen() {
		return ibanOrigen;
	}

	public String getIbanDestino() {
		return ibanDestino;
	}

	public Importe getImporte() {
		return importe;
	}

}
