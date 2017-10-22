public class DisplacedTiles implements Heuristic {
    @Override
    public int calculate(EightPuzzle.BoardState boardState) {
        int h = 0;
        int[] arr = boardState.toArray();
        for (int i = 0; i < arr.length; i++) {
            if (i == arr.length - 1) {
                h = arr[i] == 0 ? h : h + 1;
            } else {
                h = arr[i] == i + 1 ? h : h + 1;
            }
        }
        return h;
    }
}
