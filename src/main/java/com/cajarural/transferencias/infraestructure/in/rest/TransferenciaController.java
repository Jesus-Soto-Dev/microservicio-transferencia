package com.cajarural.transferencias.infraestructure.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cajarural.transferencias.domain.model.Transferencia;
import com.cajarural.transferencias.domain.port.in.EjecutarTransferenciaUseCase;

@RestController
@RequestMapping("/transferencias")
public class TransferenciaController {

	private final EjecutarTransferenciaUseCase useCase;

	public TransferenciaController(EjecutarTransferenciaUseCase useCase) {
		super();
		this.useCase = useCase;
	}
	
	@PostMapping
	public ResponseEntity<TransferenciaReponse> ejecutar (@RequestBody TransferenciaRequest request) {
		Transferencia transferencia = useCase.ejectuar(request.ibanOrigen(), request.ibanDestino(), request.cantidad(), request.moneda());
		
		TransferenciaReponse response = new TransferenciaReponse(
				transferencia.getId().toString(),
				transferencia.getIbanOrigen(),
				transferencia.getIbanDestino(),
				transferencia.getImporte().getCantidad(),
				transferencia.getImporte().getMoneda(),
				transferencia.getEstado().name()
				);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleDomainError(IllegalArgumentException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
}
