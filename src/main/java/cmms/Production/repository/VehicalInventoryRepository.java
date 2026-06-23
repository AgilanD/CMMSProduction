package cmms.Production.repository;

import cmms.Production.entity.VehicleInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VehicalInventoryRepository extends JpaRepository<VehicleInventory,Long>{
}
