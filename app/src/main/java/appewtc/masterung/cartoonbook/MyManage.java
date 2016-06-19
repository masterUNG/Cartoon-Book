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
    private SQLiteDatabase writeSqLiteDatabase;

    public static final String order_table = "orderTABLE";
    public static final String column_id = "_id";
    public static final String column_product_id = "ProductID";
    public static final String column_amount = "Amount";
    public static final String column_name = "Name";
    public static final String column_price = "Price";
    public static final String column_stock = "Stock";
    public static final String product_table = "productTABLE";

    public MyManage(Context context) {

        myOpenHelper = new MyOpenHelper(context);
        writeSqLiteDatabase = myOpenHelper.getWritableDatabase();

    }   // Constructor

    public long addProduct(String strID,
                           String strName,
                           String strPrice,
                           String strStock) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(column_id, strID);
        contentValues.put(column_name, strName);
        contentValues.put(column_price, strPrice);
        contentValues.put(column_stock, strStock);

        return writeSqLiteDatabase.insert(product_table, null, contentValues);
    }


    public long addOrder(String strProductID,
                         String strAmount) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(column_product_id, strProductID);
        contentValues.put(column_amount, strAmount);

        return writeSqLiteDatabase.insert(order_table, null, contentValues);
    }


}   // Main Class
