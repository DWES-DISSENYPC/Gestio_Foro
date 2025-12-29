package com.example.foro.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MensajeDTO {

  
    private Long id;

    private String contenido;

   
    private String autor;
    private LocalDateTime fecha;

    @NotNull
    private Long idTema;

    private Long idPadre;

}