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
import plv.estrella.com.plv.database.Shop;
import plv.estrella.com.plv.global.Constants;

/**
 * Created by samson on 30.06.15.
 */
public class PDFSender {

    public static void sendShopPDFsFromEnvio(Activity activity, List<DBItem> items){
        ArrayList<Uri> uris = new ArrayList<>();

//        for (int k = 0; k < items.size(); ++k){
//            File file = new File(ApiManager.getPath() + items.get(k).getPdf());
//            if (!file.exists() || !file.canRead()) {
//                Toast.makeText(activity, activity.getString(R.string.attachment_error), Toast.LENGTH_SHORT).show();
//                return;
//            }
//            uris.add(Uri.fromFile(file));
//        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"samson1513@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.mail_topic_envio));
//        intent.setType("application/pdf");
//        intent.putExtra(Intent.EXTRA_STREAM, uris);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_TEXT, activity.getString(R.string.mail_message_envio));
        try {
            activity.startActivity(Intent.createChooser(intent, activity.getString(R.string.send_mail)));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(activity, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void sendShopPDFsFromPedido (Activity activity, List<DBItem> items){
        ArrayList<Uri> uris = new ArrayList<>();

//        for (int k = 0; k < items.size(); ++k){
//            File file = new File(ApiManager.getPath() + items.get(k).getPdf());
//            if (!file.exists() || !file.canRead()) {
//                Toast.makeText(activity, activity.getString(R.string.attachment_error), Toast.LENGTH_SHORT).show();
//                return;
//            }
//            uris.add(Uri.fromFile(file));
//        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"samson1513@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.mail_topic_pedido));
//        intent.setType("application/pdf");
//        intent.putExtra(Intent.EXTRA_STREAM, uris);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_TEXT, activity.getString(R.string.mail_message_pedido));
        try {
            activity.startActivity(Intent.createChooser(intent, activity.getString(R.string.send_mail)));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(activity, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

}
