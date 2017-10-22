import java.util.ArrayList;

public class Tester {
    public static void main(String[] args) {
        ProblemGen pg = new ProblemGen(2, 25, 100);
        ArrayList<AStar.SearchResult>[] rDisplaced = pg.runTrials(new DisplacedTiles());
        ArrayList<AStar.SearchResult>[] rMDistance = pg.runTrials(new MDistance());
        System.out.printf("%-30.30s %-30.30s  %-30.30s%n", "Depth", "Node: Displaced", "Node: MDistance");
        for (int i = 0; i < 25 - 2; i++) {
            int dNodes = 0;
            int mNodes = 0;
            for (int j = 0; j < 100; j++) {
                dNodes += rDisplaced[i].get(j).getNodeCount();
                mNodes += rMDistance[i].get(j).getNodeCount();
            }
            System.out.printf("%-30.30s %-30.30s  %-30.30s%n", (i + 2), (dNodes / 100), (mNodes / 100));
        }

    }
}
