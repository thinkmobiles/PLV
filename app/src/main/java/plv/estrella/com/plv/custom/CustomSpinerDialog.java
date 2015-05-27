package plv.estrella.com.plv.custom;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.cristaliza.mvc.models.estrella.Item;
import java.util.ArrayList;
import java.util.List;

import plv.estrella.com.plv.R;
import plv.estrella.com.plv.adapters.SpinnerPurchaseAdapter;
import plv.estrella.com.plv.database.DBManager;
import plv.estrella.com.plv.database.Shop;

/**
 * Created by vasia on 26.05.2015.
 */
public class CustomSpinerDialog {

    private Activity mCallingActivity;
    private LayoutInflater inflater;
    private ArrayList<Item> items;
    private int selected;

    private List<Shop> shopList, subList;
    private LinearLayout spinerLayout;
    private Spinner spinner;
    private SpinnerPurchaseAdapter spinnerPurchaseAdapter;
    private TextView positivButton, negativButton;
    private EditText shopName;
    private AlertDialog alertDialog;
    private Item mCurentItem;
    private AlertDialog.Builder spinerDialog;

    public CustomSpinerDialog(Activity activity, Item item) {
        mCallingActivity = activity;
        mCurentItem = item;
    }

    public void addProduct() {
        new GetShopTask().execute();

//        createCustomDialog();
    }

    public void createCustomDialog() {

        spinerDialog = new AlertDialog.Builder(mCallingActivity);

        inflater = (LayoutInflater) mCallingActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_dialog_spinner, null);

        findDialogUI(view);
        setDialogListener();

        spinnerPurchaseAdapter = new SpinnerPurchaseAdapter(mCallingActivity, subList);
        spinner.setAdapter(spinnerPurchaseAdapter);

        spinerDialog.setView(view);
        alertDialog = spinerDialog.create();
        alertDialog.show();

    }

    private void findDialogUI(View view) {
        spinerLayout = (LinearLayout) view.findViewById(R.id.ll_spinner);
        spinner = (Spinner) view.findViewById(R.id.dialogSpinner);
        negativButton = (TextView) view.findViewById(R.id.tv_cancel_action_CD);
        positivButton = (TextView) view.findViewById(R.id.tv_accept_action_CD);
        shopName = (EditText) view.findViewById(R.id.et_new_Shop_CD);
    }

    private void setDialogListener() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = position;

//                if (subList.get(position).getId() == -1) {
//                    spinerLayout.setVisibility(View.INVISIBLE);
//                    shopName.setVisibility(View.VISIBLE);
//                }
                Log.d("QQQ", String.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        negativButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopName.getVisibility() == View.VISIBLE) {
                    spinerLayout.setVisibility(View.VISIBLE);
                    shopName.setVisibility(View.GONE);
                } else
                    alertDialog.dismiss();
            }
        });

        positivButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopName.getVisibility() == View.GONE) {
                    if (subList.get(selected).getId() == null) {
                        spinerLayout.setVisibility(View.INVISIBLE);
                        shopName.setVisibility(View.VISIBLE);
                    } else {
                        shopList.get(selected).getId();
//                        itemDAO.save(mCurentItem, subList.get(selected).getId());
                        DBManager.addItem(mCurentItem, 2, subList.get(selected));
                        alertDialog.dismiss();
                        Toast.makeText(mCallingActivity, "Item add to shop_id= " + String.valueOf(subList.get(selected).getId()), Toast.LENGTH_SHORT).show();

                    }
                } else if (!shopName.getText().toString().isEmpty()) {
//                    shopDAO.save(new Shop(shopName.getText().toString()));
                    DBManager.addShop(shopName.getText().toString());
                    spinerLayout.setVisibility(View.VISIBLE);
                    shopName.setVisibility(View.GONE);
                    addProduct();
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(mCallingActivity, "enter shop", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public class GetShopTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            shopList = new ArrayList<>();
            subList = new ArrayList<>();
//            shopList = shopDAO.getShops();
            shopList = DBManager.getShops();
            if (!shopList.isEmpty()) {
                Shop lastElement = shopList.get(shopList.size() - 1);
                shopList.add(0, lastElement);
                subList = shopList.subList(0, shopList.size() - 1);
            }

            subList.add(new Shop("Create new shop"));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            createCustomDialog();
        }
    }

}
