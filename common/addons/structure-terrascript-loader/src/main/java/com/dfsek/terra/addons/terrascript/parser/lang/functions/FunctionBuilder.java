package com.dfsek.terra.addons.terrascript.parser.lang.functions;

import java.util.List;

import com.dfsek.terra.addons.terrascript.parser.exceptions.ParseException;
import com.dfsek.terra.addons.terrascript.parser.lang.Returnable;
import com.dfsek.terra.addons.terrascript.tokenizer.Position;


public interface FunctionBuilder<T extends Function<?>> {
    T build(List<Returnable<?>> argumentList, Position position) throws ParseException;
    
    int argNumber();
    
    Returnable.ReturnType getArgument(int position);
}