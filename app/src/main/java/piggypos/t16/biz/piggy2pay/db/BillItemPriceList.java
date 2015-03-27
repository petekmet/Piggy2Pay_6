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
public class BillItemPriceList {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true)
    private BillItem billItem;

    @DatabaseField
    private String name; //name of the price point

    @DatabaseField
    private int type; //price-point, modifier-set

    @DatabaseField
    private boolean exclusivePrice;

    @ForeignCollectionField
    private ForeignCollection<BillItemPrice> price;

    public void setBillItem(BillItem billItem) {
        this.billItem = billItem;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    //iterate through the list and calculate total
    public BigDecimal getPrice(){
        BigDecimal totalPrice = BigDecimal.ZERO;
        Iterator<BillItemPrice> iterator = price.iterator();
        while(iterator.hasNext()){
            BillItemPrice price = iterator.next();
            if(price.isSelected()){
                totalPrice = totalPrice.add(price.getValue());
            }
        }
        return totalPrice;
    }

    public void delete(DatabaseHelper helper) throws SQLException {
        ArrayList<BillItemPrice> pl = new ArrayList<>(price);
        Dao<BillItemPrice,Integer> dao = helper.getDao(BillItemPrice.class);
        for(BillItemPrice p : pl){
            dao.delete(p);
        }
    }
}
