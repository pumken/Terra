package com.dfsek.terra.addon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dfsek.terra.addon.dependency.CircularDependencyException;
import com.dfsek.terra.addon.dependency.DependencyException;
import com.dfsek.terra.addon.dependency.DependencyVersionException;
import com.dfsek.terra.api.addon.BaseAddon;


public class DependencySorter {
    private final Map<String, BaseAddon> addons = new HashMap<>();
    private final Map<String, Boolean> visited = new HashMap<>();
    
    private final List<BaseAddon> addonList = new ArrayList<>();
    
    public void add(BaseAddon addon) {
        addons.put(addon.getID(), addon);
        visited.put(addon.getID(), false);
        addonList.add(addon);
    }
    
    private void sortDependencies(BaseAddon addon, List<BaseAddon> sort) {
        addon.getDependencies().forEach((id, range) -> {
            if(!addons.containsKey(id)) {
                throw new DependencyException("Addon " + addon.getID() + " specifies dependency on " + id + ", versions " + range +
                                              ", but no such addon is installed.");
            }
            
            BaseAddon dependency = addons.get(id);
            
            if(!range.isSatisfiedBy(dependency.getVersion())) {
                throw new DependencyVersionException("Addon " + addon.getID() + " specifies dependency on " + id + ", versions " + range +
                                                     ", but non-matching version " + dependency.getVersion() + " is installed..");
            }
            
            if(!visited.get(dependency.getID())) { // if we've not visited it yet
                visited.put(dependency.getID(), true); // we've visited it now
                
                sortDependencies(dependency, sort);
                
                sort.add(dependency); // add it to the list.
            }
        });
    }
    
    private void checkDependencies(BaseAddon base, BaseAddon current) {
        current.getDependencies().forEach((id, range) -> {
            BaseAddon dependency = addons.get(id);
            if(dependency.getID().equals(base.getID())) {
                throw new CircularDependencyException(
                        "Addon " + base.getID() + " has circular dependency beginning with " + dependency.getID());
            }
            checkDependencies(base, dependency);
        });
    }
    public List<BaseAddon> sort() {
        List<BaseAddon> sorted = new ArrayList<>();
        
        for(int i = addonList.size() - 1; i >= 0; i--) {
            BaseAddon addon = addonList.get(i);
            
            checkDependencies(addon, addon);
            
            addonList.remove(i);
            
            if(!visited.get(addon.getID())) {
                sortDependencies(addon, sorted);
            }
    
            if(!visited.get(addon.getID())) {
                sorted.add(addon);
                visited.put(addon.getID(), true);
            }
        }
        
        return sorted;
    }
}