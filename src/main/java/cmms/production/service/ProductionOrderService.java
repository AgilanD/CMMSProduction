package cmms.production.service;


import cmms.production.dto.ProductionOrderRequestDto;
import cmms.production.dto.ProductionOrderResponseDto;
import java.util.List;

public interface ProductionOrderService {
    ProductionOrderResponseDto createOrder(ProductionOrderRequestDto requestDto);
    List<ProductionOrderResponseDto> getAllOrders();
    ProductionOrderResponseDto getOrderById(Long id);
    ProductionOrderResponseDto updateOrder(Long id, ProductionOrderRequestDto requestDto);
    void deleteOrder(Long id);
}
