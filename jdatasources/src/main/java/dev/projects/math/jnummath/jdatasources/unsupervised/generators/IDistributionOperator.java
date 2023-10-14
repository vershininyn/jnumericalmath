package dev.projects.math.jnummath.jdatasources.unsupervised.generators;

import java.util.List;

@FunctionalInterface
public interface IDistributionOperator {
    List<Double> generate(List<Double> uniformVector);
}
