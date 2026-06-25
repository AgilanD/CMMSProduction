package cmms.Production.service;


import cmms.Production.Dto.ProductionOrderRequestDto;
import cmms.Production.Dto.ProductionOrderResponseDto;
import java.util.List;

public interface ProductionOrderService {
    ProductionOrderResponseDto createOrder(ProductionOrderRequestDto requestDto);
    List<ProductionOrderResponseDto> getAllOrders();
    ProductionOrderResponseDto getOrderById(Long id);
    ProductionOrderResponseDto updateOrder(Long id, ProductionOrderRequestDto requestDto);
    void deleteOrder(Long id);
}
