package appewtc.masterung.cartoonbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by masterUNG on 6/19/2016 AD.
 */
public class ConfirmAdapter extends BaseAdapter {

    //Explicit
    private Context context;
    private String[] productStrings, priceStrings,
            amountStrings, totalStrings;

    public ConfirmAdapter(Context context,
                          String[] productStrings,
                          String[] priceStrings,
                          String[] amountStrings,
                          String[] totalStrings) {
        this.context = context;
        this.productStrings = productStrings;
        this.priceStrings = priceStrings;
        this.amountStrings = amountStrings;
        this.totalStrings = totalStrings;
    }   // Constructor

    @Override
    public int getCount() {
        return productStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.confirm_listview, viewGroup, false);

        //Bind
        TextView productTextView = (TextView) view1.findViewById(R.id.textView29);
        TextView priceTextView = (TextView) view1.findViewById(R.id.textView30);
        TextView amountTextView = (TextView) view1.findViewById(R.id.textView31);
        TextView totalTextView = (TextView) view1.findViewById(R.id.textView32);

        productTextView.setText(productStrings[i]);
        priceTextView.setText(priceStrings[i]);
        amountTextView.setText(amountStrings[i]);
        totalTextView.setText(totalStrings[i]);

        return view1;
    }
}   // Main Class
