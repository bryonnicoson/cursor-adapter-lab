package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bryon on 6/28/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "SHOPPING_DB";
    private static final int DB_VERSION = 7;


    private final static String GROCERY_TABLE = "grocery";
    private final static String GROCERY_ID = "_id";
    private final static String GROCERY_ITEM = "item";
    private final static String GROCERY_PRICE = "price";
    private final static String GROCERY_DETAIL = "detail";
    private final static String[] GROCERY_COLS = {GROCERY_ID, GROCERY_ITEM, GROCERY_PRICE, GROCERY_DETAIL};

    private static final String TABLE_GROCERY_CREATE = "CREATE TABLE IF NOT EXISTS " + GROCERY_TABLE + "(" +
            GROCERY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            GROCERY_ITEM + " TEXT NOT NULL, " +
            GROCERY_PRICE + " REAL, " +
            GROCERY_DETAIL + " TEXT" +
            ");";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(TABLE_GROCERY_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GROCERY_TABLE + ";");
        onCreate(db);
    }


}
