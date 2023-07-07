package application;

import java.util.Arrays;

public class LPSolver {

	public static void solveCycle(int n, int m, double d, double[] xObj, double[][] xA, double[] b, int[] basis) {
		double[][] AB = copyColumns(xA, basis, m);
		double[][] ABinverse = calculateInverse(AB);
		double[][] ABinverseTranspose = transpose(ABinverse);
		double[][] xObjB = copyBasicVariables(xObj, basis, m);
		double[][] y = multiply(ABinverseTranspose, xObjB);
		double[][] yTranspose = transpose(y);
		
				
		xObj = objectiveSubtraction(xObj, multiply(yTranspose, xA));
		d = d + multiplyMatrixVector(yTranspose, b)[0];
		xA = multiply(ABinverse, xA);
		b = multiplyMatrixVector(ABinverse, b);
	}
	
	private static double[] multiplyMatrixVector(double[][] matrix, double[] vector) {
        int rows = matrix.length;
        int columns = matrix[0].length;

        double[] result = new double[rows];

        for (int i = 0; i < rows; i++) {
            double sum = 0;
            for (int j = 0; j < columns; j++) {
                sum += matrix[i][j] * vector[j];
            }
            result[i] = sum;
        }

        return result;
    }

	private static double[] objectiveSubtraction(double[] c, double[][] yA) {
		double[] xObj = new double[c.length];
		
		int current = 0;
		
		for (double i : c) {
			xObj[current] = i - yA[0][current];
			current++;
		}

		return xObj;
	}

	private static void printMatrix(double[][] matrix) {
		for (double[] row : matrix) {
			for (double element : row) {
				System.out.print(element + " ");
			}
			System.out.println();
		}
	}

	private static double[][] copyBasicVariables(double[] xObj, int[] basis, int n) {
		double[][] xObjB = new double[basis.length][1];
		int current = 0;

		for (int i : basis) {
			xObjB[current][0] = xObj[i];
			current++;
		}

		return xObjB;
	}

	private static double[][] copyColumns(double[][] xA, int[] basis, int n) {
		double[][] AB = new double[n][n];
		int current = 0;

		for (int i : basis) {
			for (int j = 0; j < n; j++) {
				AB[j][current] = xA[j][i];
			}
			current++;
		}

		return AB;
	}

	// Check if doubles work
	private static double[][] multiply(double[][] matrix1, double[][] matrix2) {
		int m1Rows = matrix1.length;
		int m1Cols = matrix1[0].length;
		int m2Cols = matrix2[0].length;

		double[][] result = new double[m1Rows][m2Cols];

		for (int i = 0; i < m1Rows; i++) {
			for (int j = 0; j < m2Cols; j++) {
				for (int k = 0; k < m1Cols; k++) {
					result[i][j] += matrix1[i][k] * matrix2[k][j];
				}
			}
		}

		return result;
	}

	private static double[][] calculateInverse(double[][] matrix) {
		int n = matrix.length;
		double[][] augmentedMatrix = new double[n][2 * n];

		// [matrix | identity]
		for (int i = 0; i < n; i++) {
			System.arraycopy(matrix[i], 0, augmentedMatrix[i], 0, n);
			augmentedMatrix[i][i + n] = 1;
		}

		// Left side to the identity matrix
		for (int i = 0; i < n; i++) {
			// If the pivot element is zero, swap rows
			if (augmentedMatrix[i][i] == 0) {
				int j = i + 1;
				boolean swapped = false;
				while (j < n) {
					if (augmentedMatrix[j][i] != 0) {
						swapRows(augmentedMatrix, i, j);
						swapped = true;
						break;
					}
					j++;
				}

				if (!swapped) {
					return matrix;
				}
			}

			// Scale the current row to make the pivot element 1
			double pivot = augmentedMatrix[i][i];
			for (int j = 0; j < 2 * n; j++) {
				augmentedMatrix[i][j] /= pivot;
			}

			// Eliminate the elements above and below the pivot
			for (int j = 0; j < n; j++) {
				if (j != i) {
					double factor = augmentedMatrix[j][i];
					for (int k = 0; k < 2 * n; k++) {
						augmentedMatrix[j][k] -= factor * augmentedMatrix[i][k];
					}
				}
			}
		}

		// Extract the inverse matrix from the augmented matrix
		double[][] inverseMatrix = new double[n][n];
		for (int i = 0; i < n; i++) {
			System.arraycopy(augmentedMatrix[i], n, inverseMatrix[i], 0, n);
		}

		return inverseMatrix;
	}

	private static void swapRows(double[][] matrix, int row1, int row2) {
		double[] temp = matrix[row1];
		matrix[row1] = matrix[row2];
		matrix[row2] = temp;
	}

	private static double[][] transpose(double[][] matrix) {
		int m = matrix.length; // Number of rows in the matrix
		int n = matrix[0].length; // Number of columns in the matrix

		double[][] transposedMatrix = new double[n][m];

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				transposedMatrix[j][i] = matrix[i][j];
			}
		}

		return transposedMatrix;
	}

}
