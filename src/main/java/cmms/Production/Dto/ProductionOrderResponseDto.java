package cmms.Production.Dto;

import cmms.Production.entity.ProductionOrder.OrderStatus;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionOrderResponseDto {

    private Long id;
    private String orderNumber;
    private Long plantId;
    private Long carModelId;
    private OrderStatus status;
    private Integer targetQuantity;
    private Integer completedQuantity;
    private LocalDate expectedEndDate;
    private LocalDate actualEndDate;

    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime lastModifiedAt;
    private Long lastModifiedBy;

}
