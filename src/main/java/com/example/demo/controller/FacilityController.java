package com.example.demo.controller;


import com.example.demo.models.dao.IDealDao;
import com.example.demo.models.dao.IFacilityDao;
import com.example.demo.models.entity.DealEntity;
import com.example.demo.models.entity.FacilityEntity;
import com.example.demo.models.service.IDealService;
import com.example.demo.models.service.IFacilityService;
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
    private IFacilityService facilityService;

    @Autowired
    private IDealService dealService;


    @RequestMapping("/nuevaFacility")
    public String mostrarPantallaNuevaFacility(Model model){
        FacilityEntity facility=new FacilityEntity();
        List<DealEntity> deals=dealService.findAll();
        model.addAttribute("deals",deals);
        model.addAttribute("facility",facility);
        return"facility/facility";
    }

    @PostMapping("/crearFacility")
    public String crearFacility(Model model, FacilityEntity facility){
        int aux1= facility.getDeal();
        Double aux2=facility.getCantidad();

        Double dinero = facilityDao.obtenerSumaFacilityDeal(aux1);
        if(dinero!=null) {
            if (dinero + aux2 <= dealService.findOne(facility.getDeal()).getCantidadPrestamo()) {
                facilityDao.save(facility);
            }
        }else{
            if (aux2 <= dealService.findOne(facility.getDeal()).getCantidadPrestamo()) {
                facilityDao.save(facility);
            }
        }
        // AÑADIR ALARMA DE ERROR
        return "redirect:/listarFacility";
    }

    @RequestMapping("/listarFacility")
    public String mostrarFacility(Model model){
        List<FacilityEntity> facilitys= (List<FacilityEntity>) facilityDao.findAll();
        model.addAttribute("facilitys",facilitys);
        FacilityFilter filter = new FacilityFilter();
        model.addAttribute("filter", filter);
        List<DealEntity> deals=dealService.findAll();
        model.addAttribute("deals",deals);
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

        List<FacilityEntity> deals=facilityService.findAll();
        Model.addAttribute("deals",deals);
        return "facility/facility_edit";
    }

    @RequestMapping(value = "/editarFacilitySave", method = RequestMethod.POST)
    public String editarFacilitySave(FacilityEntity facility) {
        facilityDao.save(facility);
        return "redirect:/listarFacility";
    }

    @RequestMapping(value = "/filtrarFacility")
    public String filtrarFacility(FacilityFilter filter, Model model) {
        List<FacilityEntity> facilitys = facilityService.filter(filter.tipo, filter.estado, filter.cantidad, filter.fechaCreacion,
                filter.fechaEfectiva, filter.fechaFinalizacion, filter.deal);
        model.addAttribute("facilitys", facilitys);
        model.addAttribute("filter", filter);
        List<DealEntity> deals=dealService.findAll();
        model.addAttribute("deals",deals);

        return "facility/facility_all";
    }

    class FacilityFilter{

        private String tipo;
        private String estado;
        private String cantidad;

        private String fechaCreacion;
        private String fechaEfectiva;
        private String fechaFinalizacion;
        private String deal;



        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }

        public String getCantidad() {
            return cantidad;
        }

        public void setCantidad(String cantidad) {
            this.cantidad = cantidad;
        }

        public String getFechaCreacion() {
            return fechaCreacion;
        }

        public void setFechaCreacion(String fechaCreacion) {
            this.fechaCreacion = fechaCreacion;
        }

        public String getFechaEfectiva() {
            return fechaEfectiva;
        }

        public void setFechaEfectiva(String fechaEfectiva) {
            this.fechaEfectiva = fechaEfectiva;
        }

        public String getFechaFinalizacion() {
            return fechaFinalizacion;
        }

        public void setFechaFinalizacion(String fechaFinalizacion) {
            this.fechaFinalizacion = fechaFinalizacion;
        }

        public String getDeal() {
            return deal;
        }

        public void setDeal(String deal) {
            this.deal = deal;
        }
    }





}
