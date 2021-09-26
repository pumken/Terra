package com.dfsek.terra.addons.terrascript.script.builders;

import java.util.List;

import com.dfsek.terra.addons.terrascript.parser.exceptions.ParseException;
import com.dfsek.terra.addons.terrascript.parser.lang.Returnable;
import com.dfsek.terra.addons.terrascript.parser.lang.functions.FunctionBuilder;
import com.dfsek.terra.addons.terrascript.script.functions.StateFunction;
import com.dfsek.terra.addons.terrascript.tokenizer.Position;
import com.dfsek.terra.api.Platform;


public class StateFunctionBuilder implements FunctionBuilder<StateFunction> {
    private final Platform platform;
    
    public StateFunctionBuilder(Platform platform) {
        this.platform = platform;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public StateFunction build(List<Returnable<?>> argumentList, Position position) {
        if(argumentList.size() < 4) throw new ParseException("Expected data", position);
        return new StateFunction((Returnable<Number>) argumentList.get(0), (Returnable<Number>) argumentList.get(1),
                                 (Returnable<Number>) argumentList.get(2), (Returnable<String>) argumentList.get(3), platform, position);
    }
    
    @Override
    public int argNumber() {
        return 4;
    }
    
    @Override
    public Returnable.ReturnType getArgument(int position) {
        return switch(position) {
            case 0, 1, 2 -> Returnable.ReturnType.NUMBER;
            case 3 -> Returnable.ReturnType.STRING;
            default -> null;
        };
    }
}
