package rezkyaulia.com.bamms_project.view;

import android.content.Context;
import android.util.AttributeSet;

import com.airbnb.lottie.LottieAnimationView;

import rezkyaulia.com.bamms_project.util.Utils;

/**
 * Created by Shiburagi on 25/08/2017.
 */

public class ALoadingView extends LottieAnimationView {
    Context context;
    public ALoadingView(Context context) {
        super(context);
        this.context = context;
    }

    public ALoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }

    public ALoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        initAnimation();

    }

    private void initAnimation() {

        int size = (int) new Utils(context).convertDpToPixel(144);
        setMaxWidth(size);
        setMaxHeight(size);

        if (getLayoutParams() != null) {
            getLayoutParams().width = size;
            getLayoutParams().height = size;
        }
        setAnimation("animation/triangle_loading.json");
        loop(true);
        playAnimation();
    }

}
