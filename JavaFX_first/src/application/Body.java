package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import java.util.Scanner;


public class Body {

	@FXML
	private Button solve;
	@FXML
	private TextArea outObjFunction;
	@FXML
	private TextArea outConstriants;

	// Start the solve
	public void solveEvent(ActionEvent e) {
		int n;
		int m;
		double d;
		double[] xObj;
		double[][] xA;
		double[] b;
		int[] basis;

		Scanner sc = new Scanner(System.in);

		// Variables
		System.out.println("Number of variables?");
		n = sc.nextInt();
		xObj = new double[n];

		// Obj Variables
		for (int i = 1; i <= n; i++) {
			System.out.printf("Value of x%d\n", i);
			xObj[i - 1] = sc.nextDouble();
		}

		// + d
		System.out.println("Starting value (d)");
		d = sc.nextDouble();

		// Get constraints
		System.out.println("number of constriants");
		m = sc.nextInt();
		xA = new double[m][n];
		b = new double[m];
		basis = new int[m];

		// Get A
		for (int ii = 0; ii < m; ii++) {
			for (int i = 0; i < n; i++) {
				System.out.printf("Value of A[%d][%d] (x,y) \n", i + 1, ii + 1);
				xA[ii][i] = sc.nextDouble();
			}
		}

		// get b
		for (int ii = 0; ii < m; ii++) {
			System.out.printf("Value of b[%d] (y) \n", ii + 1);
			b[ii] = sc.nextDouble();
		}

		// get Basis
		for (int ii = 0; ii < m; ii++) {
			System.out.printf("Row of basis [%d] (y) \n", ii + 1);
			basis[ii] = (sc.nextInt() - 1);
		}
		
		LPSolver.solveCycle(n, m, d, xObj, xA, b, basis);
	}
}
