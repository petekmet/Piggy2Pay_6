package piggypos.t16.biz.piggy2pay.fragments.receipt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import piggypos.t16.biz.piggy2pay.PiggyApplication;
import piggypos.t16.biz.piggy2pay.R;
import piggypos.t16.biz.piggy2pay.fragments.PiggyFragment;
import piggypos.t16.biz.piggy2pay.widgets.PaperButton;

/**
 * Created by peterkmet on 04/03/15.
 */
public class ReceiptFragment extends Fragment {

    @InjectView(R.id.receiptList)
    public ListView receiptListView;

    @InjectView(R.id.receiptClearBtn)
    public PaperButton clearReceiptButton;

    public static ReceiptFragment newInstance(){
        ReceiptFragment fragment = new ReceiptFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_receipt, container, false);
        ButterKnife.inject(this, rootView);
        receiptListView.setAdapter(PiggyApplication.getInstance().getReceiptListAdapter());
        return rootView;
    }

    @OnClick(R.id.receiptClearBtn)
    public void onClearReceipt(){
        PiggyApplication.getInstance().clearCustomBill();
    }
}
