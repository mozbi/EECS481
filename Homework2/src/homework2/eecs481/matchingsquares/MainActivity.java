package homework2.eecs481.matchingsquares;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public class MainActivity extends Activity {
	private String win;
	private ButtonListener buttonListener;
	private checkSquares handler;
	private Context context;
	private Drawable flipSide;
	private int [] [] squares;
	private int dimensions, matches, tries;
	private List<Drawable> squareIcons;
	private Square squareOne;
	private Square squareTwo;
	private TableLayout theGrid;	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
              
        handler = new checkSquares();
        loadsquareIcons();
        setContentView(R.layout.activity_main);
        flipSide =  getResources().getDrawable(R.drawable.question);
        buttonListener = new ButtonListener();
        theGrid = (TableLayout)findViewById(R.id.gridLayout);
        context  = theGrid.getContext();
       	newGame(4);
    }
    
    private void newGame(int dim) {
    	dimensions = dim;
    	squares = new int [dimensions] [dimensions];
    	TableRow tableRow = ((TableRow)findViewById(R.id.squareGrid));
    	
    	theGrid = new TableLayout(context);
    	tableRow.addView(theGrid);
    	
    	 for (int y = 0; y < dimensions; y++) {
    		 theGrid.addView(createRow(y));
          }
    	 
    	 loadsquares();
    	 
    	 tries=matches=0;
    	 win= "You have won the game!";
    	 ((TextView)findViewById(R.id.tries)).setText("tries: "+tries);
    	 ((TextView)findViewById(R.id.matches)).setText("Matches: "+matches);	
	}
    
    private void loadsquareIcons() {
    	squareIcons = new ArrayList<Drawable>();
    	
    	squareIcons.add(getResources().getDrawable(R.drawable.apple));
    	squareIcons.add(getResources().getDrawable(R.drawable.coffee));
    	squareIcons.add(getResources().getDrawable(R.drawable.dollar));
    	squareIcons.add(getResources().getDrawable(R.drawable.fire));
    	squareIcons.add(getResources().getDrawable(R.drawable.hamburger));
    	squareIcons.add(getResources().getDrawable(R.drawable.lock));
    	squareIcons.add(getResources().getDrawable(R.drawable.strawberry));
    	squareIcons.add(getResources().getDrawable(R.drawable.turkey));
	}

	private void loadsquares() {
	
	    	int numSquares = dimensions*dimensions;
	    	ArrayList<Integer> list = new ArrayList<Integer>();
	    	
	    	for(int i=0;i<numSquares;i++) {
	    		list.add(new Integer(i));
	    	}
	    	
	    	Random random = new Random();
    		
	    	int temp=0;
	    	
	    	/*
	    	 *  Each cell represents an index of the squareIcons ArrayList.
	    	 *  Only 2 cells share the same squareIcons index.
	    	 */
	    	for(int i=numSquares-1;i>=0;i--) {
	    		if(i>0) {
	    			temp = random.nextInt(i);
	    		}
	    		temp=list.remove(temp);
	    		// numSquares/2 because there are that many squareIcons
	    		// goes through squares from 3,3 to 0,0
	    		squares[i%dimensions][i/dimensions]=temp%(numSquares/2);
	    		temp = 0;
	    	}
	    }
	
    private TableRow createRow(int j) {
    	 TableRow row = new TableRow(context);
    	 row.setHorizontalGravity(Gravity.CENTER);
         
         for (int i = 0; i < dimensions; i++) {
		         row.addView(createStartingImage(i,j));
         }
         return row;
    }
    
    private View createStartingImage(int i, int j) {
    	Button button = new Button(context);
    	button.setBackground(flipSide);
    	//provides a unique id for each button
    	button.setId(10*i+j);
    	button.setOnClickListener(buttonListener);
    	return button;
    }
    
    class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			
				if(squareOne!=null && squareTwo != null) {
					return;
				}
				int id = view.getId();
				// /10 and %10 decodes id
				flipSquare((Button)view,id/10,id%10);
		}

		private void flipSquare(Button button,int i, int j) {
			button.setBackground(squareIcons.get(squares[i][j]));
			if(squareOne==null) {
				squareOne = new Square(button,i,j);
			}
			else { 
				if(squareOne.i == i && squareOne.j == j) {
					return; 
				}
					
				squareTwo = new Square(button,i,j);
				tries++;
				((TextView)findViewById(R.id.tries)).setText("tries: "+tries);
			
				/*
				 *  TimerTask and Timer used to allow flipped squares to show for 1sec
				 *  before performing checkSquares()
				 */
				TimerTask tt = new TimerTask() {
					@Override
					public void run() {
					    handler.sendEmptyMessage(0);
					}
				};
				    Timer t = new Timer(false);
			        t.schedule(tt, 1000);
			}		
		}	
    }
    
    class checkSquares extends Handler {
    	
    	@Override
    	public void handleMessage(Message msg) {
    		checksquares();	
    	}
    	 public void checksquares() { 
    		    if(squares[squareTwo.i][squareTwo.j] != squares[squareOne.i][squareOne.j]) {
    		    	squareTwo.button.setBackground(flipSide);
    				squareOne.button.setBackground(flipSide);
    			}
    			else {
    				squareOne.button.setVisibility(View.INVISIBLE);
    				squareTwo.button.setVisibility(View.INVISIBLE);
    				matches++;
    				((TextView)findViewById(R.id.matches)).setText("Matches: "+matches);
    				if(matches == (dimensions*dimensions)/2) {
    			        ((TextView)findViewById(R.id.win)).setText(win);
    				}	
    			}
    	    	squareOne=null;
    			squareTwo=null;   
    	 }
    }
}
