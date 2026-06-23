package cmms.Production.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;

import java.time.LocalDateTime;

@Entity
@Table(name = "quality_inspections")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QualityInspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Generated
    @Column(name = "inspection_number", nullable = false, unique = true, insertable = false, updatable = false)
    private String inspectionNumber;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "production_order_id", nullable = false, unique = true)
    private ProductionOrder productionOrder;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inspector_id", nullable = false)
    private Employee inspector;

    @Enumerated(EnumType.STRING)
    @Column(name = "inspection_result", nullable = false)
    private InspectionResult inspectionResult;

    @Column(columnDefinition = "text")
    private String remarks;


    @CreationTimestamp
    @Column(name = "inspected_at", nullable = false, updatable = false)
    private LocalDateTime inspectedAt;


    public enum InspectionResult {
        PASS, FAIL, PENDING
    }
}
