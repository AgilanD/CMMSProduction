package cmms.production.service;

import cmms.production.dto.QualityInspectionRequestDto;
import cmms.production.dto.QualityInspectionResponseDto;
import java.util.List;

public interface QualityInspectionService {
    QualityInspectionResponseDto createInspection(QualityInspectionRequestDto request);
    QualityInspectionResponseDto getInspectionById(Long id);
    List<QualityInspectionResponseDto> getAllInspections();
    QualityInspectionResponseDto updateInspection(Long id, QualityInspectionRequestDto request);
    void deleteInspection(Long id);
}
