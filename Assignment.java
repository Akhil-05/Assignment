public final class Assignment {

    // The value representing a dead cell
    public final static int DEAD    = 0x00;

    // The value representing a live cell
    public final static int LIVE    = 0x01;

    /** 
     * Start the Assignment example
     * 
     *
     */
    public final static void main(String[] args) {

        // test the Assignment implementation
        Assignment asg = new Assignment();
        asg.testDemo(5);
    }


    /**
     * Test the assignment implementation, change the array 
     * values to test each condition in the assignment
     *
     * @param nrItr      the number of times  box should be played
     */
    private void testDemo(int nrItr) {

        // the starting playing box with life and dead cells
        int[][] box = {{DEAD, DEAD, DEAD, DEAD, DEAD},
                         {DEAD, DEAD, DEAD, LIVE, DEAD},
                         {DEAD, DEAD, LIVE, LIVE, DEAD},
                         {DEAD, DEAD, DEAD, LIVE, DEAD},
                         {DEAD, DEAD, DEAD, DEAD, DEAD}}; 
        
        printBox(box);

        for (int i = 0 ; i < nrItr ; i++) {
            System.out.println();
            box = getNextBox(box);
            printBox(box);
        }
    }

    /** 
     * Print one box field to System.out
     * 
     * @param box The box to be printed to System.out
     */
    private void printBox(int[][] box) {

        for (int i = 0, e = box.length ; i < e ; i++) {

            for (int j = 0, f = box[i].length ; j < f ; j++) {
                System.out.print(Integer.toString(box[i][j]) + ",");
            } 
            System.out.println();
        }
    }

    /**
     * get the next game box, this will calculate if cells live on or die or new
     * ones should be created by reproduction.
     * 
     * @param b        The current box field
     * @return A newly created game buffer
     */
    public int[][] getNextBox(int[][] box) {

        // The box does not have any values so return the newly created
        // playing field.
        if (box.length == 0 || box[0].length == 0) {
            throw new IllegalArgumentException("Box must have  positive amount of rows or columns");
        }

        int nrRows = box.length;
        int nrCols = box[0].length;

        // temporary box to store new values
        int[][] temp = new int[nrRows][nrCols];

        for (int row = 0 ; row < nrRows ; row++) {
	
            for (int col = 0 ; col < nrCols ; col++) {
                temp[row][col] = getNewCellState(box[row][col], getLiveNeighbours(row, col, box));
            }
        }   
        return temp;
    }

    /**
     * Get the number of the live neighbours given the cell position
     * 
     * @param colPos       the column position of the cell
     * @param rowPos       the row position of the cell
     * @return the number of live neighbours given the position in the array
     */
    private int getLiveNeighbours(int colPos, int rowPos, int[][] box) {

        int liveNeighbours = 0;
        int rowEnd = Math.min(box.length , colPos + 2);
        int colEnd = Math.min(box.length, rowPos + 2);

        for (int row = Math.max(0, colPos - 1) ; row < rowEnd ; row++) {
            
            for (int col = Math.max(0, rowPos - 1) ; col < colEnd ; col++) {
                
                // make sure to exclude the cell itself from calculation
                if ((row != colPos || col != rowPos) && box[row][col] == LIVE) {
                    liveNeighbours++;
                }
            }
        }
        return liveNeighbours;
    }


    /** 
     * Get the new state of the cell given the current state and
     * the number of live neighbours of the cell.
     * 
     * @param curState          The current state of the cell, either DEAD or ALIVE
     * @param liveNeighbours    The number of live neighbours of the given cell.
     * 
     * @return The new state of the cell given the number of live neighbours 
     *         and the current state
     */
    private int getNewCellState(int curState, int liveNeighbours) {

        int newState = curState;

        switch (curState) {
        case LIVE:

            // Any live cell with fewer than two 
            // live neighbours dies
            if (liveNeighbours < 2) {
                newState = DEAD;
            }

            // Any live cell with two or three live   
            // neighbours lives on to the next generation.
            if (liveNeighbours == 2 || liveNeighbours == 3) {
                newState = LIVE;
            }

            // Any live cell with more than three live neighbours
            // dies, as if by overcrowding.
            if (liveNeighbours > 3) {
                newState = DEAD;
            }
            break;

        case DEAD:
            // Any dead cell with exactly three live neighbours becomes a 
            // live cell, as if by reproduction.
            if (liveNeighbours == 3) {
                newState = LIVE;
            }
            break;

        default:
            throw new IllegalArgumentException("State of cell must be either LIVE or DEAD");
        }			
        return newState;
    }
}