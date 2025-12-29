package com.example.foro.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class TemaDTO {


    
    private Long id;

    @NotNull
    private String titulo;

    @NotNull
    private String descripcion;
    
    private LocalDateTime fecha;
}


