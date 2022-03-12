package com.example.demo.models.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EspeceDto {

    @NotNull
    private String especeName;

    @NotNull
    private String especeFamily;

    @NotNull
    private String especeDescription;

}
