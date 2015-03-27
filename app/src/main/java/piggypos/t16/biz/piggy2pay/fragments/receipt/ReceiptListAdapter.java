package piggypos.t16.biz.piggy2pay.fragments.receipt;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import piggypos.t16.biz.piggy2pay.PiggyApplication;
import piggypos.t16.biz.piggy2pay.R;
import piggypos.t16.biz.piggy2pay.db.Bill;
import piggypos.t16.biz.piggy2pay.db.BillItem;
import piggypos.t16.biz.piggy2pay.db.DatabaseHelper;

/**
 * Created by peterkmet on 11/03/15.
 */

public class ReceiptListAdapter extends BaseAdapter {
    private ArrayList<BillItem> biList;
    private DatabaseHelper dbHelper;

    public ReceiptListAdapter(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
        reload();
    }

    @Override
    public int getCount() {
        return biList.size();
    }

    @Override
    public Object getItem(int index) {
        return (index>=biList.size()) ? null : biList.get(index);
    }

    @Override
    public long getItemId(int index) {
        return (index>=biList.size()) ? -1 : biList.get(index).getId();
    }

    @Override
    public View getView(int index, View recycledView, ViewGroup rootView) {
        View view = recycledView;
        TextView billTotalPriceView = null;
        TextView billItemTitle = null;
        TextView billItemQuantity = null;
        TextView billItemPrice = null;

        //create/restore Views
        int viewToInflate = /*(index==billItemsList.size()) ? R.layout.billitemfooter :*/ R.layout.receipt_item;
        if(view==null){

            view = LayoutInflater.from(rootView.getContext()).inflate(viewToInflate, null);
            view.setTag(R.id.receiptList, viewToInflate);
            //if(index==biList.length){ //this is the footer (last item in the list)
            //    billTotalPriceView = (TextView)view.findViewById(R.id.billTotalPrice);
            //    view.setTag(R.id.billTotalPrice, billTotalPriceView);

            //            }else{ //regular item
                billItemTitle = (TextView)view.findViewById(R.id.billItemTitle);
                //billItemTitle.setTypeface(Typeface.createFromAsset(getAssets(), Constants.ROBOTO_CONDENSED_REGULAR));
                billItemQuantity = (TextView)view.findViewById(R.id.billItemQuantity);
                billItemPrice = (TextView)view.findViewById(R.id.billItemPrice);

                view.setTag(R.id.billItemTitle, billItemTitle);
                view.setTag(R.id.billItemQuantity, billItemQuantity);
                view.setTag(R.id.billItemPrice, billItemPrice);
            //}

        }else{
            //if(index==billItemsList.size()){ //this is the footer (last item in the list)
            //    billTotalPriceView = (TextView)view.getTag(R.id.billTotalPrice);

            //}else{ //regular item
                billItemTitle = (TextView)view.getTag(R.id.billItemTitle);
                billItemQuantity = (TextView)view.getTag(R.id.billItemQuantity);
                billItemPrice = (TextView)view.getTag(R.id.billItemPrice);

            //}
        }

        BillItem bi = biList.get(index);
        //update view's content
        //if(index==billItemsList.size()){ //this is the footer (last item in the list)
            //billTotalPriceView.setText(Utilities.longToCurrency(getTotal()));
        //    billTotalPriceView.setText(Utilities.longToCurrency(bi.price.multiply(BigDecimal.valueOf(bi.quantity))));

        //}else{ //regular item


            //CatalogueItem ci = null;//mainActivity.getItemsList().get(bi.position);
            int q = bi.getQuantity();
            BigDecimal p = bi.getItemTotalPrice();
            billItemTitle.setText(bi.getHeadTitle());
            billItemPrice.setText(PiggyApplication.formatPrice(p.multiply(BigDecimal.valueOf((long)q))));
            if(q>1){
                billItemQuantity.setVisibility(View.VISIBLE);
                billItemQuantity.setText(q+"x");
            }else{
                billItemQuantity.setVisibility(View.GONE);
            }
        //}

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        reload();
        super.notifyDataSetChanged();
    }

    private void reload(){
        Bill currentBill = PiggyApplication.getInstance().getCurrentBill();
        if(currentBill==null){
            biList = new ArrayList<>();

        }else {
            try {
                Dao<Bill, Integer> billDao = dbHelper.getDao(Bill.class);
                Bill bill = billDao.queryForId(currentBill.getId());
                biList = new ArrayList(bill.getItems());
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
}
