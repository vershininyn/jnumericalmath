package dev.projects.math.jnummath.jlinalgebra.blocks;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class BlockJobConfiguration {
    private int position = 0, size = 0;
    private File file = null;
}
