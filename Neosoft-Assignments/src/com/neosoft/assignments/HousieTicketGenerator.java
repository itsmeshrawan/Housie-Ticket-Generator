package com.neosoft.assignments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HousieTicketGenerator {
	public static void main(String[] args) {

		int inputRows = 3;
		int inputColumns = 9;

		// Generating Ticket
		generatingTicket(inputRows, inputColumns);
	}

	public static void generatingTicket(int inputRows, int inputColumns) {
		Node[] nodes = new Node[1];
		nodes[0] = new Node(inputRows, inputColumns);

		List<Integer> l1 = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			l1.add(i);
		}

		List<Integer> l2 = new ArrayList<>();
		for (int i = 11; i <= 20; i++) {
			l2.add(i);
		}

		List<Integer> l3 = new ArrayList<>();
		for (int i = 21; i <= 30; i++) {
			l3.add(i);
		}

		List<Integer> l4 = new ArrayList<>();
		for (int i = 31; i <= 40; i++) {
			l4.add(i);
		}

		List<Integer> l5 = new ArrayList<>();
		for (int i = 41; i <= 50; i++) {
			l5.add(i);
		}

		List<Integer> l6 = new ArrayList<>();
		for (int i = 51; i <= 60; i++) {
			l6.add(i);
		}

		List<Integer> l7 = new ArrayList<>();
		for (int i = 61; i <= 70; i++) {
			l7.add(i);
		}

		List<Integer> l8 = new ArrayList<>();
		for (int i = 71; i <= 80; i++) {
			l8.add(i);
		}

		List<Integer> l9 = new ArrayList<>();
		for (int i = 81; i <= 90; i++) {
			l9.add(i);
		}

		List<List<Integer>> columns = new ArrayList<>();
		columns.add(l1);
		columns.add(l2);
		columns.add(l3);
		columns.add(l4);
		columns.add(l5);
		columns.add(l6);
		columns.add(l7);
		columns.add(l8);
		columns.add(l9);

		List<List<Integer>> set1 = new ArrayList<>();
		List<List<Integer>> set2 = new ArrayList<>();
		List<List<Integer>> set3 = new ArrayList<>();
		List<List<Integer>> set4 = new ArrayList<>();
		List<List<Integer>> set5 = new ArrayList<>();
		List<List<Integer>> set6 = new ArrayList<>();

		for (int i = 0; i < 9; i++) {
			set1.add(new ArrayList<>());
			set2.add(new ArrayList<>());
			set3.add(new ArrayList<>());
			set4.add(new ArrayList<>());
			set5.add(new ArrayList<>());
			set6.add(new ArrayList<>());
		}

		List<List<List<Integer>>> sets = new ArrayList<>();

		sets.add(set1);
		sets.add(set2);
		sets.add(set3);
		sets.add(set4);
		sets.add(set5);
		sets.add(set6);

		// assigning elements to each set for each column
		for (int i = 0; i < 9; i++) {
			List<Integer> li = columns.get(i);

			int randNumIndex = getRandomNumber(0, li.size() - 1);
			int randNum = li.get(randNumIndex);

			List<Integer> set = sets.get(0).get(i);
			set.add(randNum);

			li.remove(randNumIndex);
		}

		// assign element from last column to random set
		List<Integer> lastCol = columns.get(8);
		int randNumIndex = getRandomNumber(0, lastCol.size() - 1);
		int randNum = lastCol.get(randNumIndex);

		int randSetIndex = getRandomNumber(0, sets.size() - 1);
		List<Integer> randSet = sets.get(randSetIndex).get(8);
		randSet.add(randNum);

		lastCol.remove(randNumIndex);

		// 3 passes over the remaining columns
		for (int pass = 0; pass < 3; pass++) {
			for (int i = 0; i < 9; i++) {
				List<Integer> col = columns.get(i);
				if (col.isEmpty())
					continue;

				int randNumIndexP = getRandomNumber(0, col.size() - 1);
				int randNumP = col.get(randNumIndexP);

				boolean vacantSetFound = false;
				while (!vacantSetFound) {
					int randSetIndexP = getRandomNumber(0, sets.size() - 1);
					List<List<Integer>> randSetP = sets.get(randSetIndexP);

					if (getNumberOfElementsInSet(randSetP) == 15 || randSetP.get(i).size() == 2)
						continue;

					vacantSetFound = true;
					randSetP.get(i).add(randNumP);

					col.remove(randNumIndexP);
				}
			}
		}

		// one more pass over the remaining columns
		for (int i = 0; i < 9; i++) {
			List<Integer> col = columns.get(i);
			if (col.isEmpty())
				continue;

			int randNumIndexP = getRandomNumber(0, col.size() - 1);
			int randNumP = col.get(randNumIndexP);

			boolean vacantSetFound = false;
			while (!vacantSetFound) {
				int randSetIndexP = getRandomNumber(0, sets.size() - 1);
				List<List<Integer>> randSetP = sets.get(randSetIndexP);

				if (getNumberOfElementsInSet(randSetP) == 15 || randSetP.get(i).size() == 3)
					continue;

				vacantSetFound = true;
				randSetP.get(i).add(randNumP);

				col.remove(randNumIndexP);
			}
		}

		// sort the internal sets
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 9; j++) {
				Collections.sort(sets.get(i).get(j));
			}
		}

		// got the sets - need to arrange in tickets now
		int setIndex = 0;
		List<List<Integer>> currSet = sets.get(setIndex);
		Node currTicket = nodes[setIndex];

		// fill first row
		for (int size = 3; size > 0; size--) {
			if (currTicket.getRowCount(0) == 5)
				break;
			for (int colIndex = 0; colIndex < 9; colIndex++) {
				if (currTicket.getRowCount(0) == 5)
					break;
				if (currTicket.A[0][colIndex] != 0)
					continue;

				List<Integer> currSetCol = currSet.get(colIndex);
				if (currSetCol.size() != size)
					continue;

				currTicket.A[0][colIndex] = currSetCol.remove(0);
			}
		}

		// fill second row
		for (int size = 2; size > 0; size--) {
			if (currTicket.getRowCount(1) == 5)
				break;
			for (int colIndex = 0; colIndex < 9; colIndex++) {
				if (currTicket.getRowCount(1) == 5)
					break;
				if (currTicket.A[1][colIndex] != 0)
					continue;

				List<Integer> currSetCol = currSet.get(colIndex);
				if (currSetCol.size() != size)
					continue;

				currTicket.A[1][colIndex] = currSetCol.remove(0);
			}
		}

		// fill third row
		for (int size = 1; size > 0; size--) {
			if (currTicket.getRowCount(2) == 5)
				break;
			for (int colIndex = 0; colIndex < 9; colIndex++) {
				if (currTicket.getRowCount(2) == 5)
					break;
				if (currTicket.A[2][colIndex] != 0)
					continue;

				List<Integer> currSetCol = currSet.get(colIndex);
				if (currSetCol.size() != size)
					continue;

				currTicket.A[2][colIndex] = currSetCol.remove(0);
			}
		}

		try {
			// quick patch to ensure columns are sorted
			Node currentTicket = nodes[0];
			currentTicket.sortColumns();

		} catch (Exception e) {
			// something wrong, not a P0...eating the exception
			System.out.println(e.getMessage());
		}

		// print the ticket
		Node currentTicket = nodes[0];

		for (int r = 0; r < 3; r++) {
			for (int col = 0; col < 9; col++) {
				int num = currentTicket.A[r][col];
				if (num != 0)
					System.out.print(num);

				if (col != 8)
					System.out.print(",");
			}
			if (r != 2)
				System.out.println();
		}
	}

	static class Node {
		int A[][];

		public Node(int row, int column) {
			this.A = new int[row][column];
		}

		public int getRowCount(int r) {
			int count = 0;
			for (int i = 0; i < 9; i++) {
				if (A[r][i] != 0)
					count++;
			}

			return count;
		}

		public int getColCount(int c) {
			int count = 0;
			for (int i = 0; i < 3; i++) {
				if (A[i][c] != 0)
					count++;
			}

			return count;
		}

		// gives the row number of first found empty cell in given column
		public int getEmptyCellInCol(int c) {
			for (int i = 0; i < 3; i++) {
				if (A[i][c] == 0)
					return i;
			}

			return -1;
		}

		private void sortColumnWithThreeNumbers(int c) throws Exception {
			int emptyCell = this.getEmptyCellInCol(c);
			if (emptyCell != -1) {
				throw new Exception("Your column has greater than 3 cells filled, invalid function called");
			}

			int tempArr[] = new int[] { this.A[0][c], this.A[1][c], this.A[2][c] };
			Arrays.sort(tempArr);

			for (int r = 0; r < 3; r++) {
				this.A[r][c] = tempArr[r];
			}
		}

		private void sortColumnWithTwoNumbers(int c) throws Exception {
			int emptyCell = this.getEmptyCellInCol(c);
			if (emptyCell == -1) {
				throw new Exception("Your column has 3 cells filled, invalid function called");
			}

			int cell1, cell2;
			if (emptyCell == 0) {
				cell1 = 1;
				cell2 = 2;
			} else if (emptyCell == 1) {
				cell1 = 0;
				cell2 = 2;
			} else { // emptyCell == 2
				cell1 = 0;
				cell2 = 1;
			}

			if (this.A[cell1][c] < this.A[cell2][c]) {
				return;
			} else {
				// swap
				int temp = this.A[cell1][c];
				this.A[cell1][c] = this.A[cell2][c];
				this.A[cell2][c] = temp;
			}
		}

		private void sortColumn(int c) throws Exception {
			if (this.getColCount(c) == 1) {
				return;
			} else if (this.getColCount(c) == 2) {
				this.sortColumnWithTwoNumbers(c);
			} else {
				this.sortColumnWithThreeNumbers(c);
			}
		}

		public void sortColumns() throws Exception {
			for (int c = 0; c < 9; c++) {
				this.sortColumn(c);
			}
		}
	}

	static int getRandomNumber(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min + 1) + min;
	}

	static int getNumberOfElementsInSet(List<List<Integer>> set) {
		int count = 0;
		for (List<Integer> li : set)
			count += li.size();
		return count;
	}

}