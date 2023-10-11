package dev.projects.math.linalgebra.storagestrategy;

import dev.projects.math.linalgebra.blocks.IDataBlock;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RegionDataBlocksMap
        extends HashMap<RegionDataBlockKey, IDataBlock>
        implements Map<RegionDataBlockKey, IDataBlock> {
    public RegionDataBlocksMap() {

    }

    public RegionDataBlocksMap(Map<RegionDataBlockKey, IDataBlock> map) {
        super(map);
    }

    @Override
    public IDataBlock get(Object key) {
        if ((key == null)
                || (!(key instanceof Long)))
            return null;

        final long searchBlockIndex = (Long)key;

        Predicate<RegionDataBlockKey>
                keyFilterPredicate = (regionKey) -> (regionKey.getBlockIndex() == searchBlockIndex);

        RegionDataBlockKey searchKey = keySet().stream().filter(keyFilterPredicate).findFirst().orElse(null);

        return super.get(searchKey);
    }

    public Set<Integer> getKeysSet() {
        return keySet().stream().map(key -> key.getBlockIndex()).collect(Collectors.toSet());
    }
}
