package dev.projects.math.jnumexpservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class NumericalExperimentEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "num-experiments-sequence-generator")
    @SequenceGenerator(name = "num-experiments-sequence-generator", sequenceName = "num_experiments_sequence", allocationSize = 1, initialValue = 1)
    private Integer id;

    private int iterationCount;
}
