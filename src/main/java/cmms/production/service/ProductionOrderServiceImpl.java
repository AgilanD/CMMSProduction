package cmms.production.service;

import cmms.production.dto.ProductionOrderRequestDto;
import cmms.production.dto.ProductionOrderResponseDto;
import cmms.production.entity.ProductionOrder;
import cmms.production.utils.ProductionOrderMapper;
import cmms.production.repository.ProductionOrderRepository;
import cmms.production.common.entity.repository.PlantsRepository;
import cmms.production.common.entity.repository.CarModuleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

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
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toResponseDto)
                .toList();
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

        if (!existingEntity.getStatus().canTransitionTo(requestDto.getStatus())) {
            throw new IllegalStateException(
                    String.format("Invalid status transition from %s to %s", existingEntity.getStatus(), requestDto.getStatus())
            );
        }

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