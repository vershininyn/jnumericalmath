package dev.projects.math.jnumexpservice.repository;

import dev.projects.math.jnumexpservice.entity.NumericalExperimentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumericalExperimentRepository extends JpaRepository<NumericalExperimentEntity, Integer> {

}
