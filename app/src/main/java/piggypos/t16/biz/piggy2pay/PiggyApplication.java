package piggypos.t16.biz.piggy2pay;

import android.app.Application;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;

import piggypos.t16.biz.piggy2pay.db.Bill;
import piggypos.t16.biz.piggy2pay.db.BillItem;
import piggypos.t16.biz.piggy2pay.db.BillItemPrice;
import piggypos.t16.biz.piggy2pay.db.BillItemPriceList;
import piggypos.t16.biz.piggy2pay.db.DatabaseHelper;
import piggypos.t16.biz.piggy2pay.fragments.receipt.ReceiptListAdapter;

/**
 * Created by peterkmet on 23/03/15.
 */

public class PiggyApplication extends Application {
    private static final String TAG = PiggyApplication.class.getName();

    private DatabaseHelper databaseHelper = null;
    private ReceiptListAdapter receiptListAdapter = null;
    private static PiggyApplication self;

    private static DecimalFormat PRICE_FORMATTER = new DecimalFormat("* ##,###,##0.00");
    private static int DISPLAY_SCALE = 2;
    private Bill currentBill = null;

    @Override
    public void onCreate() {
        super.onCreate();
        databaseHelper =
                OpenHelperManager.getHelper(this, DatabaseHelper.class);
        currentBill = getInitialBillId();
        if(currentBill==null) {
            Log.d(TAG, "no bill currently open");
        }else{

            Log.d(TAG, "latest billId=" + currentBill.getId());
        }
        self = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        OpenHelperManager.releaseHelper();
    }

    public ReceiptListAdapter getReceiptListAdapter(){
        if(receiptListAdapter==null){

                receiptListAdapter = new ReceiptListAdapter(databaseHelper);

        }
        return receiptListAdapter;
    }

    public static PiggyApplication getInstance(){
        return self;
    }

    public static DecimalFormat getPriceFormatter(){
        return PRICE_FORMATTER;
    }

    public static String formatPrice(BigDecimal price){
        return PRICE_FORMATTER.format(price.setScale(DISPLAY_SCALE)).trim();
    }

    private Bill getInitialBillId(){
        try {
            Iterator<Bill> iterator = databaseHelper.getDao(Bill.class).queryBuilder().orderBy("created", false).iterator();

            if(iterator.hasNext()) {
                return iterator.next();

            }

        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public Bill getCurrentBill() {
        return currentBill;
    }

    public void setCurrentBillId(int currentBillId) {
        //this.currentBillId = currentBillId;
    }

    public void clearCustomBill() throws SQLException {

        //delete Bill
        //delete all BillItem
        //delete all BillItemPriceList
        //delete all BillItemPrice

        if(currentBill!=null) {
            Dao<Bill, Integer> cbDao = databaseHelper.getDao(Bill.class);
            Bill bill = cbDao.queryForId(currentBill.getId());

            bill.clearBill(databaseHelper);

            cbDao.delete(bill);
            currentBill = null;

            receiptListAdapter.notifyDataSetChanged();
        }
    }

    public void addCustomBillItem(BigDecimal price) {
        try {
            if (currentBill == null) {
                currentBill = new Bill();
                Dao<Bill, Integer> dao = databaseHelper.getDao(Bill.class);
                dao.create(currentBill);
            }


            BillItem newBillItem = new BillItem();
            newBillItem.setBill(currentBill);
            newBillItem.setQuantity(1);
            newBillItem.setHeadTitle("Custom item");
            databaseHelper.getDao(BillItem.class).create(newBillItem);

            BillItemPriceList mainPriceList = new BillItemPriceList();
            mainPriceList.setBillItem(newBillItem);
            databaseHelper.getDao(BillItemPriceList.class).create(mainPriceList);

            BillItemPrice itemPrice = new BillItemPrice();
            itemPrice.setPriceList(mainPriceList);
            itemPrice.setValue(price);
            itemPrice.setSelected(true);
            databaseHelper.getDao(BillItemPrice.class).create(itemPrice);

            receiptListAdapter.notifyDataSetChanged();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
