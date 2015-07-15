package plv.estrella.com.plv.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import plv.estrella.com.plv.MainActivity;
import plv.estrella.com.plv.R;
import plv.estrella.com.plv.global.Constants;

/**
 * Created by samson on 01.07.15.
 */
public class GaleriaFragment extends Fragment {

    private MainActivity mCallingActivity;

    public static GaleriaFragment newInstance(String _path){
        GaleriaFragment fragment = new GaleriaFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.PARAMS, _path);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallingActivity = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_galeria, container, false);
        if(getArguments() != null)
            mCallingActivity.setBackground(getArguments().getString(Constants.PARAMS));
        mCallingActivity.setFullScreenSetting(true);
        ((LinearLayout) view.findViewById(R.id.llPhoto)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallingActivity.onBackPressed();
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        mCallingActivity.setFullScreenSetting(false);
        super.onDestroy();
    }
}
