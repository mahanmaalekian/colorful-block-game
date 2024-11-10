package block_game;

import java.awt.Color;

public class BlobGoal extends Goal{

	public BlobGoal(Color c) {
		super(c);
	}

	@Override
	public int score(Block board) {
		/*
		 * ADD YOUR CODE HERE
		 */
	    int max = 0;
	    int tmp;
	    Color[][] unitCells = board.flatten();
	    boolean[][] visited = new boolean[unitCells.length][unitCells.length];

	    for (int i = 0; i < unitCells.length; i++){
		for (int j = 0; j < unitCells.length; j++){
		    visited[i][j] = false;
		}
	    }

	    for (int i = 0; i < unitCells.length; i++){
		for (int j = 0; j < unitCells[0].length; j++){
		    tmp = undiscoveredBlobSize(i,j,unitCells,visited);
		    if (tmp > max){
			max = tmp;
		    }
		}
	    }

		return max;
	}

	@Override
	public String description() {
		return "Create the largest connected blob of " + GameColors.colorToString(targetGoal) 
		+ " blocks, anywhere within the block";
	}


	public int undiscoveredBlobSize(int i, int j, Color[][] unitCells, boolean[][] visited) {
		/*
		 * ADD YOUR CODE HERE
		 */
	    if (i >= unitCells.length || i < 0 || j >= unitCells.length || j <0){
		return 0;
	    }
	    if (unitCells[i][j] != targetGoal || visited[i][j]) return 0;


	    visited[i][j] = true;
	    return 1 + undiscoveredBlobSize(i, j+1, unitCells, visited) + undiscoveredBlobSize(i+1, j, unitCells, visited) +
		    undiscoveredBlobSize(i-1, j, unitCells, visited) + undiscoveredBlobSize(i,j-1,unitCells,visited);
	}

}
