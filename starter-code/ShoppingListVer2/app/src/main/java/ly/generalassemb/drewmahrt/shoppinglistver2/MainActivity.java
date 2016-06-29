package ly.generalassemb.drewmahrt.shoppinglistver2;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import ly.generalassemb.drewmahrt.shoppinglistver2.setup.DBAssetHelper;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase db;   // i feel like i should not need db declaration here - should all be in helper?
    ListView mGroceryList;
    Cursor mCursor;
    CursorAdapter mCAdapter;
    AdapterView.OnItemClickListener mItemClickListener;
    Intent mDetailIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ignore the two lines below, they are for setup
        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        dbHelper = new DBHelper(this);  // not providing as much help as should be at this point...

        db = openOrCreateDatabase("SHOPPING_DB", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        try {
            dbHelper.onUpgrade(db, 7, 8);
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "$#@%! "+e.toString(), Toast.LENGTH_SHORT).show();
        }

        seedDB();  // db opened, insert values, next list values to listview via cursor

        mGroceryList = (ListView)findViewById(R.id.shopping_list_view);  // attach local ref to view
        mCursor = db.query("grocery", null, null, null, null, null, null, null); // get data into cursor

        mCAdapter = new CursorAdapter(MainActivity.this, mCursor, 0) {  // define cursor data presentation?

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView txt = (TextView) view.findViewById(android.R.id.text1);
                String rowData = cursor.getString(cursor.getColumnIndex("item"));
                txt.setText(rowData);

            }
        };

        mGroceryList.setAdapter(mCAdapter); // *** don't forget to set the adapter to the view

        mItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDetailIntent = new Intent(MainActivity.this, DetailActivity.class);

                String item = mCursor.getString(mCursor.getColumnIndex("item"));
                Double price = mCursor.getDouble(mCursor.getColumnIndex("price"));
                String detail = mCursor.getString(mCursor.getColumnIndex("detail"));

                mDetailIntent.putExtra("ITEM", item);
                mDetailIntent.putExtra("PRICE", price);
                mDetailIntent.putExtra("DETAIL", detail);

                startActivity(mDetailIntent);
            }
        };

        mGroceryList.setOnItemClickListener(mItemClickListener);

    }

    public void seedDB(){  // this should be in the helper as well ?  and should have conditional (if !exists...)

        String item[] = {
                "Milk", "Honey", "Bread", "Butter", "Spice", "Weekly World News",
                "National Enquirer", "King Size Kit Kat", "Tic Tacs", "SPAM", "Eggs", "No Tea"
        };

        Double price[] = {
                2.99, 6.99, 3.49, 4.99, 5.49, 7.50, 7.50, 1.99, 1.49, 3.49, 2.79, 0.00
        };

        String detail[] = {
                "2% Dairy Fresh!  MOOO!", "From the land of Milk and Honey (and bees)",
                "Wonder bread!", "Land-O-Lakes Unsalted",
                "A randomly chosen spice from the spice rack in aisle 7 or the spice Melange, whichever is cheaper",
                "Travel back to 2007 to buy a copy of this tabloid (the spice Melange might help with this)",
                "Because enquiring minds want to know", "Because a king-sized candy bar is the best kind of impulse buy",
                "Who wants a candy/mint that looks and tastes like medicine?",
                "Hormel brings you SPiced hAM!  SPAM, SPAM, SPAM, and SPAM",
                "Ex-future chickens in convenient individual containers.  Good with SPAM",
                "You have no tea, and even if you drop it from your inventory, you cannot get rid of it"
        };

        for (int i = 0; i < item.length; i++) {
            String sql = "INSERT INTO grocery VALUES (null, '" + item[i] + "', " + price[i] + ", '" + detail[i] + "');";
            db.execSQL(sql);
        }
    }
}
