package com.inmedical.JSCA.Service;

import com.inmedical.JSCA.Entites.Plate;

import java.util.List;

public interface IPlateService {
    public List<Plate> listPlate();



    public void savePlate(Plate plate);

    public void deletePlate(String plateNumber);
}
