package com.denarde.apipix.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReceivedPixDTO {

    @NotBlank(message = "{field.key.mandatory}")
    private String key;

    @NotNull(message = "{field.value.mandatory}")
    private Double value;
}
