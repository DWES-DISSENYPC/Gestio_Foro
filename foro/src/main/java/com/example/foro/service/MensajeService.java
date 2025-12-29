package com.example.foro.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.foro.dto.MensajeDTO;
import com.example.foro.model.Mensaje;
import com.example.foro.model.Tema;
import com.example.foro.repository.MensajeRepository;
import com.example.foro.repository.TemaRepository;
import jakarta.transaction.Transactional;

@Service
public class MensajeService {

    private final TemaRepository temaRepository;

    private MensajeRepository mensajeRepository;

    public MensajeService(MensajeRepository mensajeRepository, TemaRepository temaRepository) {

        this.mensajeRepository = mensajeRepository;
        this.temaRepository = temaRepository;

    }

    @Transactional
    public List<MensajeDTO> listarRespuestasDirectas(Long id) {

        List<Mensaje> mensajes = mensajeRepository.findByTemaIdAndPadreIsNull(id);
        List<MensajeDTO> mdto = new ArrayList<>();
        for (Mensaje m : mensajes)
            mdto.add(entityToDto(m));
        return mdto;

    }

    public List<MensajeDTO> listarPorMensajePadre(Long idPadre){
        List<Mensaje> mensajes = mensajeRepository.findByMensajePadreId(idPadre);
        List<MensajeDTO> listaDTO = new ArrayList<>();
        for(Mensaje m : mensajes){
            listaDTO.add(entityToDto(m));
        }
        return listaDTO;
    }

    @Transactional
    public void crearRespuesta(MensajeDTO mensa){

        Mensaje r = DtoToEntity(mensa);
        if(mensa.getFecha()==null) r.setFecha(LocalDateTime.now());

        Tema t = temaRepository.findById(mensa.getIdTema()).get();
        r.setTema(t);
        Mensaje mp = null;
        if (mensa.getIdPadre() != null) mp = mensajeRepository.findById(mensa.getIdPadre()).get();
        r.setPadre(mp);
        mensajeRepository.save(r);

    }

    @Transactional
    public Optional<MensajeDTO> obtenerPorId(Long id){
        Optional<Mensaje> opt = mensajeRepository.findById(id);
        if(opt.isEmpty()) return Optional.empty();
        return Optional.of(entityToDto(opt.get()));
    }

    
    @Transactional
    public void eliminarMensajeConHijos(Mensaje mensaje) {
        // 1. Buscar respuestas directas
        List<Mensaje> respuestas = mensajeRepository.findByPadre(mensaje.getId());

        // 2. Por cada respuesta, borrar también sus hijos (recursivo)
        for (Mensaje respuesta : respuestas) {

            eliminarMensajeConHijos(respuesta);

        }

        // 3. Cuando ya no tiene hijos, borrar el mensaje actual
        mensajeRepository.delete(mensaje);
    }

    private MensajeDTO entityToDto(Mensaje m) {

        return new MensajeDTO(

                m.getId(),
                m.getContenido(),
                m.getAutor(),
                m.getFecha(),
                m.getTema().getId(),
                m.getPadre().getId()

        );

    }

    private Mensaje DtoToEntity(MensajeDTO r) {

        Tema t = null;
        if(r.getIdTema() != null) t = temaRepository.findById(r.getIdTema()).get();

        Mensaje m = null;
        if (r.getIdPadre() != null) m = mensajeRepository.findById(r.getIdPadre()).get();
       
        return new Mensaje (

            r.getId(),
            r.getContenido(),
            r.getAutor(),
            r.getFecha(),
            t,
            m
        );
    }


}
