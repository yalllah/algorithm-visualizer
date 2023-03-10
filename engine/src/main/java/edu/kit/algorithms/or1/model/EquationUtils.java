package edu.kit.algorithms.or1.model;


import lombok.Getter;

@Getter
public enum EquationUtils {
    GREATER_THAN_OR_EQUAL_TO(">="),
    LESS_THAN_OR_EQUAL_TO("<="),
    LESS_THAN("<"),
    GREATER_THAN(">"),
    EQUAL_TO("=");

    private final String symbol;

    EquationUtils(String symbol) {
        this.symbol = symbol;
    }

}
