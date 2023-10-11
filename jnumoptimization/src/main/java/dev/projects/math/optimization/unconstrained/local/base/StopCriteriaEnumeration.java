package dev.projects.math.optimization.unconstrained.local.base;

import java.util.HashMap;
import java.util.Map;

public enum StopCriteriaEnumeration {
    STOPPED_BY_TIME,

    STOPPED_BY_DENOMINATED_ITERATION_COUNT,
    STOPPED_BY_FUNCTION_COMPUTE_COUNT,
    STOPPED_BY_GRADIENT_COMPUTE_COUNT,

    STOPPED_BY_MAX_REPEATED_FUNCTIONS_OUTPUT,

    STOPPED_BY_CRITICAL_EUCLID_GRADIENT_NORM,

    STOPPED_BY_CRITICAL_LEARNING_EUCLID_GRADIENT_NORM,
    STOPPED_BY_CRITICAL_FUNCTION_VALUE,
    STOPPED_BY_CRITICAL_LEARNING_EUCLID_DIRECTION_NORM,

    STOPPED_BY_CRITICAL_GLOBAL_EPSILON,
    STOPPED_BY_GLOBAL_MAX_MULTIPLICITY_FACTOR;

    private static final Map<StopCriteriaEnumeration, StopCriteriaQuality> qualityMap = new HashMap<>() {{
        put(STOPPED_BY_CRITICAL_FUNCTION_VALUE, StopCriteriaQuality.RANK_ONE);
        put(STOPPED_BY_CRITICAL_GLOBAL_EPSILON, StopCriteriaQuality.RANK_ONE);
        put(STOPPED_BY_DENOMINATED_ITERATION_COUNT, StopCriteriaQuality.RANK_ONE);

        put(STOPPED_BY_TIME, StopCriteriaQuality.RANK_TWO);
        put(STOPPED_BY_FUNCTION_COMPUTE_COUNT, StopCriteriaQuality.RANK_TWO);
        put(STOPPED_BY_GRADIENT_COMPUTE_COUNT, StopCriteriaQuality.RANK_TWO);
        put(STOPPED_BY_MAX_REPEATED_FUNCTIONS_OUTPUT, StopCriteriaQuality.RANK_TWO);
        put(STOPPED_BY_CRITICAL_EUCLID_GRADIENT_NORM, StopCriteriaQuality.RANK_TWO);
        put(STOPPED_BY_CRITICAL_LEARNING_EUCLID_GRADIENT_NORM, StopCriteriaQuality.RANK_TWO);
        put(STOPPED_BY_CRITICAL_LEARNING_EUCLID_DIRECTION_NORM, StopCriteriaQuality.RANK_TWO);
        put(STOPPED_BY_GLOBAL_MAX_MULTIPLICITY_FACTOR, StopCriteriaQuality.RANK_TWO);
    }};

    public boolean isSuccessfullyStopped() {
        return ((this == STOPPED_BY_CRITICAL_LEARNING_EUCLID_GRADIENT_NORM)
                    || (this == STOPPED_BY_CRITICAL_FUNCTION_VALUE)
                    || (this == STOPPED_BY_CRITICAL_LEARNING_EUCLID_DIRECTION_NORM)
                    || (this == STOPPED_BY_CRITICAL_EUCLID_GRADIENT_NORM)
                    || (this == STOPPED_BY_CRITICAL_GLOBAL_EPSILON)
                    || (this == STOPPED_BY_GLOBAL_MAX_MULTIPLICITY_FACTOR));
    }

    public StopCriteriaQuality getQualityOfCriteria() {
        return qualityMap.get(this);
    }
}
