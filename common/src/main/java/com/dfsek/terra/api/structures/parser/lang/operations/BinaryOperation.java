package com.dfsek.terra.api.structures.parser.lang.operations;

import com.dfsek.terra.api.math.vector.Location;
import com.dfsek.terra.api.platform.world.Chunk;
import com.dfsek.terra.api.structures.parser.lang.Returnable;
import com.dfsek.terra.api.structures.structure.Rotation;
import com.dfsek.terra.api.structures.tokenizer.Position;

public abstract class BinaryOperation<I, O> implements Returnable<O> {
    private final Returnable<I> left;
    private final Returnable<I> right;
    private final Position start;

    public BinaryOperation(Returnable<I> left, Returnable<I> right, Position start) {
        this.left = left;
        this.right = right;
        this.start = start;
    }

    public abstract O apply(I left, I right);

    @Override
    public Position getPosition() {
        return start;
    }

    @Override
    public O apply(Location location, Rotation rotation) {
        return apply(left.apply(location, rotation), right.apply(location, rotation));
    }

    @Override
    public O apply(Location location, Chunk chunk, Rotation rotation) {
        return apply(left.apply(location, chunk, rotation), right.apply(location, chunk, rotation));
    }
}