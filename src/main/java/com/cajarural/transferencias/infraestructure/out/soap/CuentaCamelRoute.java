package com.cajarural.transferencias.infraestructure.out.soap;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

import com.cajarural.transferencias.infrastructure.out.soap.generated.ConsultarCuentaRequest;
import com.cajarural.transferencias.infrastructure.out.soap.generated.ConsultarCuentaResponse;

//Orquestador entre el adaptador y CXF
public class CuentaCamelRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		errorHandler( 
				//Si falla luego de tres intentos manda a un log para revision manual
				deadLetterChannel("direct:error-banco") //Patrón de integración empresarial
					.maximumRedeliveries(3)
					.redeliveryDelay(2000)
					.logExhausted(true) //loguea cuando se agotan los reintentos
		);
		
		//Entrada para -> camelProducer.requestBody("direct:consultar-cuenta", iban)
		from("direct:consultar-cuenta")
			.routeId("ruta-consultar-cuenta-banco") //Nombre para logs y monitoring
			.log("Consultando cuenta en banco: ${body}") //body, en este contexto, se refiere al iban
			//.process() -> permite ejecutar código Java dentro de la ruta
			.process(exchange -> {//Transforma String Iban a un objeto ConsultarCuentaRequest que CXF entiede
				String iban = exchange.getIn().getBody(String.class);
				ConsultarCuentaRequest request = new ConsultarCuentaRequest(); //Representa la peticion SOAP que el banco espera
				request.setIban(iban);
				exchange.getIn().setBody(request); //Se reemplaza el body -> de String al objeto
			})
			/*CXF: Serializa el objeto Java a XML SOAP, envia la peticion HTTP, recibe la respusta XML SOAp, deserializa y transforma a Java ConsultarCuentaResponse*/
			.to("cxf:{{banco.soap.url}}"
					+ "?serviceClass=com.cajarural.transferencias" //Interfaz generada desde el WSDL
					+ ".infraestructure.out.soap.generated.CuentaServicePortType"
					+ "&operationName=consultarCuenta" //Operacion de WSDL
					+ "&dataFrmat=POJO") //objeto Java, pero dentro de un List
			//CXF devuelve los objetos JAVA dentro de un LIST, entonces se debe extraer. 
			.process(exchange -> {
				List<?> respuesta = exchange.getIn().getBody(List.class);
				ConsultarCuentaResponse response = (ConsultarCuentaResponse) respuesta.get(0);
				exchange.getIn().setBody(response);
			})
			.log("Respuesta del banco recibida para: ${body}");
		
		from("direct:error-banco")
			.routeId("ruta-error-banco")
			.log(LoggingLevel.ERROR, "Error al conectar con banco: ${exception.message}")
			.process(exchange -> {
				Exception ex = exchange.getProperty(
						Exchange.EXCEPTION_CAUGHT, Exception.class
				);
				throw new RuntimeException(
						"Sistema bancario no disponible" + ex.getMessage()
						);
			});
	}
}
