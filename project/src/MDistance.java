public class MDistance implements Heuristic {
    // Inspired by the IBM dev blog + stack overflow to get the -1 bit
    @Override
    public int calculate(EightPuzzle.BoardState boardState) {
        int sum = 0;
        int[] board = boardState.toArray();
        for (int i = 0; i < board.length; i++) { // FIXME: Don't hard code it :(
            if (i == boardState.empty){
                continue;
            }
            int row = i / 3, col = i % 3;
            int grow = (board[i] - 1) / 3, gcol = (board[i] - 1) % 3;
            sum += Math.abs(gcol - col) + Math.abs(grow - row);
        }
        return  sum;
    }
}
