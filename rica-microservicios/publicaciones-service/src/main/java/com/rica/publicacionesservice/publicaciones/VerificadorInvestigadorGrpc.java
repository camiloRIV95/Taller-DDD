package com.rica.publicacionesservice.publicaciones;
import com.rica.publicacionesservice.grpc.InvestigadorGrpcGrpc.InvestigadorGrpcBlockingStub;
import com.rica.publicacionesservice.grpc.InvestigadorRequest;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.grpc.StatusRuntimeException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Component
public class VerificadorInvestigadorGrpc implements VerificadorInvestigador {
  private static final Logger log = LoggerFactory.getLogger(VerificadorInvestigadorGrpc.class);
  private final InvestigadorGrpcBlockingStub stub;
  private final CircuitBreaker breaker;
  public VerificadorInvestigadorGrpc(InvestigadorGrpcBlockingStub stub) {
    this.stub = stub;
    this.breaker = CircuitBreaker.of("investigadores-service", CircuitBreakerConfig.custom().slidingWindowSize(10)
        .minimumNumberOfCalls(5).failureRateThreshold(50).waitDurationInOpenState(Duration.ofSeconds(10)).build());
    breaker.getEventPublisher().onStateTransition(e -> log.warn("Circuit Breaker investigadores-service: {}", e.getStateTransition()));
  }
  @Override public boolean existe(String correo) {
    try {
      return breaker.executeSupplier(() -> stub.withDeadlineAfter(5, TimeUnit.SECONDS)
          .existeInvestigador(InvestigadorRequest.newBuilder().setCorreoInstitucional(correo).build()).getExiste());
    } catch (CallNotPermittedException e) {
      throw new ServicioNoDisponibleException("investigadores-service no está disponible (circuito abierto)", e);
    } catch (StatusRuntimeException e) {
      throw new ServicioNoDisponibleException("No se pudo contactar a investigadores-service: " + e.getStatus().getCode(), e);
    }
  }
}
