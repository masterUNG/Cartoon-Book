package appewtc.masterung.cartoonbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by masterUNG on 6/17/2016 AD.
 */
public class MyManage {

    //Explicit
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase;
    private String[] resultStrings;

    public static final String user_table = "userTABLE";
    public static final String column_id = "_id";
    public static final String column_Name = "Name";
    public static final String column_Surname = "Surname";
    public static final String column_Address = "Address";
    public static final String column_Phone = "Phone";
    public static final String column_User = "User";
    public static final String column_Password = "Password";
    public static final String column_Money = "Money";

    public MyManage(Context context) {

        myOpenHelper = new MyOpenHelper(context);
        writeSqLiteDatabase = myOpenHelper.getWritableDatabase();
        readSqLiteDatabase = myOpenHelper.getReadableDatabase();

    }   // Constructor

    public String[] searchUser(String strUser) {

        try {

            resultStrings = null;
            Cursor cursor = readSqLiteDatabase.query(user_table,
                    new String[]{column_id, column_Name,
                            column_Surname, column_Address,
                            column_Phone, column_User,
                            column_Password, column_Money},
                    column_User + "=?",
                    new String[]{String.valueOf(strUser)},
                    null, null, null, null);



            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    for (int i=0;i<cursor.getColumnCount();i++) {
                        resultStrings = new String[cursor.getColumnCount()];
                        resultStrings[i] = cursor.getString(i);
                        Log.d("18June", "resultStrings " + i + " ==> " + resultStrings[i]);
                    }   // for
                }   //if
            }   // if

            //cursor.close();

            Log.d("18JuneV2", "resultStrings lenght ==> " + resultStrings.length);
            for (int i=0;i<resultStrings.length;i++) {
                Log.d("18JuneV2", "result " + i + " ==>  " + resultStrings[i]);
            }

            return resultStrings;

        } catch (Exception e) {
            Log.d("18June", "e ==> " + e.toString());
            return null;
        }

    }

    public long addNewUser(String strId,
                           String strName,
                           String strSurname,
                           String strAddress,
                           String strPhone,
                           String strUser,
                           String strPassword,
                           String strMoney) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(column_id, strId);
        contentValues.put(column_Name, strName);
        contentValues.put(column_Surname, strSurname);
        contentValues.put(column_Address, strAddress);
        contentValues.put(column_Phone, strPhone);
        contentValues.put(column_User, strUser);
        contentValues.put(column_Password, strPassword);
        contentValues.put(column_Money, strMoney);

        return writeSqLiteDatabase.insert(user_table, null, contentValues);
    }

}   // Main Class
