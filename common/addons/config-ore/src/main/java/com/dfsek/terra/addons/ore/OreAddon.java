/*
 * Copyright (c) 2020-2021 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.terra.addons.ore;

import com.dfsek.terra.addons.manifest.api.AddonInitializer;
import com.dfsek.terra.api.Platform;
import com.dfsek.terra.api.addon.BaseAddon;
import com.dfsek.terra.api.event.events.config.pack.ConfigPackPreLoadEvent;
import com.dfsek.terra.api.event.functional.FunctionalEventHandler;
import com.dfsek.terra.api.inject.annotations.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OreAddon implements AddonInitializer {
    private static final Logger logger = LoggerFactory.getLogger(OreAddon.class);
    
    @Inject
    private Platform platform;
    
    @Inject
    private BaseAddon addon;
    
    @Override
    public void initialize() {
        platform.getEventManager()
                .getHandler(FunctionalEventHandler.class)
                .register(addon, ConfigPackPreLoadEvent.class)
                .then(event -> event.getPack().registerConfigType(new OreConfigType(), addon.key("ORE"), 1))
                .failThrough();
        
        logger.warn("The ore-config addon is deprecated and scheduled for removal in Terra 7.0. It is recommended to use the ore-config-v2 addon for future pack development instead.");
    }
}
