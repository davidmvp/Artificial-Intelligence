package konane;


/**
 * Board
 * <br>
 * Representation of a konane game board.
 * <br>
 * Defaults to a 4x4 board. 
 * <br>
 * 
 * @author James Gettinger
 *
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Board {
    protected static final int MIN_SIZE = 4;
    protected static final int MAX_SIZE = 8;
    /**
     * last move.
     */
    protected Move last = null;
    /**
     * tiles.
     */
    protected Tile[][] tiles;
    private int size;
    /**
     * keep track the number of moves.
     */
    private int moveNum = 1;
    private int numN = 0;
    /**
     * used to check the 
     */
    private int maxDepth = 0;
    /**
     * current dpeth.
     */
    private int dept = 0;
    /**
     * Creates a board with the inputed tiles.
     * 
     * @param size
     *            the size of the board
     * 
     */
    public Board(int size) {
        this.size = size;
        tiles = new Tile[size][size];

        if (size >= MIN_SIZE && size <= MAX_SIZE && size % 2 == 0) {
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    if ((x % 2 == 0 && y % 2 == 0)
                            || (x % 2 != 0 && y % 2 != 0)) {
                        tiles[x][y] = new Tile(Tile.BLACK, true);
                    } else {
                        tiles[x][y] = new Tile(Tile.WHITE, true);
                    }
                }
            }

        } else {
            System.out.println("Board size must be an even number between "
                    + MIN_SIZE + " and " + MAX_SIZE + " inclusively");
        }
    }

    /**
     * Returns a specific tile based on its x,y coordinates
     * 
     * @param x
     *            the x-coordinate
     * @param y
     *            the y-coordinate
     * @return tile
     */
    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }
    
    /**
     * Returns a string representation of the board
     * 
     * @return a string representation
     */
    public String toString() {
        String toret = " ";

        for (int x = 0; x < size; x++) {
            toret += "|" + x;

            if (x == size - 1) {
                toret += "|";
            }
        }
        toret += "\n";

        for (int y = 0; y < size; y++) {
            toret += y;
            for (int x = 0; x < size; x++) {
                toret += "|" + tiles[x][y];

                if (x == size - 1) {
                    toret += "|";
                }
            }

            toret += "\n";
        }

        return toret;
    }

    /**
     * checks to see if the board has an valid moves
     * 
     * @return true if the game is over
     */
    public boolean endCase(Player mover) {
        ArrayList<Move> allMoves = getMoves(mover.getColor(), moveNum);

        boolean retval = false;
        if (allMoves.size() == 0) {
            retval = true;
        }

        return retval;
    }

    /**
     * get all the possible moves after a defined number of moves are made
     * 
     *  @param color
     *            the player to move's color
     * @param moves
     *            the moves to make
     * @return the moves
     */
    public ArrayList<Move> getMovesAfter(boolean color, int moveNumber,
            ArrayList<Move> moves) {
        Move tempMove = last;
        int tempMoveNum = moveNum;
        
        Tile[][] temp = new Tile[size][size];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                temp[x][y] = new Tile(tiles[x][y].getColor(), tiles[x][y].isOccupied());
            }
        }

        resetOccupied();
        
        if(moves.size() > 0){
            last = moves.get(moves.size() -1);
        }
        
        boolean simColor = color;
        if(moves.size() % 2 != 0){
            simColor = !simColor;
        }
        
        for (int i = 0; i < moves.size(); i++) {
            makeMove(moves.get(i), (moveNumber - moves.size()) + i, simColor, true);
            simColor = !simColor;
        }
        


        ArrayList<Move> toret = getMoves(color, moveNumber);

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                tiles[x][y] = temp[x][y];
            }
        }

        last = tempMove;
        moveNum = tempMoveNum;
        restoreOccupied();
        
        return toret;
    }
    
    /**
     * gets all possible moves
     * 
     * @return the moves
     */
   
    public ArrayList<Move> getMoves(boolean color, int moveNumber) {
        ArrayList<Move> moves = new ArrayList<Move>();
        if (moveNumber <= 2) {
            for (int y2 = 0; y2 < size; y2++) {
                for (int x2 = 0; x2 < size; x2++) {
                    Move temp = new Move(-1, x2, -1, y2);
                    if (makeMove(temp, moveNumber, color, false)) {
                        moves.add(temp);
                    }
                }
            }
        } else {
            for (int y1 = 0; y1 < size; y1++) {
                for (int x1 = 0; x1 < size; x1++) {
                    for (int y2 = 0; y2 < size; y2++) {
                        for (int x2 = 0; x2 < size; x2++) {
                            Move temp = new Move(x1, x2, y1, y2);
                            if (makeMove(temp, moveNumber, color, false)) {
                                moves.add(temp);
                            }
                        }
                    }
                }
            }
        }

        return moves;
    }

    /**
     * Checks to see if a move is valid.
     * 
     * @param m
     *            the move to make
     * @param mover
     *            the player who is moving
     * @param make
     *            if true the move is made
     * @return true if valid
     */
    public boolean makeMove(Move m, int moveNumber, boolean color, boolean make) {
        int x1 = m.getX1();
        int x2 = m.getX2();
        int y1 = m.getY1();
        int y2 = m.getY2();

        boolean valid = true;
        boolean toret = false;
        boolean foundLanding = false;

        if (moveNumber <= 2) {
            if (y2 < size && y2 >= 0 && x2 < size && x2 >= 0
                    && tiles[x2][y2].getColor() == color) {
                if (moveNumber == 1) {
                    if (x2 == 0 && y2 == 0) {
                        toret = true;
                    } else if (x2 == 0 && y2 == size - 1) {
                        toret = true;
                    } else if (x2 == (size / 2) - 1 && y2 == (size / 2) - 1) {
                        toret = true;
                    } else if (x2 == (size / 2) && y2 == (size / 2) - 1) {
                        toret = true;
                    } else if (x2 == (size / 2) - 1 && y2 == (size / 2)) {
                        toret = true;
                    } else if (x2 == (size / 2) && y2 == (size / 2)) {
                        toret = true;
                    } else if (x2 == size - 1 && y2 == 0) {
                        toret = true;
                    } else if (x2 == size - 1 && y2 == size - 1) {
                        toret = true;
                    }
                } else {

                    int x = last.getX2();
                    int y = last.getY2();

                    if (x2 == x && y2 == y - 1) {
                        toret = true;
                    } else if (x2 == x - 1 && y2 == y) {
                        toret = true;
                    } else if (x2 == x + 1 && y2 == y) {
                        toret = true;
                    } else if (x2 == x && y2 == y + 1) {
                        toret = true;
                    }
                }
            }

            if (toret && make) {
                setOccupied(x2, y2, false);
            }
        } else {
            if (x1 < size && y1 < size && x2 < size && y2 < size && x1 >= 0
                    && y1 >= 0 && x2 >= 0 && y2 >= 0) {
                if (tiles[x1][y1].isOccupied()
                        && tiles[x1][y1].getColor() == color) {
                    if (x1 == x2) {
                        if (y2 < y1) {
                            for (int i = 1; i <= y1 - y2; i += 1) {
                                if (i % 2 == 0) {
                                    if (tiles[x1][y1 - i].isOccupied()) {
                                        valid = false;
                                        break;
                                    } else {
                                        foundLanding = true;
                                    }
                                } else {
                                    if (!tiles[x1][y1 - i].isOccupied()
                                            || tiles[x1][y1 - i].getColor() == color) {
                                        valid = false;
                                        break;
                                    }
                                }
                            }

                            if (valid && foundLanding && (y1 - y2) % 2 == 0) {
                                toret = true;

                                if (make) {
                                    for (int i = 1; i <= y1 - y2; i += 2) {
                                        setOccupied(x1, y1-i, false);
                                    }
                                }
                            }
                        } else if (y2 > y1) {
                            for (int i = 1; i <= y2 - y1; i += 1) {
                                if (i % 2 == 0) {
                                    if (tiles[x1][y1 + i].isOccupied()) {
                                        valid = false;
                                        break;
                                    } else {
                                        foundLanding = true;
                                    }
                                } else {
                                    if (!tiles[x1][y1 + i].isOccupied()
                                            || tiles[x1][y1 + i].getColor() == color) {
                                        valid = false;
                                        break;
                                    }
                                }
                            }

                            if (valid && foundLanding && (y2 - y1) % 2 == 0) {
                                toret = true;

                                if (make) {
                                    for (int i = 1; i <= y2 - y1; i += 2) {
                                        setOccupied(x1, y1+i, false);
                                    }
                                }
                            }
                        } else {
                            valid = false;
                        }
                    } else if (y1 == y2) {
                        if (x2 < x1) {
                            for (int i = 1; i <= x1 - x2; i += 1) {
                                if (i % 2 == 0) {
                                    if (tiles[x1 - i][y1].isOccupied()) {
                                        valid = false;
                                        break;
                                    } else {
                                        foundLanding = true;
                                    }
                                } else {
                                    if (!tiles[x1 - i][y1].isOccupied()
                                            || tiles[x1 - i][y1].getColor() == color) {
                                        valid = false;
                                        break;
                                    }
                                }
                            }

                            if (valid && foundLanding && (x1 - x2) % 2 == 0) {
                                toret = true;

                                if (make) {
                                    for (int i = 1; i <= x1 - x2; i += 2) {
                                        setOccupied(x1-i, y1, false);
                                    }
                                }
                            }
                        } else if (x2 > x1) {
                            for (int i = 1; i <= x2 - x1; i += 1) {
                                if (i % 2 == 0) {
                                    if (tiles[x1 + i][y1].isOccupied()) {
                                        valid = false;
                                        break;
                                    } else {
                                        foundLanding = true;
                                    }
                                } else {
                                    if (!tiles[x1 + i][y1].isOccupied()
                                            || tiles[x1 + i][y1].getColor() == color) {
                                        valid = false;
                                        break;
                                    }
                                }
                            }

                            if (valid && foundLanding && (x2 - x1) % 2 == 0) {
                                toret = true;

                                if (make) {
                                    for (int i = 1; i <= x2 - x1; i += 2) {
                                        setOccupied(x1+i, y1, false);
                                    }
                                }
                            }
                        } else {
                            valid = false;
                        }
                    }
                }
            }

            if (toret && make) {
                setOccupied(x1, y1, false);
                tiles[x2][y2].setColor(tiles[x1][y1].getColor());
                setOccupied(x2, y2, true);
                
            }
        }

        if (toret && make) {
            last = m;
            moveNum++;
        }

        return toret;
    }
    
    /**
     * Sets a square occupied
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @param occ occupied or not
     */
    protected void setOccupied(int x, int y, boolean occ){
        tiles[x][y].setOccupied(occ);
    }
    
    /**
     * Reset the occupied list
     */
    protected void resetOccupied(){
        return;
    }
    
    /**
     * Restore the occupied list
     */
    protected void restoreOccupied(){
        return;
    }
    
   /**
    * get the size.
    * @return size.
    */
    public int getSize() {
        return size;
    }
    /**
     * set the size of the board.
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }
    /**
     * get the number of moves
     * @return the number of moves.
     */
    public int getMoveNum() {
        return moveNum;
    }
    /**
     * This method is for the human player, so they can see the board and select their move.
     * @param b the board
     * @return the move human chooses to move.
     */
    public Move HumanPlayer(Board b) {
        System.out.println("Hi, please type in the correct value based on the move you want to make.");
        Scanner kb = new Scanner(System.in);
        System.out.println("Please type in x1");
        int x1 = Integer.parseInt(kb.next());
        System.out.println("Please type in y1");
        int y1 = Integer.parseInt(kb.next());
        System.out.println("Please type in x2");
        int x2 = Integer.parseInt(kb.next());
        System.out.println("Please type in y2");
        int y2 = Integer.parseInt(kb.next());
                  Move m = new Move(x1,x2,y1,y2);
         if (b.makeMove(m, b.getMoveNum(), b.currentPlayer(), false) == false) {
             System.out.println("Invalid move, please do it again");
             HumanPlayer(b);
         }
         return m;
    }
    /**
     * Find out the current player, black or white
     * @return true black or false white.
     */
    public boolean currentPlayer() {
        if (this.getMoveNum()%2 == 1){
            return true;
        }
        else 
            return false;
    }
    /**
     * number of nodes explored for a move.
     * @return number of nodes.
     */
    public int numNodes() {
        return numN;
    }
    /**
     * Maxmimum depth reached for a move.
     * @param i to see if it is the maximum
     * @return the maximum depth.
     */
    public int maxD(int i) {
        if (i > maxDepth) {
            maxDepth = i;
        }
        return maxDepth;
    }
    /**
     * Minimax agent
     * @param moves the moves two player made so far
     * @param depth the depth you want to explore.
     * @param boardSize size of the board.
     * @return the best move.
     */
    public Move minimax(ArrayList<Move> moves, int depth,int boardSize) {
        numN = 0;
        maxDepth = 0;
        dept = depth;
       Move bestMove = new Move(-1,-1,-1,-1);
       Move mov = new Move(-1,-1,-1,-1);
       int q = -100000;
      
       Board b = new Board(boardSize);
       for (int i = 0;i<moves.size();i++) {
           b.makeMove(moves.get(i), b.getMoveNum(), b.currentPlayer(), true);
       }
       ArrayList<Move> a = new ArrayList<Move>();
       
       a = b.getMoves(b.currentPlayer(), b.getMoveNum());
        
        
        for (int i = 0 ; i< a.size();i++) {
            
            mov = a.get(i);
            ArrayList<Move> u = new ArrayList<Move>();
            u = result(moves,mov);
            numN++;
            int e = minValue(u,depth-1,boardSize);
           
            if (e > q) {
     
                bestMove = mov;
                q = e;
               
                
            }
       
           
            }
         
          
  
      
        return bestMove;
    }
    /**
     * find out the maxValue for minimax.
     * @param moves 
     * @param depth
     * @param size
     * @return the maxValue.
     */
     public int maxValue(ArrayList<Move> moves,int depth,int size) {
         
       Board b = new Board(size);
         ArrayList<Move> a = new ArrayList<Move>();
         for (int i = 0; i < moves.size();i++) {
             b.makeMove(moves.get(i), b.getMoveNum(), b.currentPlayer(), true);
         }
                 a = b.getMoves(b.currentPlayer(), b.getMoveNum());
     
         if (a.size() == 0 || depth == 0) {
             maxD(dept-depth);

             return  gameState(a,true);
         }
         int v = -100000;
        
         for (int i = 0 ;i<a.size();i++) {
             Move mov = new Move(-1,-1,-1,-1);
             mov = a.get(i);
             ArrayList<Move> temp = new ArrayList<Move>();
             temp = result(moves,mov);
            
             numN++;
             int va = minValue(temp,depth-1,size);
             
             if (va > v) {
               
                 v = va;
             }
         
          
            
         }
        
       

         return v;
     }
     /**
      * find the minValue for  minimax method.
      * @param moves
      * @param depth
      * @param size
      * @return minValue.
      */
     public int minValue(ArrayList<Move> moves, int depth, int size) {
         
     
         ArrayList<Move> a = new ArrayList<Move>();
         Board b = new Board(size);
       
         for (int i = 0; i < moves.size();i++) {
             b.makeMove(moves.get(i), b.getMoveNum(), b.currentPlayer(), true);
         }
                 a = b.getMoves(b.currentPlayer(), b.getMoveNum());
       
             
             
         if (a.size() == 0 || depth == 0) {
             maxD(dept-depth);
             return gameState(a,false);
            
             
         }
         int v = 100000; 
        
         for (int i = 0 ;i<a.size();i++) {
             Move m = new Move(-1,-1,-1,-1);
             m = a.get(i);
             ArrayList<Move> temp = new ArrayList<Move>();
             temp = result(moves,m);
             numN++;
             int va = maxValue(temp,depth-1,size);
           
             if (va < v) {
                 v = va;
             }
      
          
             
         }
       
         return v;
     }
     
     /**
      * return the moves after make a move.
      * @param moves
      * @param m
      * @return all the moves we made so far.
      */
     public ArrayList<Move> result(ArrayList<Move> moves , Move m) {
         
         ArrayList<Move> a = new ArrayList<Move>();
         for (int i = 0 ; i< moves.size();i++) {
             a.add(moves.get(i));
         }
         a.add(m);
        
         return a;
     }
     
     /**
      * heuristic function
      * @param moves
      * @param minOrMax
      * @return the value
      */
     public int gameState(ArrayList<Move> moves, boolean minOrMax) {
      
         
         if (moves.size() == 0) {
             if (minOrMax == true) {
               
             return -1000;
             }
             else if (minOrMax == false)
          
                 return 1000;
             
             
         }
         
        return moves.size();
     }
     /**
      * alpha beta search(more efficient than minimax)
      * @param moves
      * @param depth
      * @param boardSize
      * @return the best move.
      */
     public Move ALpha_Beta_Search(ArrayList<Move> moves, int depth, int boardSize) {
         Move bestMove = new Move(-1,-1,-1,-1);
         Move mov = new Move(-1,-1,-1,-1);
         dept = depth;
         numN = 0;
         maxDepth = 0;
         int q = -100000;
        
         Board b = new Board(boardSize);
         for (int i = 0;i<moves.size();i++) {
             b.makeMove(moves.get(i), b.getMoveNum(), b.currentPlayer(), true);
         }
         ArrayList<Move> a = new ArrayList<Move>();
         
         a = b.getMoves(b.currentPlayer(), b.getMoveNum());
          
          
          for (int i = 0 ; i< a.size();i++) {
             
              mov = a.get(i);
              ArrayList<Move> u = new ArrayList<Move>();
              u = result(moves,mov);
              numN++;
              int e = minValue(u,depth-1,boardSize,-100000,100000);
             
              if (e > q) {
         
                  bestMove = mov;
                  q = e;
                 
                  
              }
             
             
              }
           
            
          
        
          return bestMove;
     }
     /**
      * find the maxValue of alpha and beta
      * @param moves
      * @param depth
      * @param size
      * @param alpha
      * @param beta
      * @return the maxValue
      */
     public int maxValue(ArrayList<Move> moves,int depth,int size, int alpha, int beta) {
         
         
         Board b = new Board(size);
         ArrayList<Move> a = new ArrayList<Move>();
         for (int i = 0; i < moves.size();i++) {
             b.makeMove(moves.get(i), b.getMoveNum(), b.currentPlayer(), true);
         }
                 a = b.getMoves(b.currentPlayer(), b.getMoveNum());
     
         if (a.size() == 0 || depth == 0) {
      
             maxD(dept-depth);
             return  gameState(a,true);
         }
         int v = -100000;
        
         for (int i = 0 ;i<a.size();i++) {
             Move mov = new Move(-1,-1,-1,-1);
             mov = a.get(i);
             ArrayList<Move> temp = new ArrayList<Move>();
             temp = result(moves,mov);
             numN++;
             int va = minValue(temp,depth-1,size,alpha,beta);
            
             if (va > v) {
                
                 v = va;
             }
         
             if (v >= beta) {
                 return v;
             }
             alpha = Math.max(alpha, v);
         }
        
       

         return v;
     }
     /**
      * Return the minValue for alpha and beta 
      * @param moves
      * @param depth
      * @param size
      * @param alpha
      * @param beta
      * @return the minValue.
      */
     public int minValue(ArrayList<Move> moves, int depth, int size,int alpha,int beta) {
    
     
         ArrayList<Move> a = new ArrayList<Move>();
         Board b = new Board(size);
       
         for (int i = 0; i < moves.size();i++) {
             b.makeMove(moves.get(i), b.getMoveNum(), b.currentPlayer(), true);
         }
                 a = b.getMoves(b.currentPlayer(), b.getMoveNum());
       
             
             
         if (a.size() == 0 || depth == 0) {
             maxD(dept-depth);
             return gameState(a,false);
            
             
         }
         int v = 100000; 
        
         for (int i = 0 ;i<a.size();i++) {
             Move m = new Move(-1,-1,-1,-1);
             m = a.get(i);
             ArrayList<Move> temp = new ArrayList<Move>();
             temp = result(moves,m);
             numN++;
             int va = maxValue(temp,depth-1,size,alpha,beta);
           
             if (va < v) {
                 v = va;
             }
             if ( v <= alpha) {
                 return v;
             }
             beta = Math.min(beta, v);
      
     }
        return v;
     
     
     
     
     }
}
    

