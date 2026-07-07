package cmms.production.dto;

import cmms.production.entity.ProductionOrder.OrderStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionOrderRequestDto {

    @NotNull(message = "Plant ID cannot be null")
    private Long plantId;

    @NotNull(message = "Car Model ID cannot be null")
    private Long carModelId;

    @NotNull(message = "Order status is required")
    private OrderStatus status;

    @NotNull(message = "Target quantity is required")
    @Positive(message = "Target quantity must be a positive number")
    private Integer targetQuantity;

    private Integer completedQuantity;

    @NotNull(message = "Expected end date is required")
    @FutureOrPresent(message = "Expected end date cannot be in the past")
    private LocalDate expectedEndDate;

    private LocalDate actualEndDate;
}
