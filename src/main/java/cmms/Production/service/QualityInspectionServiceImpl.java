package cmms.Production.service;

import cmms.Production.Dto.QualityInspectionRequestDto;
import cmms.Production.Dto.QualityInspectionResponseDto;
import cmms.Production.common.entity.Employee;
import cmms.Production.entity.ProductionOrder;
import cmms.Production.entity.QualityInspection;
import cmms.Production.common.entity.repository.EmployeeRepository;
import cmms.Production.repository.ProductionOrderRepository;
import cmms.Production.repository.QualityInspectionRepository;
import cmms.Production.utils.QualityInspectionMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QualityInspectionServiceImpl implements QualityInspectionService {

    private final QualityInspectionRepository repository;
    private final QualityInspectionMapper mapper;
    private final ProductionOrderRepository productionOrderRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public QualityInspectionResponseDto createInspection(QualityInspectionRequestDto request) {
        QualityInspection inspection = mapper.toEntity(request);

        // Resolve and validate foreign entities
        ProductionOrder productionOrder = productionOrderRepository.findById(request.getProductionOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Production Order not found with id: " + request.getProductionOrderId()));

        Employee inspector = employeeRepository.findById(request.getInspectorId())
                .orElseThrow(() -> new EntityNotFoundException("Employee (Inspector) not found with id: " + request.getInspectorId()));

        inspection.setProductionOrder(productionOrder);
        inspection.setInspector(inspector);

        QualityInspection savedInspection = repository.save(inspection);
        return mapper.toResponse(savedInspection);
    }

    @Override
    @Transactional(readOnly = true)
    public QualityInspectionResponseDto getInspectionById(Long id) {
        QualityInspection inspection = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inspection not found with id: " + id));
        return mapper.toResponse(inspection);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QualityInspectionResponseDto> getAllInspections() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public QualityInspectionResponseDto updateInspection(Long id, QualityInspectionRequestDto request) {
        QualityInspection existingInspection = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inspection not found with id: " + id));

        existingInspection.setInspectionResult(request.getInspectionResult());
        existingInspection.setRemarks(request.getRemarks());

        if (existingInspection.getProductionOrder() == null || !existingInspection.getProductionOrder().getId().equals(request.getProductionOrderId())) {
            ProductionOrder productionOrder = productionOrderRepository.findById(request.getProductionOrderId())
                    .orElseThrow(() -> new EntityNotFoundException("Production Order not found with id: " + request.getProductionOrderId()));
            existingInspection.setProductionOrder(productionOrder);
        }

        if (existingInspection.getInspector() == null || !existingInspection.getInspector().getId().equals(request.getInspectorId())) {
            Employee inspector = employeeRepository.findById(request.getInspectorId())
                    .orElseThrow(() -> new EntityNotFoundException("Employee (Inspector) not found with id: " + request.getInspectorId()));
            existingInspection.setInspector(inspector);
        }

        return mapper.toResponse(repository.save(existingInspection));
    }

    @Override
    @Transactional
    public void deleteInspection(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Inspection not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
