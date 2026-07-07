package cmms.production.utils;

import cmms.production.dto.ProductionOrderRequestDto;
import cmms.production.dto.ProductionOrderResponseDto;
import cmms.production.entity.ProductionOrder;
import org.springframework.stereotype.Component;

@Component
public class ProductionOrderMapper {

    public ProductionOrder toEntity(ProductionOrderRequestDto dto) {
        if (dto == null) return null;

        return ProductionOrder.builder()
                .status(dto.getStatus())
                .targetQuantity(dto.getTargetQuantity())
                .completedQuantity(dto.getCompletedQuantity() != null ? dto.getCompletedQuantity() : 0)
                .expectedEndDate(dto.getExpectedEndDate())
                .actualEndDate(dto.getActualEndDate())
                .build();
    }

    public ProductionOrderResponseDto toResponseDto(ProductionOrder entity) {
        if (entity == null) return null;

        return ProductionOrderResponseDto.builder()
                .id(entity.getId())
                .orderNumber(entity.getOrderNumber())
                .plantId(entity.getPlant() != null ? entity.getPlant().getId() : null)
                .carModelId(entity.getCarModel() != null ? entity.getCarModel().getId() : null)
                .status(entity.getStatus())
                .targetQuantity(entity.getTargetQuantity())
                .completedQuantity(entity.getCompletedQuantity())
                .expectedEndDate(entity.getExpectedEndDate())
                .actualEndDate(entity.getActualEndDate())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .lastModifiedAt(entity.getLastModifiedAt())
                .lastModifiedBy(entity.getLastModifiedBy())
                .build();
    }
}
