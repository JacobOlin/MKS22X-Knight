public class KnightBoard {
  private int[][] board;
  private int[][] moves = {{1,2},{2,1},{1,-2},{-2,1},{-1,2},{2,-1},{-1,-2},{-2,-1}};
  private int[][] moveBoard;

  public KnightBoard(int startingRows,int startingCols) {
    if (startingRows <= 0 || startingCols <= 0) {
      throw new IllegalArgumentException();
    }
    board = new int[startingRows][startingCols];
    for (int i = 0;i < board.length;i += 1) {
      for (int j = 0;j < board[i].length;j += 1) {
        board[i][j] = 0;
      }
    }

    makeBoard();
    /*for (int i = 0;i < moveBoard.length;i += 1) {
      for (int j = 0;j < moveBoard[i].length;j += 1) {
        System.out.print(moveBoard[i][j] + " ");
      }
      System.out.println();
    }*/
  }

  public String toString() {
    String ans = "";
    for (int i = 0;i < board.length;i += 1) {
      for (int j = 0;j < board[i].length;j += 1) {
        if (board[i][j] < 10) {
          ans += " ";
        }
        if (board[i][j] == 0) {
          ans += "_ ";
        }
        else {
          ans += board[i][j] + " ";
        }
      }
      ans += "\n";
    }
    return ans;
  }

  public boolean solve(int startingRow,int startingCol) {
    for (int i = 0;i < board.length;i += 1) {
      for (int j = 0;j < board[i].length;j += 1) {
        if (board[i][j] != 0) {
          throw new IllegalStateException();
        }
      }
    }
    if (startingRow < 0 || startingRow >= board.length || startingCol < 0 || startingCol >= board[0].length) {
      throw new IllegalArgumentException();
    }
    board[startingRow][startingCol] = 1;
    boolean ans = helper(startingRow,startingCol,1);
    if (!ans) {
      board[startingRow][startingCol] = 0;
    }
    return ans;
  }

  private boolean canMove(int row,int col,int[] moves) {
    return !(row+moves[0]<0 || row+moves[0]>=board.length || col+moves[1]<0 || col+moves[1]>=board[0].length) && board[row + moves[0]][col + moves[1]] == 0;
  }

  private boolean helper(int row, int col,int move) {
    //System.out.println(moves[2][1]);
    if (move >= board.length * board[0].length) {
      return true;
    }

    for (int i = 0;i < moves.length;i += 1) {
      if (canMove(row,col,moves[i])) {
        //System.out.println(toString());
        //System.out.println("R: " + row + " C: " + col + " i: " + i + " move: " + move);
        board[row + moves[i][0]][col + moves[i][1]] = move + 1;
        if (helper(row + moves[i][0],col + moves[i][1],move + 1)) {
          return true;
        }
        board[row + moves[i][0]][col + moves[i][1]] = 0;
      }
    }
    return false;
  }

  public int countSolutions(int startingRow, int startingCol) {
    for (int i = 0;i < board.length;i += 1) {
      for (int j = 0;j < board[i].length;j += 1) {
        if (board[i][j] != 0) {
          throw new IllegalStateException();
        }
      }
    }
    if (startingRow < 0 || startingCol < 0 || startingRow >= board.length || startingCol >= board[0].length) {
      throw new IllegalArgumentException();
    }
    if (board.length == 1 || board[0].length == 1) {
      return 0;
    }
    return countHelper(startingRow,startingCol,1,0);
  }

  private int countHelper(int row, int col, int move,int count) {
    //System.out.println(moves[2][1]);
    if (move >= board.length * board[0].length) {
      count += 1;
    }

    for (int i = 0;i < moves.length;i += 1) {
      if (canMove(row,col,moves[i])) {
        //System.out.println(toString());
        //System.out.println("R: " + row + " C: " + col + " i: " + i + " move: " + move);
        board[row + moves[i][0]][col + moves[i][1]] = move + 1;
        if (countHelper(row + moves[i][0],col + moves[i][1],move + 1,count) > 0) {
          count = countHelper(row + moves[i][0],col+moves[i][1],move+1,count);
        }
        board[row + moves[i][0]][col + moves[i][1]] = 0;
      }
    }
    return count;
  }

  private void makeBoard() {
    moveBoard = new int[board.length][board[0].length];
    if (moveBoard.length >= 4 && moveBoard[0].length >= 4) {
      for (int i = 0;i < moveBoard.length;i += 1) {
        for (int j = 0;j < moveBoard[i].length;j += 1) {
          if ((i == 0 || i+1 == moveBoard.length) && (j == 0 || j+1 == moveBoard[i].length)) {
            moveBoard[i][j] = 2;
          }
          else {
            if (((i==1 || i+2==moveBoard.length) && (j==0 || j+1==moveBoard[i].length)) || ((j==1 || j+2==moveBoard[i].length) && (i==0 || i+1==moveBoard.length))) {
              moveBoard[i][j] = 3;
            }
            else {
              if (i==0 || i+1==moveBoard.length || j==0 || j+1==moveBoard[i].length) {
                moveBoard[i][j] = 4;
              }
              else{
                if ((i==1 || i+2==moveBoard.length)&&(j==1 || j+2==board.length)) {
                  moveBoard[i][j] = 4;
                }
                else {
                  if (i == 1 || j == 1 || i+2 == board.length || j+2 == board[i].length) {
                    moveBoard[i][j] = 6;
                  }
                  else {
                    moveBoard[i][j] = 8;
                  }
                }
              }
            }
          }
        }
      }
    }
  }

}
