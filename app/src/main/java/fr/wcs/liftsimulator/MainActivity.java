package fr.wcs.liftsimulator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity {

    private boolean isLiftMoving = false;
    private int currentFloor = 0;

    private void goToFloor(int floor) {
        if (!isLiftMoving && floor != currentFloor) {
            moveNextFloor(floor);
            isLiftMoving = false;
        }
    }

    private void moveNextFloor(int floor) {
        if (floor != currentFloor) {
            isLiftMoving = true;
            waitForIt();
            currentFloor = (floor > currentFloor) ? currentFloor + 1 : currentFloor - 1;
            TextView floorCount = findViewById(R.id.floor_count);
            floorCount.setText(String.valueOf(currentFloor));
            moveNextFloor(floor);
        }
    }

    private void waitForIt() {
        try {
            Thread.sleep( 0 );
        } catch (InterruptedException e) {
            e.printStackTrace();
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
}
