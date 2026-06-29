package cmms.Production.service;

import cmms.Production.Dto.ProductionOrderRequestDto;
import cmms.Production.Dto.ProductionOrderResponseDto;
import cmms.Production.entity.ProductionOrder;
import cmms.Production.utils.ProductionOrderMapper;
import cmms.Production.repository.ProductionOrderRepository;
import cmms.Production.common.entity.repository.PlantsRepository;
import cmms.Production.common.entity.repository.CarModuleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductionOrderServiceImpl implements ProductionOrderService {

    private final ProductionOrderRepository orderRepository;
    private final PlantsRepository plantsRepository;
    private final CarModuleRepository carModuleRepository;
    private final ProductionOrderMapper orderMapper;

    @Override
    public ProductionOrderResponseDto createOrder(ProductionOrderRequestDto requestDto) {
        ProductionOrder entity = orderMapper.toEntity(requestDto);

        entity.setPlant(plantsRepository.findById(requestDto.getPlantId())
                .orElseThrow(() -> new EntityNotFoundException("Plant not found with ID: " + requestDto.getPlantId())));

        entity.setCarModel(carModuleRepository.findById(requestDto.getCarModelId())
                .orElseThrow(() -> new EntityNotFoundException("Car Model not found with ID: " + requestDto.getCarModelId())));

        ProductionOrder savedEntity = orderRepository.save(entity);
        return orderMapper.toResponseDto(savedEntity);
    }

    @Override
    public List<ProductionOrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductionOrderResponseDto getOrderById(Long id) {
        ProductionOrder entity = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Production Order not found with ID: " + id));
        return orderMapper.toResponseDto(entity);
    }

    @Override
    public ProductionOrderResponseDto updateOrder(Long id, ProductionOrderRequestDto requestDto) {
        ProductionOrder existingEntity = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Production Order not found with ID: " + id));

        existingEntity.setStatus(requestDto.getStatus());
        existingEntity.setTargetQuantity(requestDto.getTargetQuantity());
        if (requestDto.getCompletedQuantity() != null) {
            existingEntity.setCompletedQuantity(requestDto.getCompletedQuantity());
        }
        existingEntity.setExpectedEndDate(requestDto.getExpectedEndDate());
        existingEntity.setActualEndDate(requestDto.getActualEndDate());

        existingEntity.setPlant(plantsRepository.findById(requestDto.getPlantId())
                .orElseThrow(() -> new EntityNotFoundException("Plant not found with ID: " + requestDto.getPlantId())));

        existingEntity.setCarModel(carModuleRepository.findById(requestDto.getCarModelId())
                .orElseThrow(() -> new EntityNotFoundException("Car Model not found with ID: " + requestDto.getCarModelId())));

        ProductionOrder updatedEntity = orderRepository.save(existingEntity);
        return orderMapper.toResponseDto(updatedEntity);
    }

    @Override
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new EntityNotFoundException("Production Order not found with ID: " + id);
        }
        orderRepository.deleteById(id);
    }
}
