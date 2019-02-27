import java.util.ArrayList;

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
    boolean ans;
    if (board.length >=4 && board[0].length >= 4) {
      ans = optimized(startingRow,startingCol,1);
    }
    else {
      ans = helper(startingRow,startingCol,1);
    }
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

  private boolean optimized(int row, int col,int move) {
    ArrayList<node> a = new ArrayList<node>();
    for (int i = 0;i < moves.length;i += 1) {
      //System.out.println("Row: " + row + " r: " + moves[i][0] + " Col: " + col + " c: " + moves[i][1] + " " + canMove(row+moves[i][0],col+moves[i][1],moves[i]));
      if (canMove(row,col,moves[i])) {
        node n = new node(moves[i][0],moves[i][1],moveBoard[row+moves[i][0]][col+moves[i][1]]);
        a.add(n);
      }
    }
    //System.out.println(a);
    for (int i = 1;i < a.size();i += 1) {
      for (int j = 0;j < i;j += 1) {
        if (a.get(j).compareTo(a.get(i))) {
          a.add(i,a.remove(j));
        }
      }
    }
    //System.out.println(moves[2][1]);
    if (move >= board.length * board[0].length) {
      return true;
    }

    for (int i = 0;i < a.size();i += 1) {
      int[] q = {a.get(i).getUd(),a.get(i).getLr()};
      if (canMove(row,col,q)) {
        //System.out.println(toString());
        //System.out.println("R: " + row + " C: " + col + " i: " + i + " move: " + move);
        board[row + a.get(i).getUd()][col + a.get(i).getLr()] = move + 1;
        for (int k = 0;k < moves.length;k += 1) {
          if (canMove(row+a.get(i).getUd()+moves[k][0],col+a.get(i).getLr()+moves[k][1],moves[k])) {
            moveBoard[row+a.get(i).getUd()+moves[k][0]][col+a.get(i).getLr()+moves[k][1]] -= 1;
          }
        }
        if (optimized(row + a.get(i).getUd(),col + a.get(i).getLr(),move + 1)) {
          return true;
        }
        for (int k = 0;k < moves.length;k += 1) {
          if (canMove(row+a.get(i).getUd()+moves[k][0],col+a.get(i).getLr()+moves[k][1],moves[k])){
            moveBoard[row+a.get(i).getUd()+moves[k][0]][col+a.get(i).getLr()+moves[k][1]] += 1;
          }
        }
        board[row + a.get(i).getUd()][col + a.get(i).getLr()] = 0;
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
                if ((i==1 || i+2==moveBoard.length)&&(j==1 || j+2==moveBoard[i].length)) {
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
    else {
      if (moveBoard.length > 3) {
        for (int i = 0;i < moveBoard.length;i += 1) {
          for (int j = 0;j < moveBoard[i].length;j += 1) {
            if (i == 0 || i+1 == moveBoard.length) {
              moveBoard[i][j] = 2;
            }
          }
        }
      }
      else {
        for (int i = 0;i < board.length;i += 1) {
          for (int j = 0;j < board[i].length;j += 1) {
            board[i][j] = 0;
          }
        }
      }
    }
  }

  private class node{
    private int ud;
    private int lr;
    private int moves;

    public node(int up,int left, int move) {
      ud = up;
      lr = left;
      moves = move;
    }

    public int getLr() {
      return lr;
    }

    public int getUd() {
      return ud;
    }

    public int val() {
      return moves;
    }

    public boolean compareTo(node a) {
      return val() > a.val();
    }

    public String toString() {
      return moves + "";
    }
  }


}
