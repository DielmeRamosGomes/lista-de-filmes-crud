package com.dielme.listadefilmes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record MovieRecordDto(@NotBlank @NotNull String category, @NotBlank @NotNull String name, @NotBlank @NotNull BigDecimal note, @NotBlank boolean assist) {

}
