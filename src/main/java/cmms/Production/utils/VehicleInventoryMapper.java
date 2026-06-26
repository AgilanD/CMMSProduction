package cmms.Production.utils;

import cmms.Production.Dto.VehicleInventoryRequestDto;
import cmms.Production.Dto.VehicleInventoryResponseDto;
import cmms.Production.entity.VehicleInventory;
import org.springframework.stereotype.Component;

@Component
public class VehicleInventoryMapper {

    public VehicleInventoryResponseDto toResponse(VehicleInventory entity) {
        if (entity == null) return null;

        return VehicleInventoryResponseDto.builder()
                .id(entity.getId())
                .vin(entity.getVin())
                .productionOrderId(entity.getProductionOrder() != null ? entity.getProductionOrder().getId() : null)
                .carModelId(entity.getCarModel() != null ? entity.getCarModel().getId() : null)
                .color(entity.getColor())
                .status(entity.getStatus())
                .manufacturedDate(entity.getManufacturedDate())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .lastModifiedAt(entity.getLastModifiedAt())
                .lastModifiedBy(entity.getLastModifiedBy())
                .build();
    }

    public VehicleInventory toEntity(VehicleInventoryRequestDto request) {
        if (request == null) return null;

        return VehicleInventory.builder()
                .color(request.getColor())
                .status(request.getStatus())
                .build();
    }
}
