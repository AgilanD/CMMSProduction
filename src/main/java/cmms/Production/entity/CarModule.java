package cmms.Production.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "car_module")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "model_name", nullable = false, unique = true)
    private String modelName;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type", nullable = false)
    private FuelType fuelType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Transmission transmission;

    @Column(name = "base_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal basePrice;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "color_options", columnDefinition = "jsonb")
    private String colorOptions;

    @Temporal(TemporalType.DATE)
    @Column(name = "launch_date")
    private LocalDate launchDate;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;



//    @Column(name = "last_modified_at ",nullable = false)
//    private LocalDateTime lastModifiedAt = LocalDateTime.MIN;
//
//
//    @Column(name = "last_modified_by ",nullable = false)
//    private Long lastModifiedBy = 0L;



    public enum FuelType {
        PETROL, DIESEL, ELECTRIC, HYBRID
    }

    public enum Transmission {
        MANUAL, AUTOMATIC
    }

}
