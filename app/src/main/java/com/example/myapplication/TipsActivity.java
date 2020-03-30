package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TipsActivity extends AppCompatActivity {
    private Button backkk_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        TextView textview= (TextView) findViewById(R.id.tips_textview);
        TextView HyperLink;
        Spanned Text;
        String para = "TIPS: " +
                "\n1. Encourage employees to walk to work or if this is not possible then encourage the use of public transport." +
                "\n2. Introduce more vegetarian or vegan lunch options in the workplace." +
                "\n3. Buy a compost bin for the office." +
                "\n4. Provide personal laptops rather than desktops for employees or encourage them to bring their own as laptops consume less energy." +
                "\n5. Change incandescent lightbulbs to LEDs." +
                "\n6. Encourage a carpool scheme to get to work." +
                "\n7. Use recycled rainwater to water plants."+
                "\n8. Introduce a rota for checking all lights and electronics are turned off at the end of each shift."+
                "\n9. Use reusable mugs, silverware, bowls and plates etc."+
                "\n10. Provide organic tea and coffee."+
                "\n11.Replace electronics with Energy Star appliances."+
                "\n12.For overseas meetings, host them virtually where possible."+
                "\n13.Encourage houseplants within the office."+
                "\n14.Introduce Meatless Mondays"+
                "\n15.If possible, install solar panels on the office building"+
                "\n16.Where possible share documents digitally rather than printing"+
                "\n";
        textview.setText(para);
        textview.setMovementMethod(new ScrollingMovementMethod());
        backkk_button = findViewById(R.id.back_button);
        HyperLink =(TextView)findViewById(R.id.hyperlink);

        Text = Html.fromHtml("Click on this link to view legislation <br />" +
                "<a href='https://assets.publishing.service.gov.uk/government/uploads/system/uploads/attachment_data/file/291208/scho1209brpv-e-e.pdf'>Gov Legislation for SMEs</a>");
        HyperLink.setMovementMethod(LinkMovementMethod.getInstance());
        HyperLink.setText(Text);

        backkk_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( TipsActivity.this,
                        HomePage.class));
            }
        });

    }
}
