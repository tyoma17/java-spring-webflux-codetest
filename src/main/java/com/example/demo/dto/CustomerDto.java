package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class CustomerDto {

    private Long id;
    private String name;

    @JsonProperty("duetime")
    private ZonedDateTime dueTime;

    @JsonProperty("jointime")
    private ZonedDateTime joinTime;
}