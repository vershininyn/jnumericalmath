package dev.projects.math.optimization.unconstrained.local.algorithms.base;

import dev.projects.math.optimization.unconstrained.local.base.StopCriteriaEnumeration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class StopCriteria {
    private String criteriaName;

    private StopCriteriaEnumeration stopCriteriaEnum;

    @Override
    public String toString() {
        return stopCriteriaEnum.toString() + " " + criteriaName;
    }
}
