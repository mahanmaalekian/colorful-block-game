package block_game;

import java.awt.Color;

public class PerimeterGoal extends Goal{

	public PerimeterGoal(Color c) {
		super(c);
	}

	@Override
	public int score(Block board) {
	    Color[][] arr = board.flatten();
	    int score = 0;
	    //top row
	    for (int i = 0; i < arr[0].length; i++){
		if (arr[0][i] == targetGoal){
		    score++;
		}
	    }

	    //right column
	    for (int i = 0; i < arr.length; i++){
		if (arr[i][arr[i].length-1] == targetGoal){
		    score++;
		}
	    }

	    //bottom row
	    for (int i = 0; i < arr[0].length; i++){
		if (arr[arr.length-1][i] == targetGoal){
		    score++;
		}
	    }

	    //left column
	    for (int i = 0; i < arr.length; i++){
		if (arr[i][0] == targetGoal){
		    score++;
		}
	    }

		return score;
	}

	@Override
	public String description() {
		return "Place the highest number of " + GameColors.colorToString(targetGoal) 
		+ " unit cells along the outer perimeter of the board. Corner cell count twice toward the final score!";
	}

}
