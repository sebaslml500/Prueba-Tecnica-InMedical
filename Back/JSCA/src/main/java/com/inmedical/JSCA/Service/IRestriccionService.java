package com.inmedical.JSCA.Service;

import com.inmedical.JSCA.Entites.Restriccion;

import java.util.List;

public interface IRestriccionService {
    public List<Restriccion> listRestriccion();

    public void saveRestriccion(Restriccion restriccion);

    public void deleteRestriccion(Long restriccionId);
}
