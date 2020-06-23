package tictactoe;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static char[][] board = new char[3][3];
    private static int blanks = 0, count_x = 0, count_o = 0;
    private static boolean win = false;

    public static void main(String[] args) {
        initializeBoard();
        printBoard();
        moves();
    }

    private static void initializeBoard() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    private static void printBoard() {
        blanks = 0; count_x = 0; count_o = 0;
        System.out.println("---------");

        for(int i = 0; i < 3; i++) {
            System.out.print("| ");
            for(int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
                if(board[i][j] == ' ') {
                    blanks++;
                } else if(board[i][j] == 'X') {
                    count_x++;
                } else if(board[i][j] == 'O') {
                    count_o++;
                }
            }
            System.out.print("|");
            System.out.println();
        }

        System.out.println("---------");
    }

    private static void moves() {
        boolean x_or_o = false;
        int col, row;
        int number_of_moves_x = 0, number_of_moves_o = 0;
        do {
            System.out.println("Enter the coordinates: ");
            if(scanner.hasNextInt()) {
                col = scanner.nextInt();
                row = scanner.nextInt();

                switch (row) {
                    case 3:
                        row = 1;
                        break;
                    case 1:
                        row = 3;
                        break;
                }

                if(row > 3 || col > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                } else {
                    if (board[row - 1][col - 1] == ' ') {
                        //System.out.println((row - 1) + " " + (col -1) + " " + board[col - 1][row - 1]);
                        if(x_or_o) {
                            board[row - 1][col - 1] = 'O';
                            x_or_o = !x_or_o;
                            number_of_moves_o++;
                        } else {
                            board[row - 1][col - 1] = 'X';
                            x_or_o = !x_or_o;
                            number_of_moves_x++;
                        }
                        printBoard();

                        if(number_of_moves_o >= 3 || number_of_moves_x >= 3) {
                            check();
                        }
                    } else {
                        System.out.println((row - 1) + " " + (col -1) + " " + board[col - 1][row - 1]);
                        System.out.println("This cell is occupied! Choose another one!");
                    }
                }
            } else {
                scanner.nextLine();
                System.out.println("You should enter numbers!");
            }
        } while (!win);

    }

    private static void check() {
        checkRow();
        checkColumn();
        checkDiagonal();

        if(blanks > 2 && (count_x > count_o + 1 || count_x + 1 < count_o)) {
            //System.out.println(GameStates.IMPOSSIBLE.value);
            return;
        } else if(blanks > 2) {
            //System.out.println(GameStates.NOT_FINISHED.value);
            return;
        } else if (blanks == 0) {
            System.out.println(GameStates.DRAW.value);
            win = true;
            System.exit(0);
        }
    }

    private static void checkRow() {
        boolean x = false, o = false;

        for(int r = 0 ; r < 3; r++) {
            if (board[r][0] == board[r][1] && board[r][1] == board[r][2]) {
                if (board[r][0] == 'X') {
                    x = true;
                } else if (board[r][0] == 'O') {
                    o = true;
                }
            }
        }

        checkWinCondition(x, o);
    }

    private static void checkColumn() {
        boolean x = false, o = false;

        for(int c = 0 ; c < 3; c++) {
            if (board[0][c] == board[1][c] && board[1][c] == board[2][c]) {
                if (board[0][c] == 'X') {
                    x = true;
                } else if (board[0][c] == 'O') {
                    o = true;
                }
            }
        }

        checkWinCondition(x, o);
    }

    private static void checkDiagonal() {
        boolean x = false, o = false;

        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == 'X') {
                x = true;
            } else if (board[0][0] == 'O') {
                o = true;
            }
        } else if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == 'X') {
                x = true;
            } else if (board[0][2] == 'O') {
                o = true;
            }
        }

        checkWinCondition(x, o);
    }

    private static void checkWinCondition(boolean x, boolean o) {
        if(x && o) {
            System.out.println(GameStates.IMPOSSIBLE.value);
            win = true;
            return;
        } else if(x) {
            System.out.println(GameStates.X_WINS.value);
            win = true;
            System.exit(0);
        } else if(o) {
            System.out.println(GameStates.O_WINS.value);
            win = true;
            System.exit(0);
        } else {
            return;
        }
    }

    enum GameStates {
        NOT_FINISHED("Game not finished"),
        DRAW("Draw"),
        X_WINS("X wins"),
        O_WINS("O wins"),
        IMPOSSIBLE("Impossible");

        private String value;
        GameStates(String i) {
            this.value = i;
        }
    }
}

/*
(1, 3) (2, 3) (3, 3)        00 01 02
(1, 2) (2, 2) (3, 2)        10 11 12
(1, 1) (2, 1) (3, 1)        20 21 22
 */