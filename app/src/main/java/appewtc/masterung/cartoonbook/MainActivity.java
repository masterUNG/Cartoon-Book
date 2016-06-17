package appewtc.masterung.cartoonbook;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        mySynchronizeJSON();

    }   // Main Method

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

            try {

                JSONArray jsonArray = new JSONArray(s);
                for (int i=0;i<jsonArray.length();i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String strId = jsonObject.getString("id");
                    String strName = jsonObject.getString("Name");
                    String strSurname = jsonObject.getString("Surname");
                    String strAddress = jsonObject.getString("Address");
                    String strPhone = jsonObject.getString("Phone");
                    String strUser = jsonObject.getString("User");
                    String strPassword = jsonObject.getString("Password");
                    String strMoney = jsonObject.getString("Money");
                    myManage.addNewUser(strId, strName, strSurname, strAddress,
                            strPhone, strUser, strPassword, strMoney);

                }   //for

                Toast.makeText(context, "โหลดข้อมูล จาก Server เรียบร้อยแล้ว",
                        Toast.LENGTH_SHORT).show();

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
