package piggypos.t16.biz.piggy2pay.db;

import android.provider.ContactsContract;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by peterkmet on 08/03/15.
 */

@DatabaseTable
public class Bill {
    private static final String TAG = Bill.class.getName();

    @DatabaseField(generatedId = true)
    private int id; //bill number in case multiple bills are open in the same time

    @DatabaseField
    private Date created;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<BillItem> items;

    public ForeignCollection<BillItem> getItems() {
        return items;
    }

    public Bill(){
        created = new Date();
    }

    public Date getCreated() {
        return created;
    }

    public int getId() {
        return id;
    }

    public void clearBill(DatabaseHelper dbHelper){
        ArrayList<BillItem> itemsList = new ArrayList<>(items);
        Log.d(TAG, "items to delete="+items.size());
        try {
            Dao<BillItem,Integer> dao = dbHelper.getDao(BillItem.class);
            for (BillItem bitm : itemsList) {
                bitm.delete(dbHelper);
                dao.delete(bitm);
            }

        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
