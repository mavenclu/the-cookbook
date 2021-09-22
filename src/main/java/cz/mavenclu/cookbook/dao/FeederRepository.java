package cz.mavenclu.cookbook.dao;

import cz.mavenclu.cookbook.entity.Feeder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeederRepository extends JpaRepository<Feeder, Long> {

    Optional<Feeder> findByIdAndChefId(Long id, String chefId);

    List<Feeder> findAllByChefId(String chefId);
}
