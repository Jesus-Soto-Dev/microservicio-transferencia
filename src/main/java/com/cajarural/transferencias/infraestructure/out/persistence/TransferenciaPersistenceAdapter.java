package com.cajarural.transferencias.infraestructure.out.persistence;

import org.springframework.stereotype.Component;

import com.cajarural.transferencias.domain.model.Transferencia;
import com.cajarural.transferencias.domain.port.out.TransferenciaRepositoryPort;

@Component
public class TransferenciaPersistenceAdapter implements TransferenciaRepositoryPort{
	
	private final TransferenciaJpaRepository jpaRepository;
	
	public TransferenciaPersistenceAdapter(TransferenciaJpaRepository jpaRepository) {
		super();
		this.jpaRepository = jpaRepository;
	}



	@Override
	public void guardar(Transferencia transferencia) {
		//Transformación de objeto de dominio a entity para guardar
		TransferenciaJpaEntity entity = new TransferenciaJpaEntity(
				transferencia.getId().toString(),
				transferencia.getIbanOrigen(),
				transferencia.getIbanDestino(),
				transferencia.getImporte().getCantidad(),
				transferencia.getImporte().getMoneda(),
				transferencia.getEstado().name()
				);
		jpaRepository.save(entity);
	}

}
