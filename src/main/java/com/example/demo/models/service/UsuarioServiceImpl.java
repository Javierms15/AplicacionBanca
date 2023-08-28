package com.example.demo.models.service;

import com.example.demo.models.dao.IUsuarioDao;
import com.example.demo.models.entity.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioServiceImpl implements IUsuarioService{

    @Autowired
    private IUsuarioDao usuarioDao;

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioEntity> findAll() {
        return (List<UsuarioEntity>) usuarioDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioEntity findOne(int id) {
        return usuarioDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(UsuarioEntity usuario) {
        usuarioDao.save(usuario);
    }

    @Override
    @Transactional
    public void delete(int id) {
        usuarioDao.deleteById(id);
    }

    public UsuarioEntity existeUsuario(String nombre, String contrasena){
         return usuarioDao.findUsuarioEntityByNombreAndAndContrasena(nombre, contrasena);
    }
}
