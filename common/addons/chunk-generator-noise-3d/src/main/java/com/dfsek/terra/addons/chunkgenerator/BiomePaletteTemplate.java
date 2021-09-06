package com.dfsek.terra.addons.chunkgenerator;

import com.dfsek.tectonic.annotations.Default;
import com.dfsek.tectonic.annotations.Value;
import com.dfsek.tectonic.loading.object.ObjectTemplate;

import com.dfsek.terra.addons.chunkgenerator.palette.PaletteHolder;
import com.dfsek.terra.addons.chunkgenerator.palette.PaletteInfo;
import com.dfsek.terra.addons.chunkgenerator.palette.SlantHolder;
import com.dfsek.terra.api.config.meta.Meta;
import com.dfsek.terra.api.world.generator.Palette;


public class BiomePaletteTemplate implements ObjectTemplate<PaletteInfo> {
    @Value("slant")
    @Default
    private final @Meta SlantHolder slant;
    @Value("palette")
    private @Meta PaletteHolder palette;
    @Value("ocean.level")
    private @Meta int seaLevel;
    
    @Value("ocean.palette")
    private @Meta Palette oceanPalette;
    
    @Override
    public PaletteInfo get() {
        return new PaletteInfo(palette, slant, oceanPalette, seaLevel);
    }
}