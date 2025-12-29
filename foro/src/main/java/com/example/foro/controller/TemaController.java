package com.example.foro.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.foro.dto.MensajeDTO;
import com.example.foro.dto.TemaDTO;
import com.example.foro.service.MensajeService;
import com.example.foro.service.TemaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/temas")
public class TemaController {

    private TemaService temaService;
    private MensajeService mensajeService;

    public TemaController(TemaService temaService, MensajeService mensajeService){

        this.temaService = temaService;
        this.mensajeService = mensajeService;

    }

    @GetMapping("/")
    public String Listar(Model model) {

        List<TemaDTO> dtos = temaService.listar();
        model.addAttribute("lista", dtos);
        return "temas/listar";
    }

    @GetMapping("/{id}")
    public String tema(@PathVariable Long id, Model model) {
        
        Optional<TemaDTO> opt = temaService.obtenerPorId(id);
        if(opt.isEmpty()) return "redirect:/temas";
        
        model.addAttribute("tema", opt.get());
        List<MensajeDTO> mensajes = mensajeService.listarRespuestasDirectas(id);
        model.addAttribute("Lista", mensajes);
        return "temas/detalles";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        TemaDTO temadto = new TemaDTO();
        model.addAttribute("tema", temadto);

        return "temas/nuevo";
    }

    @PostMapping("/nuevo")
    public String nuevo(@ModelAttribute TemaDTO dto) {

        temaService.insertar(dto);
        
        return "redirect: /temas";
    }
    
    
    
    

}
