package cmms.production.service;

import cmms.production.dto.VehicleInventoryRequestDto;
import cmms.production.dto.VehicleInventoryResponseDto;
import cmms.production.common.entity.CarModule;
import cmms.production.entity.ProductionOrder;
import cmms.production.entity.VehicleInventory;
import cmms.production.common.entity.repository.CarModuleRepository;
import cmms.production.repository.ProductionOrderRepository;
import cmms.production.repository.VehicalInventoryRepository;
import cmms.production.utils.VehicleInventoryMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleInventoryServiceImpl implements VehicleInventoryService {

    private final VehicalInventoryRepository repository;
    private final VehicleInventoryMapper mapper;
    private final ProductionOrderRepository productionOrderRepository;
    private final CarModuleRepository carModuleRepository;

    @Override
    public VehicleInventoryResponseDto createVehicle(VehicleInventoryRequestDto requestDto) {
        VehicleInventory vehicle = mapper.toEntity(requestDto);

        ProductionOrder productionOrder = productionOrderRepository.findById(requestDto.getProductionOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Production Order not found with id: " + requestDto.getProductionOrderId()));

        CarModule carModel = carModuleRepository.findById(requestDto.getCarModelId())
                .orElseThrow(() -> new EntityNotFoundException("Car Model not found with id: " + requestDto.getCarModelId()));

        vehicle.setProductionOrder(productionOrder);
        vehicle.setCarModel(carModel);

        VehicleInventory savedVehicle = repository.save(vehicle);
        return mapper.toResponse(savedVehicle);
    }

    @Override
    public VehicleInventoryResponseDto getVehicleById(Long id) {
        VehicleInventory vehicle = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle record not found with id: " + id));
        return mapper.toResponse(vehicle);
    }

    @Override
    public List<VehicleInventoryResponseDto> getAllVehicles() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public VehicleInventoryResponseDto updateVehicle(Long id, VehicleInventoryRequestDto requestDto) {
        VehicleInventory existingVehicle = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle record not found with id: " + id));

        existingVehicle.setColor(requestDto.getColor());
        existingVehicle.setStatus(requestDto.getStatus());

        if (existingVehicle.getProductionOrder() == null || !existingVehicle.getProductionOrder().getId().equals(requestDto.getProductionOrderId())) {
            ProductionOrder productionOrder = productionOrderRepository.findById(requestDto.getProductionOrderId())
                    .orElseThrow(() -> new EntityNotFoundException("Production Order not found with id: " + requestDto.getProductionOrderId()));
            existingVehicle.setProductionOrder(productionOrder);
        }

        if (existingVehicle.getCarModel() == null || !existingVehicle.getCarModel().getId().equals(requestDto.getCarModelId())) {
            CarModule carModel = carModuleRepository.findById(requestDto.getCarModelId())
                    .orElseThrow(() -> new EntityNotFoundException("Car Model not found with id: " + requestDto.getCarModelId()));
            existingVehicle.setCarModel(carModel);
        }

        return mapper.toResponse(repository.save(existingVehicle));
    }

    @Override
    public void deleteVehicle(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Vehicle record not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
