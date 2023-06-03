import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {

	static int gold_amount = 0;
	static int max_level_allowed = 0;
	static Random rnd = new Random();

	public static void main(String[] args) throws Exception {
		String[][] allPieces = readingCSV();
		takeInput();

		long startGreedy = System.nanoTime();
		String[][] selectedPiecesForGreedy = greedyApproach(allPieces, gold_amount);
		long totalTimeGreedy = System.nanoTime() - startGreedy;

		long startRandom = System.nanoTime();
		String[][] selectedPiecesForRandom = randomApproach(allPieces, gold_amount);
		long totalTimeRandom = System.nanoTime() - startRandom;

		System.out.println("\n-Greedy Approach-");
		print(selectedPiecesForGreedy, totalTimeGreedy);
		System.out.println("\n---------o---------");
		System.out.println("\n-Random Approach-");
		print(selectedPiecesForRandom, totalTimeRandom);
	}

	static String[][] greedyApproach(String[][] allPieces, int temp_gold_amount) throws Exception { // It chooses the one with the highest "attack point / gold" ratio.
		
		String[][] selectedPieces = new String[max_level_allowed][4];
		int typeNumber = 0;
		
		for (int i = 0; i < max_level_allowed; i++) {
			int a = 0;
			for (int x = 0; x < 10; x++) {
				if (temp_gold_amount >= Integer.parseInt(allPieces[typeNumber + a][2])) {
					for (int k = 0; k < selectedPieces[i].length; k++) {
						selectedPieces[i][k] = allPieces[typeNumber + a][k];
					}
					break;
				} else {
					a++;
				}
			}

			float efficentNumber = Float.parseFloat(allPieces[typeNumber][3]) / Float.parseFloat(allPieces[typeNumber][2]);					
			for (int j = typeNumber; j < typeNumber + 9; j++) {
				if (temp_gold_amount >= Integer.parseInt(allPieces[j][2])) {
					if (temp_gold_amount >= Integer.parseInt(allPieces[j + 1][2]) && efficentNumber < Float.parseFloat(allPieces[j + 1][3]) / Float.parseFloat(allPieces[j + 1][2])) {
						efficentNumber = Float.parseFloat(allPieces[j + 1][3]) / Float.parseFloat(allPieces[j + 1][2]);
						for (int l = 0; l < selectedPieces[i].length; l++) {
							selectedPieces[i][l] = allPieces[j + 1][l];
						}
					}
				}
			}

			if (selectedPieces[i][0] != null) {
				temp_gold_amount -= Integer.parseInt(selectedPieces[i][2]);
			}
			typeNumber += 10;
		}

		return selectedPieces;
	}

	static String[][] randomApproach(String[][] allPieces, int temp_gold_amount) { // It chooses a random piece of each type.																				
		String[][] selectedPieces = new String[max_level_allowed][4];

		for (int i = 0; i < max_level_allowed; i++) {
			boolean flag = false;
			for (int j = 0; j < 10; j++) {
				if (temp_gold_amount >= Integer.parseInt(allPieces[10 * i + j][2])) {
					flag = true;
					break;
				}
			}

			while (flag) {
				int r = rnd.nextInt(10);
				if (temp_gold_amount >= Integer.parseInt(allPieces[10 * i + r][2])) {
					for (int j = 0; j < selectedPieces[i].length; j++) {
						selectedPieces[i][j] = allPieces[10 * i + r][j];
					}
					break;
				} else {
					r = rnd.nextInt(10);
				}
			}

			if (selectedPieces[i][0] != null) {
				temp_gold_amount -= Integer.parseInt(selectedPieces[i][2]);
			}
		}

		return selectedPieces;
	}

	static String[][] readingCSV() throws Exception { // It reads the file and saves it in a matrix.
		Scanner sc = new Scanner(new File("input_1.csv"));
		String[][] arr = new String[90][4];
		int i = 0;
		sc.nextLine();
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			arr[i] = line.split(",");
			i++;
		}
		sc.close();

		return arr;
	}

	static void takeInput() {
		Scanner scn = new Scanner(System.in);

		do {
			System.out.println("Please Enter The Gold Amount (It can be between 5 and 1200)");
			gold_amount = scn.nextInt();
		} while (gold_amount < 5 || gold_amount > 1200);

		do {
			System.out.println("Please Enter Maximum Allowed Level (It can be between 1 and 9)");
			max_level_allowed = scn.nextInt();
		} while (max_level_allowed < 1 || max_level_allowed > 9);

		scn.close();
	}

	static void print(String[][] selectedPieces, long totalTimeSpent) {
		int totalCost = 0;
		int totalAttackPoint = 0;
		for (int i = 0; i < selectedPieces.length; i++) {
			if (selectedPieces[i][0] != null) {
				System.out.println(selectedPieces[i][0] + " (" + selectedPieces[i][1] + ", " + selectedPieces[i][2] + " Gold, " + selectedPieces[i][3] + " Attack)");
			}
			if (selectedPieces[i][0] != null) {
				totalCost += Integer.parseInt(selectedPieces[i][2]);
				totalAttackPoint += Integer.parseInt(selectedPieces[i][3]);
			}
		}
		
		System.out.println();
		System.out.println("Total Cost of Army = " + totalCost + " Gold");
		System.out.println("Total Attack Point of Army = " + totalAttackPoint + " Attack Point");
		System.out.println("Total Time Spent = " + totalTimeSpent + " Nanosecond");
	}

	public static class ArmyCombination {
		int totalAttackPoint;
		int[][] army = new int[9][4];
	}
}
