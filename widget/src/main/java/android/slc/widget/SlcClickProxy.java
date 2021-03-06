package android.slc.widget;

import android.view.View;

public class SlcClickProxy implements View.OnClickListener {

    private View.OnClickListener origin;
    private long lastClick = 0;
    private long timers = 180; //ms
    private IAgain mIAgain;

    public SlcClickProxy(View.OnClickListener origin) {
        this(origin, 280);
    }

    public SlcClickProxy(View.OnClickListener origin, long timers) {
        this(origin, null, timers);
    }


    public SlcClickProxy(View.OnClickListener origin, IAgain again, long timers) {
        this.origin = origin;
        this.mIAgain = again;
        this.timers = timers;
    }


    @Override
    public void onClick(View v) {
        if (System.currentTimeMillis() - lastClick >= timers) {
            origin.onClick(v);
            lastClick = System.currentTimeMillis();
        } else {
            if (mIAgain != null) mIAgain.onAgain();
        }
    }

    public interface IAgain {
        void onAgain();//重复点击
    }
}
