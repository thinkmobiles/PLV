package plv.estrella.com.plv.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import plv.estrella.com.plv.MainActivity;
import plv.estrella.com.plv.R;

/**
 * Created by samson on 07.07.15.
 */
public class CompaniaFragment extends Fragment implements View.OnClickListener {

    private MainActivity mCallingActivity;
    private ImageView ivVolver;
    private TextView tvDescription, tvNameVideo;
    private RelativeLayout rlVideo;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallingActivity = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compania,null);

        findUI(view);
        setListeners();

        return view;
    }

    private void findUI(View _view){
        ivVolver            = (ImageView) _view.findViewById(R.id.ivVolver_FC);
        tvDescription       = (TextView) _view.findViewById(R.id.tvDescription_FC);
        tvNameVideo         = (TextView) _view.findViewById(R.id.tvNameVideo_FC);
        rlVideo             = (RelativeLayout) _view.findViewById(R.id.rlVideo);

        mCallingActivity.setBackground();
        mCallingActivity.setTitle(mCallingActivity.getString(R.string.compania));
    }

    private void setListeners(){
        ivVolver.setOnClickListener(this);
        rlVideo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivVolver_FC:
                mCallingActivity.onBackPressed();
                break;
            case R.id.rlVideo:
                break;
        }
    }
}
