package com.example.foro.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.foro.dto.TemaDTO;
import com.example.foro.model.Mensaje;
import com.example.foro.model.Tema;
import com.example.foro.repository.MensajeRepository;
import com.example.foro.repository.TemaRepository;

@Service
public class TemaService {

    private TemaRepository temaRepository;
    private MensajeRepository mensajeRepository;

    public TemaService(TemaRepository temaRepository,  MensajeRepository mensajeRepository) {

        this.temaRepository = temaRepository;
        this.mensajeRepository = mensajeRepository;

    }

    @Transactional
    public List<TemaDTO> listar() {

        List<Tema> temas = temaRepository.findAll();
        List<TemaDTO> dtos = new ArrayList<>();
        for (Tema t : temas) {
            dtos.add(entityToDTO(t));
        }

        return dtos;
    }

    @Transactional
    public Optional<TemaDTO> obtenerPorId(Long id) {

        Optional<Tema> opt = temaRepository.findById(id);

        if (opt.isEmpty()) return Optional.empty();

        return Optional.of(entityToDTO(opt.get()));

    }

    // Hace INSERT o UPDATE del tema (crea o actualiza)
    @Transactional
    public void insertar(TemaDTO tdto) {

        Tema tema = dtoToEntity(tdto);

        // En las altas añade la fecha en las actualizaciones la fecha no se debe cambiar
        if(tema.getFecha()==null) tema.setFecha(LocalDateTime.now());
        temaRepository.save(tema);

    }

    @Transactional
    public void eliminar(TemaDTO temaDto) {
        Tema tema = dtoToEntity(temaDto);

        // 1. Obtener todos los mensajes asociados al tema
        List<Mensaje> mensajes = mensajeRepository.findByTema(tema);

        // 2. Borrar todos los mensajes del tema
        // (no hace falta recursividad porque findByTema devuelve TODOS)
        mensajeRepository.deleteAll(mensajes);

        // 3. Borrar el tema
        temaRepository.delete(tema);

    }

    public Tema dtoToEntity(TemaDTO tdto) {

        return new Tema(

                tdto.getId(),
                tdto.getTitulo(),
                tdto.getDescripcion(),
                tdto.getFecha()

        );
    }

    public TemaDTO entityToDTO(Tema t) {

        return new TemaDTO(

                t.getId(),
                t.getTitulo(),
                t.getDescripcion(),
                t.getFecha()

        );
    }

}
