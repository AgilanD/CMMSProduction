package cmms.Production.service;

import cmms.Production.Dto.VehicleInventoryRequestDto;
import cmms.Production.Dto.VehicleInventoryResponseDto;
import java.util.List;

public interface VehicleInventoryService {
    VehicleInventoryResponseDto createVehicle(VehicleInventoryRequestDto requestDto);
    VehicleInventoryResponseDto getVehicleById(Long id);
    List<VehicleInventoryResponseDto> getAllVehicles();
    VehicleInventoryResponseDto updateVehicle(Long id, VehicleInventoryRequestDto requestDto);
    void deleteVehicle(Long id);
}
