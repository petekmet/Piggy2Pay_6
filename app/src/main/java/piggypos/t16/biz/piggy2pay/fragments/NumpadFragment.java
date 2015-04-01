package piggypos.t16.biz.piggy2pay.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnTouch;
import piggypos.t16.biz.piggy2pay.PiggyApplication;
import piggypos.t16.biz.piggy2pay.R;

/**
 * Created by peterkmet on 28/02/15.
 */
public class NumpadFragment extends Fragment {
    private static final String TAG = NumpadFragment.class.getName();
    private Rect padRect;

    private int scale = 2;

    private BigDecimal numEntered = BigDecimal.ZERO.setScale(scale);


    @InjectView(R.id.numPad1)
    public TextView numPad1;
    @InjectView(R.id.numEntered)
    public TextView numEnteredView;

    public NumpadFragment(){
        Log.d(TAG, "new Instance");
    }

    public static NumpadFragment newInstance(){
        NumpadFragment fragment = new NumpadFragment();
        return fragment;
    }

    @OnTouch({R.id.numPad1,R.id.numPad2,R.id.numPad3,R.id.numPad4,R.id.numPad5,R.id.numPad6,R.id.numPad7,R.id.numPad8,R.id.numPad9,R.id.numPad0,R.id.numPadClear,R.id.numPadPlus,})
    boolean onClickPad1(View view, MotionEvent event){
        Boolean state = (Boolean)view.getTag();
        if(state==null){
            state = false;
            view.setTag(state);
        }
        if(!state && event.getActionMasked()==MotionEvent.ACTION_DOWN) {
            state = true;
            view.setTag(state);
            padRect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            int colorRes = R.color.accent_material_light;

            view.setBackgroundColor(getResources().getColor(colorRes));
            ((TextView)view).setTextColor(getResources().getColor(R.color.background_material_light));
            //Log.d(TAG, "touches "+padTouched);
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                   onNumEntered(0);
                }
            }, 500);
        }else
        if(state && (event.getActionMasked()==MotionEvent.ACTION_UP || event.getActionMasked()==MotionEvent.ACTION_CANCEL)){
            state = false;
            view.setTag(state);
            view.setBackgroundColor(getResources().getColor(R.color.background_material_light));
            ((TextView)view).setTextColor(getResources().getColor(R.color.secondary_text_default_material_light));
            view.getHandler().removeCallbacksAndMessages(null);
            if(event.getActionMasked()==MotionEvent.ACTION_UP) {
                onNumEntered(view.getId());
            }

        }else
        if(event.getActionMasked() == MotionEvent.ACTION_MOVE && padRect!=null){
            if(!padRect.contains(view.getLeft() + (int) event.getX(), view.getTop() + (int) event.getY())){
                state = false;
                view.setTag(state);
                view.setBackgroundColor(getResources().getColor(R.color.background_material_light));
                ((TextView)view).setTextColor(getResources().getColor(R.color.secondary_text_default_material_light));
                padRect = null;
                view.getHandler().removeCallbacksAndMessages(null);
            }
        }
        return true;
    }

    private void onNumEntered(int id){
        BigDecimal entered = null;//BigDecimal.ZERO;

        switch(id){
            case R.id.numPad1:
                entered = BigDecimal.valueOf(1, scale);
                break;

            case R.id.numPad2:
                entered = BigDecimal.valueOf(2, scale);
                break;

            case R.id.numPad3:
                entered = BigDecimal.valueOf(3, scale);
                break;

            case R.id.numPad4:
                entered = BigDecimal.valueOf(4, scale);
                break;

            case R.id.numPad5:
                entered = BigDecimal.valueOf(5, scale);
                break;

            case R.id.numPad6:
                entered = BigDecimal.valueOf(6, scale);
                break;

            case R.id.numPad7:
                entered = BigDecimal.valueOf(7, scale);
                break;

            case R.id.numPad8:
                entered = BigDecimal.valueOf(8, scale);
                break;

            case R.id.numPad9:
                entered = BigDecimal.valueOf(9, scale);
                break;

            case R.id.numPad0:
                entered = BigDecimal.valueOf(0, scale);
                break;

            case R.id.numPadPlus:
                PiggyApplication.getInstance().addCustomBillItem(numEntered);
                this.numEntered = entered = BigDecimal.ZERO.setScale(scale);
                break;

            case R.id.numPadClear:
                entered = BigDecimal.TEN;
                break;

            default:
                entered = BigDecimal.ZERO;
                this.numEntered = BigDecimal.ZERO.setScale(scale);
        }

        if(entered.compareTo(BigDecimal.TEN)==0){
            this.numEntered = this.numEntered.divide(BigDecimal.TEN, BigDecimal.ROUND_FLOOR);
        }else {
            BigDecimal numEntered = this.numEntered.multiply(BigDecimal.TEN).add(entered);
            if(numEntered.compareTo(BigDecimal.valueOf(10000000L))<0){
                this.numEntered = numEntered;
            }
        }

        numEnteredView.setText(PiggyApplication.formatPrice(this.numEntered));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_numpad, container, false);
        ButterKnife.inject(this, rootView);

        if(savedInstanceState!=null){
            numEntered = BigDecimal.valueOf(savedInstanceState.getLong("NUM_ENTERED"), 2);

        }

        numEnteredView.setText(PiggyApplication.formatPrice(this.numEntered));
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("NUM_ENTERED", numEntered.unscaledValue().longValue());
    }
}
