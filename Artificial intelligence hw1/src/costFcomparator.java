import java.util.Comparator;


public class costFcomparator implements Comparator<Vertex> {

    @Override
    public int compare(Vertex o1, Vertex o2) {
        if (o1.getfCost() < o2.getfCost()) {
            return -1;
        }
        if (o1.getfCost() > o2.getfCost()) {
            return 1;
        }
        return 0;
    }

    
}
