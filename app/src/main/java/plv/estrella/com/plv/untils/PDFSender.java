package plv.estrella.com.plv.untils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import plv.estrella.com.plv.R;
import plv.estrella.com.plv.database.DBItem;
import plv.estrella.com.plv.database.DBManager;
import plv.estrella.com.plv.global.Constants;

/**
 * Created by samson on 30.06.15.
 */
public class PDFSender {

//    public static void sendShopPDFs (Activity activity, long pos){
//        // ItemDAO i = new ItemDAO(activity);
//        List<DBItem> items = DBManager.get(pos);
//        ArrayList<Uri> uris =new ArrayList<>();
//        //  List<Item> items = i.getItems(String.valueOf(pos));
//
//        for (int k=0; k<items.size(); k++){
//            File file = new File(ApiManager.getPath() + items.get(k).getPdf());
//            if (!file.exists() || !file.canRead()) {
//                Toast.makeText(activity, activity.getString(R.string.attachment_error), Toast.LENGTH_SHORT).show();
//                return;
//            }
//            uris.add(Uri.fromFile(file));
//
//        }
//
//        Intent mailer = new Intent(Intent.ACTION_SEND_MULTIPLE);
//        mailer.setType(Constants.TYPE_MESSAGE);
//        mailer.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
//        activity.startActivity(Intent.createChooser(mailer, activity.getString(R.string.send_mail)));
//    }

}
