package com.example.demo.controller;

import com.example.demo.models.entity.UsuarioEntity;
import com.example.demo.models.service.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @RequestMapping("")
    public String prueba(Map<String, Object> model) {
        UsuarioEntity usuario = new UsuarioEntity();
        model.put("usuario", usuario);

        List<UsuarioEntity> listaUsuarios = usuarioService.findAll();
        model.put("usuarios", listaUsuarios);

        return "usuario/menuUsuario";
    }

    @RequestMapping("/crearUsuario")
    public String crearUsuario(UsuarioEntity usuario) {
        usuarioService.save(usuario);
        return "redirect:/usuario";
    }

    @RequestMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") int id) {
        usuarioService.delete(id);
        return "redirect:/usuario";
    }

    @RequestMapping("/formEditar/{id}")
    public String eliminar(@PathVariable(value = "id") int id, Map<String, Object> model) {

        UsuarioEntity usuario = usuarioService.findOne(id);
        model.put("usuario", usuario);

        return "usuario/editarUsuario";
    }

    @RequestMapping(value = "/editarUsuario", method = RequestMethod.POST)
    public String eliminar(UsuarioEntity usuario) {
        usuarioService.save(usuario);
        return "redirect:/usuario";
    }
}
