package piggypos.t16.biz.piggy2pay.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;

/**
 * Created by peterkmet on 27/03/15.
 */

@DatabaseTable
public class ItemPrice {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField
    private BigDecimal price;

    @DatabaseField
    private boolean selected;

    @DatabaseField(foreign = true)
    private ItemPriceList priceList;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public BigDecimal getValue() {
        return price;
    }

    public void setValue(BigDecimal price) {
        this.price = price;
    }

    public void setPriceList(ItemPriceList priceList) {
        this.priceList = priceList;
    }
}
