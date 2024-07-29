package com.inmedical.JSCA.Controller;

import com.inmedical.JSCA.Entites.DTO.DayPlateDTO;
import com.inmedical.JSCA.Entites.DTO.PlateDTO;
import com.inmedical.JSCA.Entites.DTO.RestriccionDTO;
import com.inmedical.JSCA.Entites.Plate;
import com.inmedical.JSCA.Entites.Restriccion;
import com.inmedical.JSCA.Service.PlateService;
import com.inmedical.JSCA.Service.RestriccionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1.0/pico-placa")
@CrossOrigin(value = "http://localhost:4200")
public class APIController {
    @Autowired
    private PlateService plateService;

    @Autowired
    private RestriccionService restriccionService;



    @PostMapping("/createdPlate")
    public ResponseEntity<Plate> createPlate(@RequestBody PlateDTO plateDTO){
        Plate createPlate = plateService.createPlate(plateDTO);
        return ResponseEntity.ok(createPlate);
    }

    @PostMapping("/createdRestriccion")
    public ResponseEntity<Restriccion> createRestriccion(@RequestBody RestriccionDTO restriccionDTO){
        Restriccion createRestriccion = restriccionService.createRestriccion(restriccionDTO);
        return  ResponseEntity.ok(createRestriccion);
    }
    @GetMapping("/can-circulate/{plateNumber}")
    public ResponseEntity<?> canCirculate(@PathVariable String plateNumber) {
        if (!plateService.plateExists(plateNumber)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró una placa con el número: " + plateNumber);
        }

        try {
            Map<LocalDate, String> circulationStatus = plateService.canCirculate(plateNumber);
            return ResponseEntity.ok(circulationStatus);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la solicitud: " + e.getMessage());
        }
    }
    @GetMapping("/plates")
    public ResponseEntity<List<Plate>> listAllPlates() {
        List<Plate> plates = plateService.listPlate();
        return ResponseEntity.ok(plates);
    }

    @GetMapping("/restriccion")
    public ResponseEntity<List<Restriccion>> listAllRestriccion() {
        List<Restriccion> restriccions = restriccionService.listRestriccion();
        return ResponseEntity.ok(restriccions);
    }

    @GetMapping("/plates/{number}")
    public ResponseEntity<?> getPlateByNumber(@PathVariable String number) {
        try {
            Plate plate = plateService.getPlateByNumber(number);
            return ResponseEntity.ok(plate);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/plates/{plateNumber}")
    public ResponseEntity<?> deletePlate(@PathVariable String plateNumber) {
        try {
            plateService.deletePlate(plateNumber);
            return ResponseEntity.ok().body("Placa eliminada con éxito");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la placa: " + e.getMessage());
        }
    }

    @PostMapping("/plates/{plateNumber}/days")
    public ResponseEntity<?> addDayPlateToPlate(@PathVariable String plateNumber, @RequestBody DayPlateDTO dayPlateDTO) {
        try {
            Plate updatedPlate = plateService.addDayPlateToPlate(plateNumber, dayPlateDTO);
            return ResponseEntity.ok(updatedPlate);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al agregar el día a la placa: " + e.getMessage());
        }
    }

    @DeleteMapping("/restriccion/{restriccionId}")
    public ResponseEntity<?> deleteRestriccion(@PathVariable Long restriccionId) {
        try {
            restriccionService.deleteRestriccion(restriccionId);
            return ResponseEntity.ok().body("Restricción eliminada con éxito");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la restricción: " + e.getMessage());
        }
    }
}
