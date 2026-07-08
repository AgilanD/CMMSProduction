package cmms.production.dto;

import cmms.production.entity.QualityInspection.InspectionResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QualityInspectionResponseDto {

    private Long id;
    private String inspectionNumber;
    private Long productionOrderId;
    private Long inspectorId;
    private InspectionResult inspectionResult;
    private String remarks;
    private LocalDateTime inspectedAt;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime lastModifiedAt;
    private Long lastModifiedBy;

}
