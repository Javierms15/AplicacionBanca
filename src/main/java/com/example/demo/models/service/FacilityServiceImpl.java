package com.example.demo.models.service;

import com.example.demo.models.dao.DealCustom;
import com.example.demo.models.dao.FacilityCustom;
import com.example.demo.models.dao.IDealDao;
import com.example.demo.models.dao.IFacilityDao;
import com.example.demo.models.entity.DealEntity;
import com.example.demo.models.entity.FacilityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class FacilityServiceImpl implements  IFacilityService{

    @Autowired
    private IFacilityDao facilityDao;

    @Autowired
    private FacilityCustom facilityCustom;

    @Override
    @Transactional(readOnly = true)
    public List<FacilityEntity> findAll() {
        return facilityDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public FacilityEntity findOne(int id) {
        return facilityDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(FacilityEntity deal) {
        facilityDao.save(deal);
    }

    @Override
    @Transactional
    public void delete(int id) {
        facilityDao.deleteById(id);
    }


    @Override
    @Transactional(readOnly = true)
    public List<FacilityEntity> filter(String tipo, String estado, String cantidad, String fechaCreacion,
                                   String fechaEfectiva, String fechaFinalizacion, String deal, String idBanco) {
        if (tipo == "" && estado == "" && cantidad == "" && fechaCreacion == "" && fechaEfectiva == ""
                && fechaFinalizacion == "" && deal == "" && idBanco == "") {
            return findAll();
        }

        try {

            if (cantidad != "") {
                Double.parseDouble(cantidad);
            }

            if (deal != "") {
                Integer.parseInt(deal);
            }
        } catch (NumberFormatException e) {
            return findAll();
        }

        return facilityCustom.filtradoFacility(tipo, estado, cantidad, fechaCreacion, fechaEfectiva, fechaFinalizacion,
                deal, idBanco);
    }


}
