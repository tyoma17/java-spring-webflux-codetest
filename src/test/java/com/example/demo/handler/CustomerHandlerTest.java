package com.example.demo.handler;

import com.example.demo.config.CustomerRouter;
import com.example.demo.dto.CustomerDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.ResourceUtils;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CustomerHandler.class, CustomerRouter.class})
@WebFluxTest
class CustomerHandlerTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private WebTestClient client;

    @BeforeEach
    void setup() {
        client = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    void sort() throws IOException {
        File json = ResourceUtils.getFile("classpath:10_customers.json");
        CustomerDto[] customers = objectMapper.readValue(json, CustomerDto[].class);

        client
                .post()
                .uri("/customers/sort")
                .accept(APPLICATION_JSON)
                .body(Flux.just(customers), CustomerDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerDto[].class).value(array -> {

                    final int length = array.length;

                    for (int i = 0; i < length - 1; i++) {
                        assertThat(array[i].getDueTime()).isBeforeOrEqualTo(array[i + 1].getDueTime());
                    }
                }
        );
    }
}