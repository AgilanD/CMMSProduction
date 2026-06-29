package cmms.Production.common.entity.repository;

import cmms.Production.common.entity.Plants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantsRepository extends JpaRepository<Plants, Long> {


}
