package com.example.demo.controller;

import com.example.demo.models.entity.ClienteEntity;
import com.example.demo.models.entity.UsuarioEntity;
import com.example.demo.models.service.IUsuarioService;
import com.example.demo.models.service.UsuarioServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InicioSesionController {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @RequestMapping("/login")
    public String mostrarInicioSesion(){
        return"usuario/inicioSesion";
    }

    @RequestMapping("/iniciarSesion")
    public String iniciarSesion(UsuarioEntity usuario, HttpSession session){

        UsuarioEntity usuarioRegistrado = usuarioService.existeUsuario(usuario.getNombre(), usuario.getContrasena());

        if(usuarioRegistrado == null){
            return "redirect:/login";
        }else {
            session.setAttribute("usuario", usuarioRegistrado);
            return "redirect:/";
        }
    }

    @RequestMapping("/cerrarSesion")
    public String cerrarSesion(HttpSession session){

        session.setAttribute("usuario", null);

        return "redirect:/login";

    }

}
