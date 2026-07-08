package cmms.production.utils;

import cmms.production.dto.QualityInspectionRequestDto;
import cmms.production.dto.QualityInspectionResponseDto;
import cmms.production.entity.QualityInspection;
import org.springframework.stereotype.Component;

@Component
public class QualityInspectionMapper {

    public QualityInspectionResponseDto toResponse(QualityInspection entity) {
        if (entity == null) return null;

        return QualityInspectionResponseDto.builder()
                .id(entity.getId())
                .inspectionNumber(entity.getInspectionNumber())
                .productionOrderId(entity.getProductionOrder() != null ? entity.getProductionOrder().getId() : null)
                .inspectorId(entity.getInspector() != null ? entity.getInspector().getId() : null)
                .inspectionResult(entity.getInspectionResult())
                .remarks(entity.getRemarks())
                .inspectedAt(entity.getInspectedAt())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .lastModifiedAt(entity.getLastModifiedAt())
                .lastModifiedBy(entity.getLastModifiedBy())
                .build();
    }

    public QualityInspection toEntity(QualityInspectionRequestDto request) {
        if (request == null) return null;

        return QualityInspection.builder()
                .inspectionResult(request.getInspectionResult())
                .remarks(request.getRemarks())
                .build();
    }
}
