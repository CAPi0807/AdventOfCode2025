package software.aoc.day10.b.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LinearOptimizer {

    private static final double EPSILON = 1e-9;

    public long solveMinPresses(double[][] matrix, double[] target) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        // Calcular límite superior seguro para búsqueda (basado en el target más alto)
        // Como los coeficientes son positivos, ninguna variable puede exceder el max(target).
        double maxTarget = 0;
        for (double v : target) maxTarget = Math.max(maxTarget, v);
        int searchLimit = (int) maxTarget + 2;

        // Gauss-Jordan para RREF
        int pivotRow = 0;
        int[] pivotColByRow = new int[rows];
        Arrays.fill(pivotColByRow, -1);
        boolean[] isFreeVar = new boolean[cols];
        Arrays.fill(isFreeVar, true);

        for (int col = 0; col < cols && pivotRow < rows; col++) {
            int sel = pivotRow;
            for (int i = pivotRow + 1; i < rows; i++) {
                if (Math.abs(matrix[i][col]) > Math.abs(matrix[sel][col])) sel = i;
            }

            if (Math.abs(matrix[sel][col]) > EPSILON) {
                swapRows(matrix, target, sel, pivotRow);
                isFreeVar[col] = false;
                pivotColByRow[pivotRow] = col;

                double div = matrix[pivotRow][col];
                for (int j = col; j < cols; j++) matrix[pivotRow][j] /= div;
                target[pivotRow] /= div;

                for (int i = 0; i < rows; i++) {
                    if (i != pivotRow) {
                        double factor = matrix[i][col];
                        for (int j = col; j < cols; j++) matrix[i][j] -= factor * matrix[pivotRow][j];
                        target[i] -= factor * target[pivotRow];
                    }
                }
                pivotRow++;
            }
        }

        // Verificar consistencia básica
        for (int i = pivotRow; i < rows; i++) {
            if (Math.abs(target[i]) > EPSILON) return -1;
        }

        List<Integer> freeIndices = new ArrayList<>();
        for (int j = 0; j < cols; j++) if (isFreeVar[j]) freeIndices.add(j);

        // Optimización con límite dinámico
        return findBestIntegerSolution(matrix, target, pivotColByRow, pivotRow, freeIndices, cols, searchLimit);
    }

    private long findBestIntegerSolution(double[][] rref, double[] target, int[] pivotColByRow, int rank,
                                         List<Integer> freeIndices, int totalVars, int limit) {
        long minTotal = Long.MAX_VALUE;
        boolean found = false;

        // Si hay demasiadas variables libres, el límite dinámico puede ser lento.
        // Para este problema específico, rara vez hay más de 2 variables libres.
        if (freeIndices.size() > 3) limit = 100; // Fallback de seguridad por rendimiento

        int[] freeValues = new int[freeIndices.size()];

        while (true) {
            double currentSum = 0;
            boolean valid = true;
            double[] solution = new double[totalVars];

            // Asignar variables libres
            for (int i = 0; i < freeIndices.size(); i++) {
                solution[freeIndices.get(i)] = freeValues[i];
                currentSum += freeValues[i];
            }

            // Calcular variables pivote
            for (int r = 0; r < rank; r++) {
                int pCol = pivotColByRow[r];
                double val = target[r];
                for (int fIdx : freeIndices) {
                    val -= rref[r][fIdx] * solution[fIdx];
                }

                // Debe ser entero positivo (o cero)
                if (val < -EPSILON || Math.abs(val - Math.round(val)) > EPSILON) {
                    valid = false;
                    break;
                }
                solution[pCol] = Math.round(val);
                currentSum += solution[pCol];
            }

            if (valid) {
                if ((long) currentSum < minTotal) {
                    minTotal = (long) currentSum;
                    found = true;
                }
            }

            if (freeIndices.isEmpty()) break;

            // Iterador de combinación
            int k = 0;
            while (k < freeIndices.size()) {
                freeValues[k]++;
                if (freeValues[k] <= limit) { // USO DEL LÍMITE DINÁMICO
                    break;
                } else {
                    freeValues[k] = 0;
                    k++;
                }
            }
            if (k == freeIndices.size()) break;
        }

        return found ? minTotal : -1; // Retornamos -1 explícito si no hay solución
    }

    private void swapRows(double[][] M, double[] V, int r1, int r2) {
        double[] tempRow = M[r1]; M[r1] = M[r2]; M[r2] = tempRow;
        double tempVal = V[r1]; V[r1] = V[r2]; V[r2] = tempVal;
    }
}