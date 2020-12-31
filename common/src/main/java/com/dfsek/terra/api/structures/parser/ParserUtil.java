package com.dfsek.terra.api.structures.parser;

import com.dfsek.terra.api.structures.parser.exceptions.ParseException;
import com.dfsek.terra.api.structures.parser.lang.Returnable;
import com.dfsek.terra.api.structures.tokenizer.Token;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParserUtil {

    private static final Map<Token.Type, List<Token.Type>> PRECEDENCE = new HashMap<>();
    private static final List<Token.Type> ARITHMETIC = Arrays.asList(Token.Type.ADDITION_OPERATOR, Token.Type.SUBTRACTION_OPERATOR, Token.Type.MULTIPLICATION_OPERATOR, Token.Type.DIVISION_OPERATOR);

    static { // Setup precedence
        PRECEDENCE.put(Token.Type.ADDITION_OPERATOR, Arrays.asList(Token.Type.MULTIPLICATION_OPERATOR, Token.Type.DIVISION_OPERATOR));
        PRECEDENCE.put(Token.Type.SUBTRACTION_OPERATOR, Arrays.asList(Token.Type.MULTIPLICATION_OPERATOR, Token.Type.DIVISION_OPERATOR));
        PRECEDENCE.put(Token.Type.EQUALS_OPERATOR, ARITHMETIC);
        PRECEDENCE.put(Token.Type.NOT_EQUALS_OPERATOR, ARITHMETIC);
        PRECEDENCE.put(Token.Type.GREATER_THAN_OPERATOR, ARITHMETIC);
        PRECEDENCE.put(Token.Type.GREATER_THAN_OR_EQUALS_OPERATOR, ARITHMETIC);
        PRECEDENCE.put(Token.Type.LESS_THAN_OPERATOR, ARITHMETIC);
        PRECEDENCE.put(Token.Type.LESS_THAN_OR_EQUALS_OPERATOR, ARITHMETIC);
    }

    public static void checkType(Token token, Token.Type... expected) throws ParseException {
        for(Token.Type type : expected) if(token.getType().equals(type)) return;
        throw new ParseException("Expected " + Arrays.toString(expected) + " but found " + token.getType(), token.getPosition());
    }

    public static void checkReturnType(Returnable<?> returnable, Returnable.ReturnType... types) throws ParseException {
        for(Returnable.ReturnType type : types) if(returnable.returnType().equals(type)) return;
        throw new ParseException("Expected " + Arrays.toString(types) + " but found " + returnable.returnType(), returnable.getPosition());
    }

    public static void checkArithmeticOperation(Returnable<?> left, Returnable<?> right, Token operation) throws ParseException {
        if(!left.returnType().equals(Returnable.ReturnType.NUMBER) || !right.returnType().equals(Returnable.ReturnType.NUMBER)) {
            throw new ParseException("Operation " + operation.getType() + " not supported between " + left.returnType() + " and " + right.returnType(), operation.getPosition());
        }
    }

    public static void checkBooleanOperation(Returnable<?> left, Returnable<?> right, Token operation) throws ParseException {
        if(!left.returnType().equals(Returnable.ReturnType.BOOLEAN) || !right.returnType().equals(Returnable.ReturnType.BOOLEAN)) {
            throw new ParseException("Operation " + operation.getType() + " not supported between " + left.returnType() + " and " + right.returnType(), operation.getPosition());
        }
    }

    public static void checkVarType(Token token, Returnable.ReturnType returnType) throws ParseException {
        if(returnType.equals(Returnable.ReturnType.STRING) && token.getType().equals(Token.Type.STRING_VARIABLE)) return;
        if(returnType.equals(Returnable.ReturnType.NUMBER) && token.getType().equals(Token.Type.NUMBER_VARIABLE)) return;
        if(returnType.equals(Returnable.ReturnType.BOOLEAN) && token.getType().equals(Token.Type.BOOLEAN_VARIABLE)) return;
        throw new ParseException("Type mismatch, cannot convert from " + returnType + " to " + token.getType(), token.getPosition());
    }

    /**
     * Checks if token is a binary operator
     *
     * @param token Token to check
     * @throws ParseException If token isn't a binary operator
     */
    public static void checkBinaryOperator(Token token) throws ParseException {
        if(!token.isBinaryOperator())
            throw new ParseException("Expected binary operator, found " + token.getType(), token.getPosition());
    }

    public static Returnable.ReturnType getVariableReturnType(Token varToken) throws ParseException {
        switch(varToken.getType()) {
            case NUMBER_VARIABLE:
                return Returnable.ReturnType.NUMBER;
            case STRING_VARIABLE:
                return Returnable.ReturnType.STRING;
            case BOOLEAN_VARIABLE:
                return Returnable.ReturnType.BOOLEAN;
            default:
                throw new ParseException("Unexpected token " + varToken.getType() + "; expected variable declaration", varToken.getPosition());
        }
    }

    public static boolean hasPrecedence(Token.Type first, Token.Type second) {
        if(!PRECEDENCE.containsKey(first)) return false;
        return PRECEDENCE.get(first).contains(second);
    }
}