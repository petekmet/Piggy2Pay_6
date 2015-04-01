package piggypos.t16.biz.piggy2pay.db;

import android.app.ActionBar;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by peterkmet on 08/03/15.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DB_NAME = "piggy2pay.db";
    private static final int DB_VERSION = 13;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Bill.class);
            TableUtils.createTable(connectionSource, BillItem.class);
            TableUtils.createTable(connectionSource, BillItemPriceList.class);
            TableUtils.createTable(connectionSource, BillItemPrice.class);

            TableUtils.createTable(connectionSource, Group.class);

            TableUtils.createTable(connectionSource, Item.class);
            TableUtils.createTable(connectionSource, ItemPriceList.class);
            TableUtils.createTable(connectionSource, ItemPrice.class);

            /*
            Bill bill = new Bill();
            Dao<Bill, Integer> billDao = getDao(Bill.class);
            billDao.create(bill);


            BillItem billItem = new BillItem();
            billItem.setHeadTitle("Rohlik");
            billItem.setQuantity(2);
            billItem.setBill(bill);

            Dao<BillItem, Integer> billItemDao = getDao(BillItem.class);
            billItemDao.create(billItem);
            */

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Bill.class, true);
            TableUtils.dropTable(connectionSource, BillItem.class, true);
            TableUtils.dropTable(connectionSource, BillItemPrice.class, true);
            TableUtils.dropTable(connectionSource, BillItemPriceList.class, true);

            TableUtils.dropTable(connectionSource, Group.class, true);

            TableUtils.dropTable(connectionSource, Item.class, true);
            TableUtils.dropTable(connectionSource, ItemPriceList.class, true);
            TableUtils.dropTable(connectionSource, ItemPrice.class, true);

            onCreate(database, connectionSource);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
