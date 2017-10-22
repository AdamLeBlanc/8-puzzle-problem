import java.lang.reflect.AccessibleObject;
import java.util.*;

public class AStar {
    private PriorityQueue<Node> frontire;
    private HashMap<EightPuzzle.BoardState, Node> visted;
    private EightPuzzle game;
    private int nodeCount;

    public AStar(int[] initial, Heuristic h) {
        frontire = new PriorityQueue<Node>(8, new Comparator<>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.f > o2.f ? 1 : -1;
            }
        });
        game = new EightPuzzle(initial, h);
        Node initialNode = new Node(game.initialState(), null, null);
        frontire.add(initialNode);
        visted = new HashMap<>();
    }


    public SearchResult search() {
        while (!frontire.isEmpty()) {
            Node current = frontire.poll();
            if (game.isGoal(current.state)) {
                return new SearchResult(current, nodeCount);
            }
            if (visted.containsKey(current.state)) {
                continue;
            }
            visted.put(current.state, current);
            for (EightPuzzle.Action a : game.possibleActions(current.state)) {
                if (a == current.undo)
                    continue; // Don't go backwards
                EightPuzzle.BoardState nBs = game.apply(current.state, a);
                nodeCount++;
                Node nNode = new Node(nBs, current, a.undo());
                frontire.add(nNode);
            }

        }
        return null;
    }

    private class Node {
        final int f, g;
        final EightPuzzle.BoardState state;
        final EightPuzzle.Action undo;
        final Node parent;

        Node(EightPuzzle.BoardState state, Node parent, EightPuzzle.Action undo) {
            this.state = state;
            this.parent = parent;
            g = parent != null ? parent.g + 1 : 0;
            f = game.heuristicOf(state) + g;
            this.undo = undo;
        }

    }

    public class SearchResult {
        private List<State> path;
        private int nodeCount;
        public SearchResult(Node goal, int nodeCount) {
            this.nodeCount = nodeCount;
            path = new ArrayList<>();
            createPath(goal, null);
        }

        public List<State> getPath() {
            return path;
        }

        public int getNodeCount() {
            return nodeCount;
        }

        public ArrayList<EightPuzzle.Action> getMoves(){
            ArrayList<EightPuzzle.Action> result = new ArrayList<>();
            for (State s : path){
                result.add(0, s.next);
            }
            return result;
        }

        private void createPath(Node n, EightPuzzle.Action next){
            path.add(new State(n, next));
            if (n.parent == null){
                return;
            }
            createPath(n.parent, n.undo.undo());
        }

        public class State {
            private Node n;
            private EightPuzzle.BoardState boardState;
            private EightPuzzle.Action next;

            public State(Node n, EightPuzzle.Action next) {
                this.n = n;
                this.boardState = n.state;
                this.next = next;
            }

            public Node getN() {
                return n;
            }

            public EightPuzzle.BoardState getBoardState() {
                return boardState;
            }

            public EightPuzzle.Action getNext() {
                return next;
            }
        }


    }
}
