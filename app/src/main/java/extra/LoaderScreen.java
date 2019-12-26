package extra;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.avaal.com.afm2020autoCx.R;


/**
 * Created by dell pc on 04-10-2016.
 */
public class LoaderScreen {
    private Context mContext;
    private View v;
    private ImageView iv;



    public LoaderScreen(Context context) {
        mContext = context;
    }


    public View getView() {
        if (mContext != null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.loader_layout, null);
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

