package com.dfsek.terra.bukkit.world.block.data;

import com.dfsek.terra.api.platform.block.BlockFace;
import com.dfsek.terra.api.platform.block.data.Stairs;

public class BukkitStairs extends BukkitBlockData implements Stairs {

    public BukkitStairs(org.bukkit.block.data.type.Stairs delegate) {
        super(delegate);
    }

    @Override
    public Shape getShape() {
        return BukkitEnumAdapter.adapt(((org.bukkit.block.data.type.Stairs) super.getHandle()).getShape());
    }

    @Override
    public void setShape(Shape shape) {
        ((org.bukkit.block.data.type.Stairs) super.getHandle()).setShape(BukkitEnumAdapter.adapt(shape));
    }

    @Override
    public Half getHalf() {
        return BukkitEnumAdapter.adapt(((org.bukkit.block.data.type.Stairs) super.getHandle()).getHalf());
    }

    @Override
    public void setHalf(Half half) {
        ((org.bukkit.block.data.type.Stairs) super.getHandle()).setHalf(BukkitEnumAdapter.adapt(half));
    }

    @Override
    public BlockFace getFacing() {
        return BukkitEnumAdapter.adapt(((org.bukkit.block.data.type.Stairs) super.getHandle()).getFacing());
    }

    @Override
    public void setFacing(BlockFace facing) {
        ((org.bukkit.block.data.type.Stairs) super.getHandle()).setFacing(BukkitEnumAdapter.adapt(facing));
    }

    @Override
    public boolean isWaterlogged() {
        return ((org.bukkit.block.data.type.Stairs) super.getHandle()).isWaterlogged();
    }

    @Override
    public void setWaterlogged(boolean waterlogged) {
        ((org.bukkit.block.data.type.Stairs) super.getHandle()).setWaterlogged(waterlogged);
    }
}