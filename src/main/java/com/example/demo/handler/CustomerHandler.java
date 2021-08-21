package com.example.demo.handler;

import com.example.demo.dto.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.util.Comparator.comparing;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@Slf4j
public class CustomerHandler {

    public Mono<ServerResponse> sort(ServerRequest request) {

        log.trace("Sorting customers...");

        Flux<CustomerDto> requestBody = request.bodyToFlux(CustomerDto.class);
        requestBody = requestBody.sort(comparing(CustomerDto::getDueTime));

        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(requestBody, CustomerDto.class);
    }
}