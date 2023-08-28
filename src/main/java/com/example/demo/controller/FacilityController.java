package com.example.demo.controller;


import com.example.demo.models.dao.IDealDao;
import com.example.demo.models.dao.IFacilityDao;
import com.example.demo.models.entity.DealEntity;
import com.example.demo.models.entity.FacilityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class FacilityController {

    @Autowired
    private IFacilityDao facilityDao;

    @Autowired
    private IDealDao dealDao;


    @RequestMapping("/nuevaFacility")
    public String mostrarPantallaNuevaFacility(Model model){
        FacilityEntity facility=new FacilityEntity();
        List<DealEntity> deals=dealDao.findAll();
        model.addAttribute("deals",deals);
        model.addAttribute("facility",facility);
        return"facility/facility";
    }

    @PostMapping("/crearFacility")
    public String crearFacility(Model model, FacilityEntity facility){
        facilityDao.save(facility);
        return "redirect:/listarFacility";
    }

    @RequestMapping("/listarFacility")
    public String mostrarFacility(Model model){
        List<FacilityEntity> facilitys= (List<FacilityEntity>) facilityDao.findAll();
        model.addAttribute("facilitys",facilitys);
        return "facility/facility_all";
    }

    @RequestMapping("eliminarFacility/{id}")
    public String eliminar(@PathVariable(value = "id") int id) {
        FacilityEntity facility=facilityDao.findById(id).orElse(null);
        facilityDao.delete(facility);
        return "redirect:/listarFacility";
    }

    @RequestMapping("editarFacility/{id}")
    public String editarFacility(@PathVariable(value = "id") int id, Map<String, Object> model,Model Model) {

        FacilityEntity facility = facilityDao.findById(id).orElse(null);
        model.put("facility", facility);

        List<DealEntity> deals=dealDao.findAll();
        Model.addAttribute("deals",deals);
        return "facility/facility_edit";
    }

    @RequestMapping(value = "/editarFacilitySave", method = RequestMethod.POST)
    public String editarFacilitySave(FacilityEntity facility) {
        facilityDao.save(facility);
        return "redirect:/listarFacility";
    }

    @RequestMapping(value = "/filtrarFacility")
    public String filtrarFacility(Model model, @RequestParam(value = "valor") String valor, @RequestParam(value="filtro") String filtro) {
       /* List<ClienteEntity> clientes=new ArrayList<>();
        if(filtro.equals("nombre")) {
            clientes= (List<ClienteEntity>) clienteDao.findByNombre(valor);
        }else if(filtro.equals("direccion")){
            clientes= (List<ClienteEntity>) clienteDao.findByDireccion(valor);
        }else if(filtro.equals("dinero")){
            clientes= (List<ClienteEntity>) clienteDao.findByCapital(Double.parseDouble(valor));
        }else if(filtro.equals("email")){
            clientes= (List<ClienteEntity>) clienteDao.findByEmail(valor);
        }else if(filtro.equals("banco")){
            BancoEntity banco=bancoDao.findByNombre(valor);
            clientes= (List<ClienteEntity>) clienteDao.findByBanco(banco.getIdBanco());
        }else{
            clientes= (List<ClienteEntity>) clienteDao.findAll();
        }
        model.addAttribute("clientes",clientes);
        */

        return "facility/listaFacility";
    }





}
