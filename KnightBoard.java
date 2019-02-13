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

  public boolean helper(int row, int col,int move) {
    return true;
  }
}
