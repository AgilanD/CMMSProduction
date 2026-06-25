package cmms.Production.controller;


import cmms.Production.Dto.QualityInspectionRequestDto;
import cmms.Production.Dto.QualityInspectionResponseDto;
import cmms.Production.Dto.VehicleInventoryRequestDto;
import cmms.Production.Dto.VehicleInventoryResponseDto;
import cmms.Production.service.QualityInspectionService;
import cmms.Production.service.VehicleInventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Production")
public class ProductionController {

    private final QualityInspectionService service;

    private final VehicleInventoryService Vehicleservice;

    @PostMapping("/AddVehicles")
    public VehicleInventoryResponseDto create(@Valid @RequestBody VehicleInventoryRequestDto requestDto) {
        return Vehicleservice.createVehicle(requestDto);
    }

    @GetMapping("/GetByIds/{id}")
    public VehicleInventoryResponseDto getByIds(@PathVariable Long id) {
        return Vehicleservice.getVehicleById(id);
    }

    @GetMapping("/GetAllVehicle")
    public List<VehicleInventoryResponseDto> getAlls() {
        return Vehicleservice.getAllVehicles();
    }

    @PutMapping("/AddVehicle/{id}")
    public VehicleInventoryResponseDto update(
            @PathVariable Long id,
            @Valid @RequestBody VehicleInventoryRequestDto requestDto) {
        return Vehicleservice.updateVehicle(id, requestDto);
    }

    @DeleteMapping("/AddVehicle/{id}")
    public void deleteById(@PathVariable Long id) {
        Vehicleservice.deleteVehicle(id);
    }

    @GetMapping("/Checking")
    public String Message(){
        return "SuccessFully Connect the ProductionServices";
    }


    @PostMapping("/AddQualityInspectionResponse")
    public QualityInspectionResponseDto create( @RequestBody QualityInspectionRequestDto request) {
        return service.createInspection(request);
    }

    @GetMapping("/GetById/{id}")
    public QualityInspectionResponseDto getById(@PathVariable Long id) {
        return service.getInspectionById(id);
    }

    @GetMapping("/GetAllQualityInspection")
    public List<QualityInspectionResponseDto> getAll() {
        return service.getAllInspections();
    }

    @PutMapping("/UpdateById/{id}")
    public QualityInspectionResponseDto update(@PathVariable Long id, @RequestBody QualityInspectionRequestDto request) {
        return service.updateInspection(id, request);
    }

    @DeleteMapping("/DeleteById/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteInspection(id);
    }



}
