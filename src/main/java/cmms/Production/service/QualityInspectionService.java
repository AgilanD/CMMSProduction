package cmms.Production.service;

import cmms.Production.Dto.QualityInspectionRequestDto;
import cmms.Production.Dto.QualityInspectionResponseDto;
import java.util.List;

public interface QualityInspectionService {
    QualityInspectionResponseDto createInspection(QualityInspectionRequestDto request);
    QualityInspectionResponseDto getInspectionById(Long id);
    List<QualityInspectionResponseDto> getAllInspections();
    QualityInspectionResponseDto updateInspection(Long id, QualityInspectionRequestDto request);
    void deleteInspection(Long id);
}
