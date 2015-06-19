package plv.estrella.com.plv.models;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabWidget;
import android.widget.TextView;

import java.io.Serializable;

import plv.estrella.com.plv.R;

/**
 * Created by samson on 22.05.2015.
 */
public class ItemProduct implements Serializable {
    private String mDescription;
    private int res;

    public ItemProduct(String _desc, int _id){
        this.mDescription = _desc;
        this.res = _id;
    }

    public String getmDescription() {
        return this.mDescription;
    }

    public int getRes() {
        return this.res;
    }
}
