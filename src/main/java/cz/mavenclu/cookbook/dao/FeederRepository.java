package cz.mavenclu.cookbook.dao;

import cz.mavenclu.cookbook.entity.Feeder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeederRepository extends JpaRepository<Feeder, Long> {
}
