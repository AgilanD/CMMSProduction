package cmms.production.controller;


import cmms.production.dto.*;
import cmms.production.service.ProductionOrderService;
import cmms.production.service.QualityInspectionService;
import cmms.production.service.VehicleInventoryService;
import cmms.production.usercontext.RequireRole;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Production")
public class ProductionController {

    private final QualityInspectionService service;

    private final VehicleInventoryService vehicleservice;

    private final ProductionOrderService productionOrderService;

    @PostMapping("/AddVehiclesinVENTED")
    public VehicleInventoryResponseDto create(@Valid @RequestBody VehicleInventoryRequestDto requestDto) {
        return vehicleservice.createVehicle(requestDto);
    }
    @GetMapping("/GetByIds/{id}")
    public VehicleInventoryResponseDto getByIds(@PathVariable Long id) {
        return vehicleservice.getVehicleById(id);
    }

    @GetMapping("/GetAllVehicle")
    @RequireRole({"ADMIN", "PLANT_MANAGER"})
    public List<VehicleInventoryResponseDto> getAlls() {
        return vehicleservice.getAllVehicles();
    }

    @PutMapping("/AddVehicle/{id}")
    public VehicleInventoryResponseDto update(@PathVariable Long id, @Valid @RequestBody VehicleInventoryRequestDto requestDto) {
        return vehicleservice.updateVehicle(id, requestDto);
    }

    @DeleteMapping("/AddVehicle/{id}")
    public void deleteById(@PathVariable Long id) {
        vehicleservice.deleteVehicle(id);
    }

    @GetMapping("/Checking")
    @RequireRole({"ADMIN", "PLANT_MANAGER"})
    public String message(){
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

    @PostMapping("/Productionsorders")
    public ProductionOrderResponseDto createOrder(@Valid @RequestBody ProductionOrderRequestDto requestDto) {
        return productionOrderService.createOrder(requestDto);
    }

    @GetMapping("/Productionsorders/{id}")
    public ProductionOrderResponseDto getOrderById(@PathVariable Long id) {
        return productionOrderService.getOrderById(id);
    }

    @GetMapping("/Productionsorders")
    public List<ProductionOrderResponseDto> getAllOrders() {
        return productionOrderService.getAllOrders();
    }

    @PutMapping("/Productionsorders/{id}")
    public ProductionOrderResponseDto updateOrder(@PathVariable Long id, @Valid @RequestBody ProductionOrderRequestDto requestDto) {
        return productionOrderService.updateOrder(id, requestDto);
    }

    @DeleteMapping("/Productionsorders/{id}")
    public void deleteOrder(@PathVariable Long id) {
        productionOrderService.deleteOrder(id);
    }



}
