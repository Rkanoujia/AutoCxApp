package extra;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.avaal.com.afm2020autoCx.R;

import androidx.core.content.ContextCompat;


/**
 * Created by dell pc on 23-04-2018.
 */

public class UploadScreen {
    private Context mContext;
    private View v;
    private ImageView iv;



    public UploadScreen(Context context) {
        mContext = context;
    }


    public View getView() {
        if (mContext != null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.upload_screen, null);
            iv = (ImageView) v.findViewById(R.id.img);
        }
        return v;
    }

    public void startAnimating() {
        if (iv != null)
            ((AnimationDrawable) iv.getDrawable()).start();
    }

    public void showBackground(Context context, boolean show){
        v.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));

    }
    public void stopAnimation() {
        if (iv != null){
            ((AnimationDrawable) iv.getDrawable()).stop();
        }
        mContext = null;
    }
}
