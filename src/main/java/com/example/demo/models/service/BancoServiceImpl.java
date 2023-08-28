package com.example.demo.models.service;

import com.example.demo.models.dao.IBancoDao;
import com.example.demo.models.entity.BancoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BancoServiceImpl implements IBancoService{

    @Autowired
    private IBancoDao bancoDao;

    @Override
    public List<BancoEntity> findAll() {
        return (List<BancoEntity>) bancoDao.findAll();
    }

    @Override
    public BancoEntity findOne(int id) {
        return null;
    }

    @Override
    public void save(BancoEntity banco) {

    }

    @Override
    public void delete(int id) {

    }
}
