package appewtc.masterung.cartoonbook;

import android.content.Context;
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

                String[] nameStrings = new String[jsonArray.length()];
                String[] descripStrings = new String[jsonArray.length()];
                String[] stocktrings = new String[jsonArray.length()];
                String[] pricetrings = new String[jsonArray.length()];
                String[] iconStrings = new String[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    nameStrings[i] = jsonObject.getString("Name");
                    descripStrings[i] = jsonObject.getString("Description");
                    stocktrings[i] = jsonObject.getString("Stock");
                    pricetrings[i] = jsonObject.getString("Price");
                    iconStrings[i] = jsonObject.getString("Cover");

                }   //for

                CartoonAdapter cartoonAdapter = new CartoonAdapter(context,
                        iconStrings, nameStrings, descripStrings, pricetrings, stocktrings);
                listView.setAdapter(cartoonAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        if (guestABoolean) {
                            //from User

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

}   // Main Class
