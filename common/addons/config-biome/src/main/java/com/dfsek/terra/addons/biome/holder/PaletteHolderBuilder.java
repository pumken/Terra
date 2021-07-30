package com.dfsek.terra.addons.biome.holder;

import com.dfsek.terra.api.world.generator.Palette;
import net.jafama.FastMath;

import java.util.Map;
import java.util.TreeMap;

public class PaletteHolderBuilder {
    private final TreeMap<Integer, Palette> paletteMap = new TreeMap<>();

    public PaletteHolderBuilder add(int y, Palette palette) {
        paletteMap.put(y, palette);
        return this;
    }

    public PaletteHolder build() {

        int min = FastMath.min(paletteMap.keySet().stream().min(Integer::compareTo).orElse(0), 0);
        int max = FastMath.max(paletteMap.keySet().stream().max(Integer::compareTo).orElse(255), 255);

        Palette[] palettes = new Palette[paletteMap.lastKey() + 1 - min];
        for(int y = min; y <= FastMath.max(paletteMap.lastKey(), max); y++) {
            Palette d = null;
            for(Map.Entry<Integer, Palette> e : paletteMap.entrySet()) {
                if(e.getKey() >= y) {
                    d = e.getValue();
                    break;
                }
            }
            if(d == null) throw new IllegalArgumentException("No palette for Y=" + y);
            palettes[y - min] = d;
        }
        return new PaletteHolder(palettes, -min);
    }
}