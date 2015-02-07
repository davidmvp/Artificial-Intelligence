import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * driver.
 * @author davidmvp23
 *
 */
public class driver {

    /**
     * @param args args.
     * @throws FileNotFoundException 
     */
    public static void main(String[] args) throws FileNotFoundException {

        Scanner kb = new Scanner(System.in);
        System.out.print("Enter input file name: "); 
        String file = kb.next();
        Scanner filescan = new Scanner(new FileReader(file));
        String str = filescan.nextLine();
        String[] dimension = str.split(" ");
        int width = 0;
        int height = 0;
        int counter = 0;
        String s = "";
        
        SuperGraph g = new SuperGraph(dimension[0],dimension[1]);
        while (filescan.hasNext()) {
            str = filescan.nextLine();
            width = 0;
            for (int i = 0; i < str.length();i++) {
                 s = str.charAt(i) + "";
                if (counter == 0) {
                    Vertex v = new Vertex(width,height);
                    v.sym(s);
                    g.addVertex(v);
                    counter++;
                }
                else if (height == 0) {
                    if (!s.equals("#")) {
                        Vertex v = new Vertex(width,height);
                        v.sym(s);
                        g.addVertex(v);
                        counter++;
                        Vertex v1 = g.findVertex(width-1,height);
                        if (!v1.returnSym().equals("#")) {
                            g.addNeighbors(v, v1);
                        }
                        if (s.equals("s")) {
                            g.setStart(v);
                        }
                        if (s.equals("g")) {
                            g.setEnd(v);
                        }
                        
                    }
                    
                    else {
                        Vertex v = new Vertex(width,height);
                        v.sym("#");
                        g.addVertex(v);
                        counter++;
                    }
                    
                }
                
                else if (width == 0) {
                    if (!s.equals("#")) {
                        Vertex v = new Vertex(width,height);
                        v.sym(s);
                        g.addVertex(v);
                        counter++;
                        Vertex v1 = g.findVertex(width,height-1);
                        if (!v1.returnSym().equals("#")) {
                            g.addNeighbors(v, v1);
                        }
                        if (s.equals("s")) {
                            g.setStart(v);
                        }
                        if (s.equals("g")) {
                            g.setEnd(v);
                        }
                        
                    }
                    
                    else {
                        Vertex v = new Vertex(width,height);
                        v.sym("#");
                        g.addVertex(v);
                        counter++;
                    }
                }
                else if (height != 0 && width != 0) {
                    if (!s.equals("#")) {
                        Vertex v = new Vertex(width,height);
                        v.sym(s);
                        g.addVertex(v);
                        counter++;
                        Vertex v1 = g.findVertex(width-1,height);
                        Vertex v2 = g.findVertex(width,height-1);
                        if (!v1.returnSym().equals("#")) {
                            g.addNeighbors(v, v1);
                        }
                        if (!v2.returnSym().equals("#")) {
                            g.addNeighbors(v, v2);
                        }
                        if (s.equals("s")) {
                            g.setStart(v);
                        }
                        if (s.equals("g")) {
                            g.setEnd(v);
                        }
                        
                    }
                    
                    else {
                        Vertex v = new Vertex(width,height);
                        v.sym("#");
                        g.addVertex(v);
                        counter++;
                    }
              
                }
                width++;
            }
            height++;
            }
       
            
  g.DepthFirstSearchHelper();
  g.breadthFirstHelper();
  g.AstarHelper();
   
}
}


