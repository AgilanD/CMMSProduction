package cmms.production.entity;


import cmms.production.usercontext.UserContext;
import cmms.production.common.entity.CarModule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SoftDelete(strategy = SoftDeleteType.DELETED, columnName = "is_deleted")
public class VehicleInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @org.hibernate.annotations.GeneratedColumn(value = "'vin-' || id")
    @Column(nullable = false, unique = true, length = 17, insertable = false, updatable = false)
    private String vin;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "production_order_id", nullable = true)
    private ProductionOrder productionOrder;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_model_id", nullable = true)
    private CarModule carModel;

    @Column(nullable = false)
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleStatus status;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "manufactured_date", nullable = false, updatable = false)
    private LocalDate manufacturedDate;

    @CreatedDate
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT '2026-06-23 19:54:30'")
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "created_by", nullable = false, columnDefinition = "BIGINT DEFAULT 1")
    private Long createdBy;

    @LastModifiedDate
    @Column(name = "last_modified_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT '2026-06-23 19:54:30'")
    private LocalDateTime lastModifiedAt;

    @LastModifiedBy
    @Column(name = "last_modified_by", nullable = false, columnDefinition = "BIGINT DEFAULT 1")
    private Long lastModifiedBy;


@PrePersist
protected void onCreate() {
    this.createdAt = LocalDateTime.now(java.time.ZoneOffset.UTC);
    this.lastModifiedAt = LocalDateTime.now(java.time.ZoneOffset.UTC);

    this.createdBy = UserContext.getUserId();
    this.lastModifiedBy = UserContext.getUserId();
}

    @PreUpdate
    protected void onUpdate() {
        this.lastModifiedAt = LocalDateTime.now(java.time.ZoneOffset.UTC);
        this.lastModifiedBy = UserContext.getUserId();
    }



    public enum VehicleStatus {

        MANUFACTURED, INSPECTED, DELIVERED

    }



}

