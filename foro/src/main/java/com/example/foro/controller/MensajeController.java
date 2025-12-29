package com.example.foro.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.foro.dto.MensajeDTO;
import com.example.foro.service.MensajeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/mensajes")
public class MensajeController {


    private MensajeService mensajeService;

    public MensajeController(MensajeService mensajeService) {

        this.mensajeService = mensajeService;

    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {

        Optional<MensajeDTO> opt = mensajeService.obtenerPorId(id);
        if(opt.isEmpty()) return "redirect: /temas";
        model.addAttribute("mensaje", opt.get());
        List<MensajeDTO> lista = mensajeService.listarRespuestasDirectas(id);
        model.addAttribute("lista", lista);

        return new String();
    }
    
    @GetMapping("/nuevo/{idTema")
    public String nuevo(Model model, 
        @PathVariable Long idTema,
        @RequestParam (required = false, name = "idPadre") Long idPadre
    ) {
        MensajeDTO dto = new MensajeDTO(); 
        dto.setIdTema(idTema);
        if (idPadre != null) dto.setIdPadre(idPadre);
        model.addAttribute("m", dto);
        return "/mensajes/nuevo";
    }

    @PostMapping("/nuevo/{idTema}")
    public String nuevo(@ModelAttribute MensajeDTO dto) {

        mensajeService.crearRespuesta(dto);
        return "redirect: /temas/" + dto.getIdTema();
    }
    
    

}
