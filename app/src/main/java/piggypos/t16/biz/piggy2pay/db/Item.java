package piggypos.t16.biz.piggy2pay.db;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by peterkmet on 27/03/15.
 */

@DatabaseTable
public class Item {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField(foreign = true)
    private Group group;

    @ForeignCollectionField
    private ForeignCollection<ItemPriceList> itemPriceLists;
}
