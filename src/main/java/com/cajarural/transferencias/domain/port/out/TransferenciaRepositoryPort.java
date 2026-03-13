package com.cajarural.transferencias.domain.port.out;

import com.cajarural.transferencias.domain.model.Transferencia;
//Puerto de salida -> el dominio solicita a la capa de persistencia, sin saber de JPA ni H2
public interface TransferenciaRepositoryPort {
	void guardar(Transferencia transferencia);
}
