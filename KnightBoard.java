public class KnightBoard {
  private int[][] board;

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
    return helper(startingRow,startingCol,1);
  }

  private boolean canMove(int row,int col,int[] moves) {
    return !(row+moves[0]<0 || row+moves[0]>=board.length || col+moves[1]<0 || col+moves[1]>=board[0].length);
  }

  private boolean helper(int row, int col,int move) {
    int[][] moves = {{1,2},{2,1},{1,-2},{-2,1},{-1,2},{2,-1},{-1,-2},{-2,-1}};
    //System.out.println(moves[2][1]);
    if (move >= board.length * board[0].length) {
      return true;
    }

    for (int i = 0;i < moves.length;i += 1) {
      if (canMove(row,col,moves[i])) {
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
}
