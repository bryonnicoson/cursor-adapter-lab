package ly.generalassemb.drewmahrt.shoppinglistver2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView mTvItem;
    TextView mTvPrice;
    TextView mTvDetail;
    Intent mainIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTvItem = (TextView) findViewById(R.id.tv_item);
        mTvPrice = (TextView) findViewById(R.id.tv_price);
        mTvDetail = (TextView) findViewById(R.id.tv_detail);

        mainIntent = getIntent();

        mTvItem.setText(mainIntent.getStringExtra("ITEM"));
        mTvPrice.setText("$" + mainIntent.getDoubleExtra("PRICE", 0.00));
        mTvDetail.setText(mainIntent.getStringExtra("DETAIL"));
    }
}
