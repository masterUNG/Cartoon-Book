package appewtc.masterung.cartoonbook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class CartoonActivity extends AppCompatActivity {

    //Explicit
    private TextView nameTextView, moneyTextView;
    private ListView listView;
    private String[] loginStrings;
    private boolean bolGuest;
    private String urlJSON = "http://swiftcodingthai.com/gun/get_cartoon.php";
    private String[] nameStrings, descripStrings, stockStrings
            , priceStrings, iconStrings;
    private String amountString, productIDString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartoon);

        //Bind Widget
        nameTextView = (TextView) findViewById(R.id.textView9);
        moneyTextView = (TextView) findViewById(R.id.textView10);
        listView = (ListView) findViewById(R.id.listView);

        bolGuest = getIntent().getBooleanExtra("Guest", false); // false ==> Guest, true ==> User
        if (bolGuest) {
            loginStrings = getIntent().getStringArrayExtra("Login");
            nameTextView.setText("Welcome : " + loginStrings[1] + " " + loginStrings[2]);
            checkMoney();
        }

        createListCartoon();

    }   // Main Method

    private void createListCartoon() {
        ConnectedCartoon connectedCartoon = new ConnectedCartoon(this, urlJSON, bolGuest);
        connectedCartoon.execute();
    }

    private void checkMoney() {
        moneyTextView.setText("Money : " + loginStrings[7] + " แต้ม");
    }

    private class ConnectedCartoon extends AsyncTask<Void, Void, String> {

        //Explicit
        private Context context;
        private String urlJSONString;
        private boolean guestABoolean;


        public ConnectedCartoon(Context context,
                                String urlJSONString,
                                boolean guestABoolean) {
            this.context = context;
            this.urlJSONString = urlJSONString;
            this.guestABoolean = guestABoolean;
        }   // Constructor

        @Override
        protected String doInBackground(Void... voids) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlJSONString).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                return null;
            }

        }   // doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("18TestV2", "JSON ==> " + s);

            try {

                JSONArray jsonArray = new JSONArray(s);

                nameStrings = new String[jsonArray.length()];
                descripStrings = new String[jsonArray.length()];
                stockStrings = new String[jsonArray.length()];
                priceStrings = new String[jsonArray.length()];
                iconStrings = new String[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    nameStrings[i] = jsonObject.getString("Name");
                    descripStrings[i] = jsonObject.getString("Description");
                    stockStrings[i] = jsonObject.getString("Stock");
                    priceStrings[i] = jsonObject.getString("Price");
                    iconStrings[i] = jsonObject.getString("Cover");

                }   //for

                CartoonAdapter cartoonAdapter = new CartoonAdapter(context,
                        iconStrings, nameStrings, descripStrings, priceStrings, stockStrings);
                listView.setAdapter(cartoonAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        if (guestABoolean) {
                            //from User
                           confirmdialog((i + 1), nameStrings[i]);

                        } else {
                            //From Guest
                            MyAlert myAlert = new MyAlert();
                            myAlert.myDialog(context, "ยังไม่ได้ Login",
                                    "กรุณา Login ก่อน คะ ?");
                        }

                    }   // onItemClick
                });


            } catch (Exception e) {
                e.printStackTrace();
            }

        }   // onPost

    }   // Async Class

    private void confirmdialog(int ProductID, String nameProduce) {

        CharSequence[] charSequences = new CharSequence[]{"1 เล่ม", "2 เล่ม", "3 เล่ม", "4 เล่ม",
                "5 เล่ม", "6 เล่ม", "7 เล่ม", "8 เล่ม"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(nameProduce);
        builder.setSingleChoiceItems(charSequences, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                amountString = Integer.toString(i + 1);
                Log.d("18TestV3", "amountString ==> " + amountString);
                dialogInterface.dismiss();
            }   // onClick
        });
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();


    }   // confirmdialog

}   // Main Class
