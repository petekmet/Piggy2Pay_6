package piggypos.t16.biz.piggy2pay.fragments.groups;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import piggypos.t16.biz.piggy2pay.R;

/**
 * Created by peterkmet on 29/03/15.
 */
public class GroupsFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_groups, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }
}
