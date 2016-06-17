package appewtc.masterung.cartoonbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by masterUNG on 6/17/2016 AD.
 */
public class MyManage {

    //Explicit
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase;

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
