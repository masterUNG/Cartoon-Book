package appewtc.masterung.cartoonbook;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConfirmActivity extends AppCompatActivity {

    //Explicit
    private TextView nameTextView, surnameTextView,
            moneyTextView, dateTextView, grandTotalTextView;
    private ListView listView;
    private String[] loginStrings, productIDStrings, amountStrings,
            productNameStrings, productPriceStrings, totalStrings;
    private String dateString;
    private static final String urlJSON = "http://swiftcodingthai.com/gun/get_product_where_id.php";

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

        //Create ListView
        createListView();


    }   // Main Method

    private void createListView() {

        //Read from SQLite
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM orderTABLE", null);
        cursor.moveToFirst();

        productIDStrings = new String[cursor.getCount()];
        amountStrings = new String[cursor.getCount()];
        productNameStrings = new String[cursor.getCount()];
        productPriceStrings = new String[cursor.getCount()];
        totalStrings = new String[cursor.getCount()];

        for (int i=0;i<cursor.getCount();i++) {

            productIDStrings[i] = cursor.getString(cursor.getColumnIndex("ProductID"));
            Log.d("19JuneV2", "productID แรก ==> " + productIDStrings[i]);
            amountStrings[i] = cursor.getString(cursor.getColumnIndex("Amount"));
            productNameStrings[i] = myFindProduct(0, productIDStrings[i]);
            productPriceStrings[i] = myFindProduct(1, productIDStrings[i]);

            int intPrice = Integer.parseInt(productPriceStrings[i]);
            int intAmount = Integer.parseInt(amountStrings[i]);
            int intTotal = intPrice * intAmount;
            totalStrings[i] = Integer.toString(intTotal);

            cursor.moveToNext();
        }   // for


    }   // createListView

    private String myFindProduct(int intIndex, String productIDString) {

        String result = null;
        String[] columnStrings = new String[]{"Name", "Price"};
        String[] dummy = new String[]{"test", "100"};
        Log.d("19JuneV2", "productID ==> " + productIDString);

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("id", productIDString)
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(urlJSON).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String strResponse = response.body().string();
                Log.d("19JuneV2", "JSON ==> " + strResponse);
            }
        });

        return dummy[intIndex];
    }

    public void clickAddMore(View view) {
        finish();
    }

    public void clickOrder(View view) {

    }   // clickOrder

}   // Main Class
