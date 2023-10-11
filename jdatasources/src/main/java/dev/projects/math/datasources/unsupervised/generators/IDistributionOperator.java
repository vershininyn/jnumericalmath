package dev.projects.math.datasources.unsupervised.generators;

import java.util.List;

@FunctionalInterface
public interface IDistributionOperator {
    List<Double> generate(List<Double> uniformVector);
}
