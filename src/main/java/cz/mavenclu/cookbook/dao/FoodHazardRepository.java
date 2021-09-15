package cz.mavenclu.cookbook.dao;

import cz.mavenclu.cookbook.entity.FoodHazard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodHazardRepository extends JpaRepository<FoodHazard, Long> {

}
