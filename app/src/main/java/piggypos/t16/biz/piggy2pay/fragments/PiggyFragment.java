package piggypos.t16.biz.piggy2pay.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import piggypos.t16.biz.piggy2pay.PiggyApplication;

/**
 * Created by peterkmet on 23/03/15.
 */
public class PiggyFragment extends Fragment {
    public PiggyApplication getApp(){
        return PiggyApplication.getInstance();
    }
}
