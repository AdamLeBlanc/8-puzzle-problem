import java.util.ArrayList;
import java.util.Random;

public class ProblemGen {
    private int startDepth, maxDepth, trialsPerDepth;

    public ProblemGen(int startDepth, int maxDepth, int trialsPerDepth) {
        this.startDepth = startDepth;
        this.maxDepth = maxDepth;
        this.trialsPerDepth = trialsPerDepth;
    }

    public ArrayList<AStar.SearchResult>[] runTrials(Heuristic heuristic) {
 //        ArrayList<AStar.SearchResult>[] resultsList = new ArrayList(trialsPerDepth * (maxDepth - startDepth));
        ArrayList<AStar.SearchResult>[] resultsList = new ArrayList[maxDepth - startDepth];
        for (int i = startDepth; i < maxDepth; i++) {
            resultsList[i - startDepth] = new ArrayList<>(trialsPerDepth);
            for (int j = 0; j < trialsPerDepth; j++) {
                AStar.SearchResult r = createProblem(heuristic, i).search();
                resultsList[i - startDepth].add(r);
            }
        }
        return resultsList;
    }

    private AStar createProblem(Heuristic heuristic, int depth) {
        int[] init = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0};
        EightPuzzle puzzle = new EightPuzzle(init, heuristic);
        EightPuzzle.BoardState bs = puzzle.initialState();
        ArrayList<EightPuzzle.BoardState> stateList = new ArrayList<>(depth);
        for (int i = 0; i < depth * 2 ; i++) {
            ArrayList<EightPuzzle.Action> actions = (ArrayList) puzzle.possibleActions(bs);
            int indexAction = new Random().nextInt(actions.size());
            EightPuzzle.BoardState next = puzzle.apply(bs, actions.get(indexAction));
            if(stateList.contains(next)){
                i = i -1; // been there already...
                continue;
            }
            bs = next;
        }
        return new AStar(bs.toArray(), heuristic);
    }

}
