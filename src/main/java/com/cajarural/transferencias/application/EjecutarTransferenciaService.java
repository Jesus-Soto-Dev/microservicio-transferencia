package com.cajarural.transferencias.application;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.cajarural.transferencias.domain.model.Importe;
import com.cajarural.transferencias.domain.model.Transferencia;
import com.cajarural.transferencias.domain.port.in.EjecutarTransferenciaUseCase;
import com.cajarural.transferencias.domain.port.out.CuentaBancariaPort;
import com.cajarural.transferencias.domain.port.out.TransferenciaRepositoryPort;

@Service
public class EjecutarTransferenciaService implements EjecutarTransferenciaUseCase{
	
	private final CuentaBancariaPort cuentaBancariaPort;
	private final TransferenciaRepositoryPort repositoryPort;
	
	public EjecutarTransferenciaService(CuentaBancariaPort cuentaBancariaPort,
			TransferenciaRepositoryPort repositoryPort) {
		super();
		this.cuentaBancariaPort = cuentaBancariaPort;
		this.repositoryPort = repositoryPort;
	}



	@Override
	public Transferencia ejectuar(String ibanOrigen, String ibanDestindo, BigDecimal cantidad, String moneda) {
		// 1. Crea la transferencia, validadas por el dominio
		Importe importe = new Importe(cantidad,moneda);
		Transferencia transferencia = Transferencia.crear(ibanOrigen, ibanDestindo, importe);
		
		// 2. Verifica la actividad de la cuenta
		if(!cuentaBancariaPort.cuentaEstaActiva(ibanOrigen)) {
			transferencia.rechazar();
			repositoryPort.guardar(transferencia); //Para auditar
			return transferencia;
		}
		
		// 3. Verificar fondos
		BigDecimal saldoDisponible = cuentaBancariaPort.consultarSaldoDisponible(ibanOrigen);
		if(importe.esMayorQue(saldoDisponible)) {
			transferencia.rechazar();
			repositoryPort.guardar(transferencia); //Para auditar
			return transferencia;
		}
		
		//4. Completar la transferencia
		transferencia.completar();
		repositoryPort.guardar(transferencia);
		return transferencia;
	}

}
