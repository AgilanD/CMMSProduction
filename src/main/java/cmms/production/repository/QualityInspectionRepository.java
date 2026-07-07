package cmms.production.repository;

import cmms.production.entity.QualityInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QualityInspectionRepository extends JpaRepository<QualityInspection ,Long> {

}
