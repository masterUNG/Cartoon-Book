package appewtc.masterung.cartoonbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConfirmActivity extends AppCompatActivity {

    //Explicit
    private TextView nameTextView, surnameTextView,
            moneyTextView, dateTextView, grandTotalTextView;
    private ListView listView;
    private String[] loginStrings;
    private String dateString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        //Bind Widget
        nameTextView = (TextView) findViewById(R.id.textView20);
        surnameTextView = (TextView) findViewById(R.id.textView21);
        moneyTextView = (TextView) findViewById(R.id.textView22);
        dateTextView = (TextView) findViewById(R.id.textView23);
        grandTotalTextView = (TextView) findViewById(R.id.textView28);
        listView = (ListView) findViewById(R.id.listView2);

        //Current Date
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yy");
        Date date = new Date();
        dateString = dateFormat.format(date);

        //Show View
        loginStrings = getIntent().getStringArrayExtra("Login");
        nameTextView.setText(loginStrings[1]);
        surnameTextView.setText(loginStrings[2]);
        moneyTextView.setText(loginStrings[7]);
        dateTextView.setText(dateString);




    }   // Main Method

    public void clickAddMore(View view) {
        finish();
    }

    public void clickOrder(View view) {

    }   // clickOrder

}   // Main Class
