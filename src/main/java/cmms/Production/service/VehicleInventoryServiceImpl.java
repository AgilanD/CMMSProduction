package cmms.Production.service;

import cmms.Production.Dto.VehicleInventoryRequestDto;
import cmms.Production.Dto.VehicleInventoryResponseDto;
import cmms.Production.common.entity.CarModule;
import cmms.Production.entity.ProductionOrder;
import cmms.Production.entity.VehicleInventory;
import cmms.Production.common.entity.repository.CarModuleRepository;
import cmms.Production.repository.ProductionOrderRepository;
import cmms.Production.repository.VehicalInventoryRepository;
import cmms.Production.utils.VehicleInventoryMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

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
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
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
