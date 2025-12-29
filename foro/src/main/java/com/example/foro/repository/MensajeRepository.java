package com.example.foro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.foro.model.Mensaje;
import com.example.foro.model.Tema;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {

    
    // Obtener todas las respuestas directas de un mensaje
    List<Mensaje> findByPadre(Long id);

    // Para encontrar todos los mensajes que sean respuestas directas.
    List<Mensaje> findByTemaIdAndPadreIsNull(Long id);

    //Para listar todos los mensajes de un tema
    List<Mensaje> findByTema(Tema tema);

    List<Mensaje> findByMensajePadreId(Long idPadre);
}
