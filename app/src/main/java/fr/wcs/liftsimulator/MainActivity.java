package fr.wcs.liftsimulator;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int TIME_BETWEEN_FLOORS = 3000;
    private boolean isLiftMoving = false;
    private int currentFloor = 0;

    private void goToFloor(int floor) {
        if (!isLiftMoving && floor != currentFloor) {
            moveNextFloor(floor);
        }
    }

    private void moveNextFloor(int floor) {
        if (floor != currentFloor) {
            isLiftMoving = true;
            //waitForIt();
            new MoveLift().execute( floor );
        } else {
            isLiftMoving = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        ArrayList<View> allButtons = ( findViewById(R.id.button_container)).getTouchables();
        for(View v : allButtons) {
            v.setOnClickListener( btn ->  goToFloor(
                    Integer.valueOf( ((Button)v).getText().toString())));
        }
    }

    private class MoveLift extends AsyncTask<Integer,Void,Integer> {

        @Override
        protected void onPostExecute(Integer targetFloor) {
            super.onPostExecute( targetFloor );
            currentFloor = (targetFloor > currentFloor) ? currentFloor + 1 : currentFloor - 1;
            TextView floorCount = findViewById(R.id.floor_count);
            floorCount.setText(String.valueOf(currentFloor));
            moveNextFloor(targetFloor);
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            try {
                Thread.sleep( TIME_BETWEEN_FLOORS );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return integers[0];
        }
    }


}
