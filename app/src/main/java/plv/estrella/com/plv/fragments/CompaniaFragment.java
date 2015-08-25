package plv.estrella.com.plv.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.cristaliza.mvc.models.estrella.Item;
import com.squareup.picasso.Picasso;

import plv.estrella.com.plv.MainActivity;
import plv.estrella.com.plv.R;
import plv.estrella.com.plv.VideoPlayerActivity;
import plv.estrella.com.plv.global.Constants;
import plv.estrella.com.plv.models.ItemSerializable;
import plv.estrella.com.plv.untils.ApiManager;
import plv.estrella.com.plv.untils.BitmapCreator;

/**
 * Created by samson on 07.07.15.
 */
public class CompaniaFragment extends Fragment implements View.OnClickListener {

    private MainActivity mCallingActivity;
    private ImageView ivVolver, ivPhotoComp, ivVideo, ivLogo;
    private TextView tvDescription, tvNameVideo;
    private RelativeLayout rlVideo;
    private EventListener eventListener;
    private Item mCurrentItem, mCompania;

    public static CompaniaFragment newInstance(final ItemSerializable _item) {
        final CompaniaFragment fragment = new CompaniaFragment();
        final Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.PARAM_ITEM, _item);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallingActivity = (MainActivity) activity;
        if(getArguments() != null){
            mCurrentItem = ((ItemSerializable) getArguments().getSerializable(Constants.PARAM_ITEM)).getItem();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compania,null);
        findUI(view);
        setListeners();
        makeDownLoadListener();
        ApiManager.getSecondLevel(eventListener, mCurrentItem);
        return view;
    }
    private void findUI(View _view){
        ivVolver            = (ImageView) _view.findViewById(R.id.ivVolver_FC);
        ivPhotoComp         = (ImageView) _view.findViewById(R.id.ivPhotoComp_FC);
        ivVideo             = (ImageView) _view.findViewById(R.id.ivVideo_FC);
        ivLogo              = (ImageView) _view.findViewById(R.id.ivLogo_FC);
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
    private void makeDownLoadListener(){
        eventListener = new EventListener() {
            @Override
            public void onEvent(Event event) {
                switch (event.getId()){
                    case AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID:
//                        Toast.makeText(getActivity(), event.getType() + getActivity().getString(R.string.error), Toast.LENGTH_SHORT).show();
                        break;
                    case AppModel.ChangeEvent.SECOND_LEVEL_CHANGED_ID:
                        setData();
                        break;
                }
            }
        };
    }
    private void setData(){
        mCompania = ApiManager.getSecondList().get(0);
        ivPhotoComp.setImageBitmap(BitmapCreator.getCompressedBitmap(mCompania.getBackgroundImage(), Constants.RATIO_4_3, 1024f));
        ivLogo.setImageBitmap(BitmapCreator.getCompressedBitmap(mCompania.getLogo(), Constants.RATIO_8_1, 560f));
        Picasso.with(mCallingActivity)
                .load(Constants.URL_YOUTUBE_IMG
                        + VideoPlayerActivity.getYouTubeImageId(mCompania.getExtraVideos().get(0))
                        + Constants.URL_YOUTUBE_IMG_INDEX)
                .into(ivVideo);
        tvDescription.setText(Html.fromHtml(mCompania.getDescription()));
        tvNameVideo.setText(mCompania.getExtraVideosDescripton().get(0));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivVolver_FC:
                mCallingActivity.onBackPressed();
                break;
            case R.id.rlVideo:
                startVideoActtivity(mCompania.getExtraVideos().get(0));
                break;
        }
    }
    private void startVideoActtivity(String _path) {
        Intent intent = new Intent(mCallingActivity, VideoPlayerActivity.class);
        intent.putExtra(Constants.YOUTUBE_VIDEO_ID, _path);
        startActivity(intent);
    }
}
