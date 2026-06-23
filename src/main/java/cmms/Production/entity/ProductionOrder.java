package cmms.Production.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;

import java.time.LocalDate;

@Entity
@Table(name = "production_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Generated
    @Column(name = "order_number", nullable = false, unique = true, insertable = false, updatable = false)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plant_id", nullable = false)
    private Plants plant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_model_id", nullable = false)
    private CarModule carModel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(name = "target_quantity", nullable = false)
    private Integer targetQuantity;

    @Builder.Default
    @Column(name = "completed_quantity", nullable = false)
    private Integer completedQuantity = 0;

    @Temporal(TemporalType.DATE)
    @Column(name = "expected_end_date", nullable = false)
    private LocalDate expectedEndDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "actual_end_date")
    private LocalDate actualEndDate;


    public enum OrderStatus {
        PENDING, IN_PROGRESS, COMPLETED, CANCELLED
    }

}
