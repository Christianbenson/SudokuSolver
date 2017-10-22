package com.mycompany.sudokusolver;

public class SudokuSolver {
    private int[][] matrix;
    private int rows;
    private int cols;
    private boolean falseVal;

    /*
     * Constructs an empty sudokuboard with the entered
     * rows and columns.
     */

    public SudokuSolver(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        matrix = new int[rows][cols];
    }
    /*
     * Attempts to solve the sudoku. returns true if the sudoku
     * was succesfully solved, otherwise false.
     */
    public boolean solve() {
        if(falseVal){
            return false;
        }
        return solve(0, 0);
    }
    /*
     * Sets the value at entered position to entered value. Returns false if
     * the entered value is not allowed(e.g. breaking sudoku rules) or
     * if the position is outside the sudokuboard.
     */
    public boolean setValue(int row, int col, int value) {
        if (row >= rows || col >= cols || row < 0 || col < 0 || value <= 0
                || value > 9) {
            return false;
        }
        if(!checkVal(row, col, value)){
            falseVal = true;
            return false;
        }
        matrix[row][col] = value;
        return true;
    }
    /*
     * Returns the value at entered position. Returns 0
     * if position is outside sudokuboard.
     */
    public int getValue(int row, int col) {
        if (row >= rows || col >= cols || row < 0 || col < 0) {
            return 0;
        }
        return matrix[row][col];

    }
    /*
     * Clears all values from local memory, removing any existing values.
     */
    public void clear() {
        falseVal = false;
        matrix = new int[rows][cols];
    }

    private boolean solve(int row, int col) {
        if (isEmpty(row, col)) {
            for (int i = 1; i < 10; i++) {
                if (checkVal(row, col, i)) {
                    matrix[row][col] = i;
                    if (col == cols - 1) {
                        if (row == rows - 1) {
                            return true; // SISTA RUTAN, HAR INTE FALSE
                            // RETURNERATS ÄNNU FINNS LÖSNING!
                        } else {
                            if (solve(row + 1, 0)) {
                                return true;
                            } else {
                                matrix[row][col] = 0; // RENSA RUTAN SÅ NÄSTA
                                // VÄRDE KOLLAS
                            }
                        }
                    } else {
                        if (solve(row, col + 1)) {
                            return true;
                        } else {
                            matrix[row][col] = 0;
                        }
                    }
                } else {
                    // BACKTRACK
                }
            }
        } else {
            int currentVal = matrix[row][col];
            if(!checkVal(row, col, currentVal)){
                return false;
            }
            if (col == cols - 1) {
                if (row == rows - 1) {
                    return true; // SISTA RUTAN
                } else {
                    if (solve(row + 1, 0)) {
                        return true;
                    } else {
                        // BACKTRACK
                    }
                }
            } else {
                if (solve(row, col + 1)) {
                    return true;
                } else {
                    // BACKTRACK
                }
            }
        }
        return false; // INGEN LÖSNING, BACKTRACKA
    }

    /*
     * finds out if the value is allowed by checking the row , column and region
     */
    private boolean checkVal(int row, int col, int val) {
        if (!isEmpty(row, col)) {
            int tempVal = matrix[row][col];
            matrix[row][col] = 0;
            for (int i = 0; i < cols; i++) {
                if (matrix[row][i] == val) {
                    return false;
                }
            }
            for (int i = 0; i < rows; i++) {
                if (matrix[i][col] == val) {
                    return false;
                }
            }

            boolean temp = checkRegion(getRegion(row, col), val);
            matrix[row][col] = tempVal;
            return temp;
        }

        for (int i = 0; i < cols; i++) {
            if (matrix[row][i] == val) {
                return false;
            }
        }
        for (int i = 0; i < rows; i++) {
            if (matrix[i][col] == val) {
                return false;
            }
        }
        return checkRegion(getRegion(row, col), val);
    }

    private int getRegion(int row, int col) {
        int vertical = 0;
        int horizontal = 0;
        if (row > 2 && row < 6) {
            vertical = 1;
        } else if (row > 5) {
            vertical = 2;
        }
        if (col > 2 && col < 6) {
            horizontal = 1;
        } else if (col > 5) {
            horizontal = 2;
        }
        switch (vertical) {
            case 1:
                return horizontal + 3;
            case 2:
                return horizontal + 6;
            default:
                return horizontal;
        }

    }

    private boolean checkRegion(int region, int val) {
        int rows = (region / 3) * 3;
        int cols = (region % 3) * 3;
        for (int i = rows; i < rows + 3; i++) {
            for (int k = cols; k < cols + 3; k++) {
                if (matrix[i][k] == val) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isEmpty(int row, int col) {
        return matrix[row][col] == 0;
    }
}
