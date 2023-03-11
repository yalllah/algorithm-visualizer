package edu.kit.algorithms.or1;

import edu.kit.algorithms.or1.model.SimplexTableau;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class SimplexSolver {



    public List<SimplexTableau> solveTableau(SimplexTableau simplexTableau) {
        int[] pivotPosition = getPivotPosition(simplexTableau);
        while (pivotPosition.length > 1) {
            updateTableau(simplexTableau, pivotPosition);
            pivotPosition = getPivotPosition(simplexTableau);
        }
        return Collections.singletonList(
                simplexTableau
        );
        /*
        return Collections.singletonList(
                new SimplexTableau(
                        null,
                        null,
                        null,
                        null,
                        null,
                        800 / 3.0
        ));
        */
    }

    private void updateTableau(SimplexTableau simplexTableau, int[] pivotPosition) {
        double pivotElement = simplexTableau.simplexTable()[pivotPosition[0]][pivotPosition[1]];
        for (int i = 0; i < simplexTableau.simplexTable().length; i++) {
            for (int j = 0; j < simplexTableau.simplexTable().length; j++) {
                if (i == pivotPosition[1] && j == pivotPosition[0]) {
                    simplexTableau.simplexTable()[i][j] = 1 / pivotElement;
                } else if (i == pivotPosition[0]) {
                    simplexTableau.simplexTable()[i][j] = simplexTableau.simplexTable()[i][j] / pivotElement;
                } else if (j == pivotPosition[1]) {
                    simplexTableau.simplexTable()[i][j] = simplexTableau.simplexTable()[i][j] / (-pivotElement);
                } else {
                    simplexTableau.simplexTable()[i][j] = simplexTableau.simplexTable()[i][j] - (simplexTableau.simplexTable()[pivotPosition[0]][j] * simplexTableau.simplexTable()[i][pivotPosition[1]]) / pivotElement;
                }
            }
        }

    }
    private int getMinGoalFunctionValueIndex(double[] goalCoefficients) {
        int minValueIndex = IntStream.range(0, goalCoefficients.length).reduce((i, j) -> goalCoefficients[i] < goalCoefficients[j] ? i : j).getAsInt();
        return (goalCoefficients[minValueIndex] < 0) ? minValueIndex : -1;
    }

    private int getMinQuotientIndex(double[] columnValues, double[] rightSideValues) {
        int[] validIndicesList = IntStream.range(0, columnValues.length).filter(i -> columnValues[i] > 0).toArray();
        if (validIndicesList.length == 0) return -1;
        return IntStream.of(validIndicesList).reduce((i, j) -> (rightSideValues[i] / columnValues[i] < rightSideValues[j] / columnValues[j]) ? i : j).getAsInt();
    }

    private int[] getPivotPosition(SimplexTableau simplexTableau) {
        int columnIndex = getMinGoalFunctionValueIndex(simplexTableau.goalCoefficients());
        if (columnIndex == -1) return new int[]{0};
        int rowIndex = getMinQuotientIndex(getColumn(columnIndex, simplexTableau), simplexTableau.rightSide());
        if (rowIndex == -1) return new int[]{0};
        return new int[]{rowIndex, columnIndex};
    }

    private double[] getColumn(int index, SimplexTableau simplexTableau) {
        double[] row = new double[simplexTableau.simplexTable().length];
        for (int i = 0; i < simplexTableau.simplexTable().length; i++) {
            row[i] = simplexTableau.simplexTable()[i][index];
        }
        return row;
    }


}


