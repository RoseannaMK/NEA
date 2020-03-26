package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class TipsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        TextView textview= (TextView) findViewById(R.id.tips_textview);
        String para = "TIPS: " +
                "\n1. Encourage employees to walk to work or if this is not possible then encourage the use of public transport." +
                "\n2. Introduce more vegetarian or vegan lunch options in the workplace." +
                "\n3. Buy a compost bin for the office." +
                "\n4. Provide personal laptops rather than desktops for employees or encourage them to bring their own as laptops consume less energy." +
                "\n5. Change incandescent lightbulbs to LEDs." +
                "\n6. Encourage a carpool scheme to get to work." +
                "\n7. H"+
                "\n7. H"+
                "\n7. H"+
                "\n7. H"+
                "\n7. H"+
                "\n7. H"+
                "\n7. H"+
                "\n7. H"+
                "\n7. H"+
                "\n7. H"+
                "\n7. H"+
                "\n7. H"+
                "\n7. H"+
                "\n7. H";
        textview.setText(para);
        textview.setMovementMethod(new ScrollingMovementMethod());
    }
}
