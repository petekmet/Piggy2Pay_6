package piggypos.t16.biz.piggy2pay.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by peterkmet on 08/03/15.
 */

@DatabaseTable
public class BillItem {
    @DatabaseField(generatedId=true)
    private int id;

    @DatabaseField
    private String headTitle;

    @DatabaseField
    private int quantity;

    @DatabaseField(foreign = true)
    private Bill bill;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<BillItemPriceList> priceLists;

    public int getId() {
        return id;
    }

    public BigDecimal getItemTotalPrice(){
        BigDecimal price = BigDecimal.ZERO;
        //iterate through all lists and calculate price
        Iterator<BillItemPriceList> iterator = priceLists.iterator();
        while(iterator.hasNext()){
            BillItemPriceList list = iterator.next();
            price = price.add(list.getPrice());
        }
        return price;
    }

    public void delete(DatabaseHelper helper) throws SQLException {
        ArrayList<BillItemPriceList> pl = new ArrayList<>(priceLists);
        Dao<BillItemPriceList,Integer> dao = helper.getDao(BillItemPriceList.class);
        for(BillItemPriceList l : pl){
            l.delete(helper);
            dao.delete(l);
        }
    }

    public String getHeadTitle() {
        return headTitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setHeadTitle(String headTitle) {
        this.headTitle = headTitle;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
