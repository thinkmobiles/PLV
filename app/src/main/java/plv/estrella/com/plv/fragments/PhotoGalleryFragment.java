package plv.estrella.com.plv.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import plv.estrella.com.plv.MainActivity;
import plv.estrella.com.plv.R;

/**
 * Created by samson on 17.06.2015.
 */
public class PhotoGalleryFragment extends Activity {

    private ImageView mPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_photo_gallery);

        mPhoto = (ImageView) findViewById(R.id.ivPhotoSolo);
        mPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
