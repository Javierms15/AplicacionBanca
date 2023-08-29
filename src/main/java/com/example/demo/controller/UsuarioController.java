package com.example.demo.controller;

import com.example.demo.models.dao.IBancoDao;
import com.example.demo.models.entity.BancoEntity;
import com.example.demo.models.entity.UsuarioEntity;
import com.example.demo.models.service.IBancoService;
import com.example.demo.models.service.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private IBancoService bancoService;

    @RequestMapping("")
    public String inicioUsuario(Map<String, Object> model) {
        UsuarioEntity usuario = new UsuarioEntity();
        model.put("usuario", usuario);

        List<UsuarioEntity> listaUsuarios = usuarioService.findAll();
        model.put("usuarios", listaUsuarios);

        List<BancoEntity> listaBancos = bancoService.findAll();
        model.put("bancos", listaBancos);

        return "usuario/menuUsuario";
    }

    @RequestMapping("/crearUsuario")
    public String crearUsuario(UsuarioEntity usuario, RedirectAttributes flash) {
        usuarioService.save(usuario);
        flash.addFlashAttribute("success", "Usuario " + usuario.getNombre() + " creado correctamente");
        return "redirect:/usuario";
    }

    @RequestMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") int id, RedirectAttributes flash) {
        usuarioService.delete(id);
        flash.addFlashAttribute("success", "Usuario eliminado correctamente");
        return "redirect:/usuario";
    }

    @RequestMapping("/formEditar/{id}")
    public String eliminar(@PathVariable(value = "id") int id, Map<String, Object> model) {

        UsuarioEntity usuario = usuarioService.findOne(id);
        model.put("usuario", usuario);

        List<BancoEntity> listaBancos = bancoService.findAll();
        model.put("bancos", listaBancos);

        return "usuario/editarUsuario";
    }

    @RequestMapping(value = "/editarUsuario", method = RequestMethod.POST)
    public String eliminar(UsuarioEntity usuario, RedirectAttributes flash) {
        usuarioService.save(usuario);
        flash.addFlashAttribute("success", "Usuario " + usuario.getNombre() + " editado correctamente");
        return "redirect:/usuario";
    }
}
