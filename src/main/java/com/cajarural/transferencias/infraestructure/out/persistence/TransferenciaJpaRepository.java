package com.cajarural.transferencias.infraestructure.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferenciaJpaRepository extends JpaRepository<TransferenciaJpaEntity,String>{
	
	//Por el nombre genera el --> Select * From transferencias WHERE estado = ?
	List<TransferenciaJpaEntity> findByEstado(String estado);

}
