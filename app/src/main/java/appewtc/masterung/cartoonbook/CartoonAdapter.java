package appewtc.masterung.cartoonbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by masterUNG on 6/18/2016 AD.
 */
public class CartoonAdapter extends BaseAdapter {

    //Explicit
    private Context context;
    private String[] iconStrings, nameStrings, descripStrings,
            priceStrings, stockStrings;

    public CartoonAdapter(Context context,
                          String[] iconStrings,
                          String[] nameStrings,
                          String[] descripStrings,
                          String[] priceStrings,
                          String[] stockStrings) {
        this.context = context;
        this.iconStrings = iconStrings;
        this.nameStrings = nameStrings;
        this.descripStrings = descripStrings;
        this.priceStrings = priceStrings;
        this.stockStrings = stockStrings;
    }   // Constructor


    @Override
    public int getCount() {
        return iconStrings.length;
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
        View view1 = layoutInflater.inflate(R.layout.cartoon_listview, viewGroup, false);

        ImageView imageView = (ImageView) view1.findViewById(R.id.imageView2);
        Picasso.with(context).load(iconStrings[i]).resize(150, 180).into(imageView);

        TextView nameTextView = (TextView) view1.findViewById(R.id.textView11);
        TextView descripTextView = (TextView) view1.findViewById(R.id.textView12);
        TextView priceTextView = (TextView) view1.findViewById(R.id.textView13);
        TextView stockTextView = (TextView) view1.findViewById(R.id.textView14);

        nameTextView.setText(nameStrings[i]);
        descripTextView.setText(descripStrings[i]);
        priceTextView.setText(priceStrings[i]);
        stockTextView.setText(stockStrings[i]);

        return view1;
    }
}   // Main Class
