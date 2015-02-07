package konane;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The driver for my konane program.
 * @author davidmvp23
 *
 */
public class Driver {
    public static void main(String args[]) {
       
       
        Scanner kb = new Scanner(System.in);
        ArrayList<Move> pre;
        boolean valid = true;
       
    
        System.out.println("Welcome to Konane!!");
        boolean keepgoing = true;
        
            while (keepgoing) {
                int totalForBlack = 0;
                int totalForWhite = 0;
                valid = true;
                ArrayList<Move> a = new ArrayList<Move>();
                pre = new ArrayList<Move>();
                System.out.println("Before we start the game, please choose the size of the board");
                int bSize = kb.nextInt();
                Board b = new Board(bSize);
                System.out.println("Please select who you want the Black(first) player to be? a: human,b: minimax, c: alpha_beta");
                String firstPlayer = kb.next();
                System.out.println("Please select who you want the White(second)  player to be? a: human,b: minimax, c: alpha_beta");
                String secondPlayer = kb.next();
                System.out.println("If you have selected b or c, please type in the depth, if not, type in any number.");
                int depth = kb.nextInt();
                
                while (valid) {
                Move m = new Move(-1,-1,-1,-1);
                if ( (b.getMoveNum()%2) == 1) {
                    System.out.println("It is Black's turn.");
                    System.out.println(b);
                    a = b.getMoves(b.currentPlayer(), b.getMoveNum());
                   
                    if (a.size()==0) {
                        valid = false;
                        System.out.println("White wins!!");
                        System.out.println("Total nodes explored for Black: " + totalForBlack);
                        System.out.println("Total nodes explored for White: " + totalForWhite);
                        totalForBlack = 0;
                        totalForWhite = 0;
                        break;
                    }
                    
                    if (firstPlayer.equals("a")) {
                        
                      m = b.HumanPlayer(b);  
                    }
                    else {
                        Board s1 = new Board(b.getSize());
                        for (int i = 0 ; i< pre.size();i++) {
                            s1.makeMove(pre.get(i), s1.getMoveNum(), s1.currentPlayer(), true);
                           
                        }
                        if (firstPlayer.equals("b")) {
                            final long startTime = System.currentTimeMillis();
                            m = b.minimax(pre, depth, bSize);
                            
                            final long endTime = System.currentTimeMillis();
                            System.out.println("Time it takes " + (endTime - startTime));
                            int numN = b.numNodes();
                            totalForBlack = totalForBlack  + numN;
                            System.out.println("Number of nodes explored :" + numN);
                            int maxDepth = b.maxD(-100);
                            System.out.println("Maximum search depth reached :" + maxDepth);
                        }
                      
                        else if (firstPlayer.equals("c")) {
                            final long startTime = System.currentTimeMillis();
                            m = b.ALpha_Beta_Search(pre, depth, bSize)  ; 
                            final long endTime = System.currentTimeMillis();
                            System.out.println("Time it takes " + (endTime - startTime));
                            int numN = b.numNodes();
                            totalForBlack = totalForBlack  + numN;
                            System.out.println("Number of nodes explored :" + numN);
                            int maxDepth = b.maxD(-100);
                            System.out.println("Maximum search depth reached :" + maxDepth);
                        }
                     
                        if (b.makeMove(m, b.getMoveNum(), b.currentPlayer(), false) == false) {
                         System.out.println("Invalid move!");
                         }
                   }   
                     b.makeMove(m, b.getMoveNum(), b.currentPlayer(), true);
                     pre.add(m);
                     System.out.println("Black goes from (" + m.getX1() + ", " + m.getY1() + ") to (" +  m.getX2() + ", " + m.getY2() + ")");
                     System.out.println();
                     System.out.println();
                     System.out.println();
                     
                }
                else {
                    System.out.println("It is White's turn.");
                    System.out.println(b);
                    a = b.getMoves(b.currentPlayer(), b.getMoveNum());
                      
                        if (a.size()==0) {
                            valid = false;
                            System.out.println("Black wins!!");
                            System.out.println("Total nodes explored for Black: " + totalForBlack);
                            System.out.println("Total nodes explored for White: " + totalForWhite);
                            totalForBlack = 0;
                            totalForWhite = 0;
                            break;
                        }
                        
                        if (secondPlayer.equals("a")) {
                          m = b.HumanPlayer(b);  
                        }
                        else {
                           
                            Board s1 = new Board(b.getSize());
                            for (int i = 0 ; i< pre.size();i++) {
                                s1.makeMove(pre.get(i), s1.getMoveNum(), s1.currentPlayer(), true);
                               
                            }
                            if (secondPlayer.equals("b")) {
                                final long startTime = System.currentTimeMillis();
                                m = b.minimax(pre, depth, bSize);
                                final long endTime = System.currentTimeMillis();
                                
                                System.out.println("Time it takes " + (endTime - startTime));
                                int numN = b.numNodes();
                                totalForWhite = totalForWhite + numN;
                                System.out.println("Number of nodes explored :" + numN);
                                int maxDepth = b.maxD(-100);
                                System.out.println("Maximum search depth reached :" + maxDepth);
                            }
                          
                            else if (secondPlayer.equals("c")) {
                                final long startTime = System.currentTimeMillis();
                                m = b.ALpha_Beta_Search(pre, depth, bSize);  
                                final long endTime = System.currentTimeMillis();
                                System.out.println("Time it takes " + (endTime - startTime));
                                int numN = b.numNodes();
                                totalForWhite = totalForWhite + numN;
                                System.out.println("Number of nodes explored :" + numN);
                                int maxDepth = b.maxD(-100);
                                System.out.println("Maximum search depth reached :" + maxDepth);
                            }
                         
                         if (b.makeMove(m, b.getMoveNum(), b.currentPlayer(), false) == false) {
                             System.out.println("Invalid move!");
                         }
                        }    
                         b.makeMove(m, b.getMoveNum(), b.currentPlayer(), true);
                         pre.add(m);
                         System.out.println("White goes from (" + m.getX1() + ", " + m.getY1() + ") to (" +  m.getX2() + ", " + m.getY2() + ")");
                         
                         System.out.println();
                         System.out.println();
                         System.out.println();
                    
                }
            }
                
                System.out.println("Do you wish to play another game? y/n");
                String choice = kb.next();
                if (choice.equals("n")) {
                    keepgoing = false;
                }
                else {
                    keepgoing = true;
                }
        }
            
    }
    

    
}
                     
               
