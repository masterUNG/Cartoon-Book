package appewtc.masterung.cartoonbook;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private MyManage myManage;
    private static final String urlJSON = "http://swiftcodingthai.com/gun/get_user_master.php";
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;
    private String[] userLoginStrings = new String[8];
    private boolean statusLogin = false; // false ==> userFalse, true ==> haveUser

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        userEditText = (EditText) findViewById(R.id.editText7);
        passwordEditText = (EditText) findViewById(R.id.editText8);

        myManage = new MyManage(this);

        //Test Add User
//        myManage.addNewUser("1", "name", "sur", "add", "phone", "user",
//                "pass", "money");

        //Delete All SQLite
        deleteAllSQLite();

        //mySynchronizeJSON();

    }   // Main Method

    public void clickGuest(View view) {
        Intent intent = new Intent(MainActivity.this, CartoonActivity.class);
        intent.putExtra("Guest", false);
        startActivity(intent);
    }

    public void clickSignIn(View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //Check Space
        if (userString.equals("") || passwordString.equals("")) {

            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "มีช่องว่างคะ ?",
                    "กรุณากรอกทุกช่อง คะ");

        } else {
            checkUserAnPassword();
        }

    }   // clickSignIn

    private void checkUserAnPassword() {

        ConnectedUSER connectedUSER = new ConnectedUSER(this, urlJSON);
        connectedUSER.execute();


    }   // checkUserAnPass

    private void mySynchronizeJSON() {
        ConnectedUSER connectedUSER = new ConnectedUSER(MainActivity.this, urlJSON);
        connectedUSER.execute();
    }

    private class ConnectedUSER extends AsyncTask<Void, Void, String> {

        private Context context;
        private String myJSON;

        public ConnectedUSER(Context context, String myJSON) {
            this.context = context;
            this.myJSON = myJSON;
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(myJSON).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                return null;
            }
        }   // doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("17JuneV1", "JSON ==> " + s);
            Toast.makeText(context, "โหลดข้อมูล จาก Server เรียบร้อยแล้ว",
                    Toast.LENGTH_SHORT).show();

            try {

                JSONArray jsonArray = new JSONArray(s);
                for (int i=0;i<jsonArray.length();i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    if (userString.equals(jsonObject.getString("User"))) {

                        userLoginStrings[0] = jsonObject.getString("id");
                        userLoginStrings[1] = jsonObject.getString("Name");
                        userLoginStrings[2] = jsonObject.getString("Surname");
                        userLoginStrings[3] = jsonObject.getString("Address");
                        userLoginStrings[4] = jsonObject.getString("Phone");
                        userLoginStrings[5] = jsonObject.getString("User");
                        userLoginStrings[6] = jsonObject.getString("Password");
                        userLoginStrings[7] = jsonObject.getString("Money");
                        statusLogin = true;

                    }   // if

                }   //for

                for (int i=0;i<userLoginStrings.length;i++) {
                    Log.d("18Test", "userLogin (" + i + ") = " + userLoginStrings[i]);
                }
                Log.d("18Test", "statusLogin ==> " + statusLogin);

                if (statusLogin) {
                    //Check Password

                    if (passwordString.equals(userLoginStrings[6])) {
                        Toast.makeText(context, "Welcome " + userLoginStrings[1] + " " + userLoginStrings[2],
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(context, CartoonActivity.class);
                        intent.putExtra("Login", userLoginStrings);
                        startActivity(intent);
                        finish();

                    } else {
                        MyAlert myAlert = new MyAlert();
                        myAlert.myDialog(context, "Password False",
                                "Please Try Again Password False");
                    }

                } else {
                    MyAlert myAlert = new MyAlert();
                    myAlert.myDialog(context, "ไม่มี User นี้",
                            "ไม่มี " + userString + " ในฐานข้อมูล ของเรา");
                }



            } catch (Exception e) {
                e.printStackTrace();
            }

        }   // onPost

    }   // AsyncTask Class

    private void deleteAllSQLite() {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        sqLiteDatabase.delete(MyManage.user_table, null, null);
    }

    public void clickSignUpMain(View view) {
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }

}   // Main Cla

// ss
