package cmms.production.service;

import cmms.production.dto.QualityInspectionRequestDto;
import cmms.production.dto.QualityInspectionResponseDto;
import cmms.production.common.entity.Employee;
import cmms.production.entity.ProductionOrder;
import cmms.production.entity.QualityInspection;
import cmms.production.common.entity.repository.EmployeeRepository;
import cmms.production.repository.ProductionOrderRepository;
import cmms.production.repository.QualityInspectionRepository;
import cmms.production.utils.QualityInspectionMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class QualityInspectionServiceImpl implements QualityInspectionService {

    private final QualityInspectionRepository repository;
    private final QualityInspectionMapper mapper;
    private final ProductionOrderRepository productionOrderRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public QualityInspectionResponseDto createInspection(QualityInspectionRequestDto request) {
        QualityInspection inspection = mapper.toEntity(request);

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
    public QualityInspectionResponseDto getInspectionById(Long id) {
        QualityInspection inspection = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inspection not found with id: " + id));
        return mapper.toResponse(inspection);
    }
    @Override
    public List<QualityInspectionResponseDto> getAllInspections() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
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
    public void deleteInspection(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Inspection not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
