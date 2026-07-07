package cmms.production.service;

import cmms.production.dto.VehicleInventoryRequestDto;
import cmms.production.dto.VehicleInventoryResponseDto;
import java.util.List;

public interface VehicleInventoryService {
    VehicleInventoryResponseDto createVehicle(VehicleInventoryRequestDto requestDto);
    VehicleInventoryResponseDto getVehicleById(Long id);
    List<VehicleInventoryResponseDto> getAllVehicles();
    VehicleInventoryResponseDto updateVehicle(Long id, VehicleInventoryRequestDto requestDto);
    void deleteVehicle(Long id);
}
