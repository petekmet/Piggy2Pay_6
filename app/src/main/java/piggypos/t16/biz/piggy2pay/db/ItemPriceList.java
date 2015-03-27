package piggypos.t16.biz.piggy2pay.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by peterkmet on 27/03/15.
 */
public class ItemPriceList {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true)
    private Item item;

    @DatabaseField
    private String name; //name of the price point

    @DatabaseField
    private int type; //price-point, modifier-set

    @DatabaseField
    private boolean exclusivePrice;

    @ForeignCollectionField
    private ForeignCollection<ItemPrice> price;

    public void setBillItem(Item item) {
        this.item = item;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
