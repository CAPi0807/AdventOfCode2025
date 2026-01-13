package software.aoc.day10.a.service;

import java.util.Arrays;


  //Resuelve sistemas de ecuaciones lineales sobre GF(2).
  //Minimiza el peso de Hamming (número de 1s) en la solución.

public class GaussianSolver {

    // Retorna -1 si no hay solución
    public int solveMinimumPresses(int[][] matrix, int[] target) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        // Construir matriz aumentada [A | b]
        int[][] augmented = new int[rows][cols + 1];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(matrix[i], 0, augmented[i], 0, cols);
            augmented[i][cols] = target[i];
        }

        // 1. Eliminación Gaussiana (Forma Escalonada)
        int pivotRow = 0;
        int[] pivotColIndex = new int[rows]; // Guarda qué columna es el pivote para cada fila
        Arrays.fill(pivotColIndex, -1);

        for (int col = 0; col < cols && pivotRow < rows; col++) {
            // Buscar fila con 1 en esta columna
            int sel = pivotRow;
            while (sel < rows && augmented[sel][col] == 0) sel++;

            if (sel < rows) {
                // Intercambiar filas
                swapRows(augmented, sel, pivotRow);
                pivotColIndex[pivotRow] = col;

                // Hacer 0 el resto de la columna
                for (int i = 0; i < rows; i++) {
                    if (i != pivotRow && augmented[i][col] == 1) {
                        xorRow(augmented, i, pivotRow);
                    }
                }
                pivotRow++;
            }
        }

        // Verificar consistencia (0 = 1)
        for (int i = pivotRow; i < rows; i++) {
            if (augmented[i][cols] == 1) return -1; // Sistema imposible
        }


        // Las columnas que NO son pivotes son variables libres.
        // Recursivamente probamos 0 y 1 para las variables libres para encontrar el mínimo.
        return findMinWeightSolution(augmented, pivotColIndex, pivotRow, cols);
    }

    private int findMinWeightSolution(int[][] matrix, int[] pivotCols, int rank, int totalVars) {
        int minWeight = Integer.MAX_VALUE;

        // Identificar variables libres (aquellas columnas que no están en pivotCols)
        boolean[] isPivot = new boolean[totalVars];
        for (int i = 0; i < rank; i++) isPivot[pivotCols[i]] = true;

        // Contar variables libres
        int freeVarCount = 0;
        int[] freeVarIndices = new int[totalVars]; // Mapeo simple
        for (int j = 0; j < totalVars; j++) {
            if (!isPivot[j]) freeVarIndices[freeVarCount++] = j;
        }

        // Probar todas las combinaciones de variables libres (2^k)
        // Para inputs de AoC k suele ser muy pequeño.
        int combinations = 1 << freeVarCount;

        for (int i = 0; i < combinations; i++) {
            int[] solution = new int[totalVars];
            int currentWeight = 0;

            // Asignar valores a variables libres según los bits de 'i'
            for (int bit = 0; bit < freeVarCount; bit++) {
                if ((i & (1 << bit)) != 0) {
                    solution[freeVarIndices[bit]] = 1;
                    currentWeight++;
                }
            }

            // Back-substitution para encontrar las variables pivote
            // Empezamos desde la última fila pivote hacia arriba
            for (int r = rank - 1; r >= 0; r--) {
                int pivotCol = pivotCols[r];
                int sum = matrix[r][totalVars]; // El valor target (lado derecho)

                // Restar (XOR) el efecto de las variables a la derecha
                for (int c = pivotCol + 1; c < totalVars; c++) {
                    if (matrix[r][c] == 1 && solution[c] == 1) {
                        sum ^= 1;
                    }
                }
                solution[pivotCol] = sum;
                if (sum == 1) currentWeight++;
            }

            minWeight = Math.min(minWeight, currentWeight);
        }

        return minWeight;
    }

    private void swapRows(int[][] M, int r1, int r2) {
        int[] temp = M[r1];
        M[r1] = M[r2];
        M[r2] = temp;
    }

    private void xorRow(int[][] M, int targetRow, int sourceRow) {
        for (int c = 0; c < M[0].length; c++) {
            M[targetRow][c] ^= M[sourceRow][c];
        }
    }
}