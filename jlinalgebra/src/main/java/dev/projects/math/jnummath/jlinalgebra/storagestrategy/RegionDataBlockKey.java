package dev.projects.math.jnummath.jlinalgebra.storagestrategy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
public class RegionDataBlockKey {
    private int blockIndex = 0;

    private int startRowIndex = 0;
    private int endRowIndex = 0;

    private int startColumnIndex = 0;
    private int endColumnIndex = 0;

    @Override
    public int hashCode() {
        return Objects.hash(blockIndex, startRowIndex, endRowIndex, startColumnIndex, endColumnIndex);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (!(obj instanceof RegionDataBlockKey)) return false;

        RegionDataBlockKey otherKey = (RegionDataBlockKey) obj;

        return (getBlockIndex() == otherKey.getBlockIndex())
                && (getStartRowIndex() == otherKey.getStartRowIndex())
                && (getEndRowIndex() == otherKey.getEndRowIndex())
                && (getStartColumnIndex() == otherKey.getStartColumnIndex())
                && (getEndColumnIndex() == otherKey.getEndColumnIndex());
    }
}
