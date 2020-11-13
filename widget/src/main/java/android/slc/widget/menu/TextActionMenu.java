package android.slc.widget.menu;

import android.content.Context;
import android.slc.widget.R;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.core.view.BaseActionProviderImp;



public class TextActionMenu extends BaseActionProviderImp {
    private TextView mTitle;

    /**
     * Creates cpb_complete_state_selector new instance.
     *
     * @param context Context for accessing resources.
     */
    public TextActionMenu(Context context) {
        super(context);
    }

    @Override
    protected int getActionViewLayout() {
        return R.layout.action_text_view_by_menu;
    }

    @Override
    protected void bindView(View rootView) {
        super.bindView(rootView);
        if (rootView != null) {
            rootView.setId(mForItem.getItemId());
            mTitle = rootView.findViewById(R.id.title);
            mTitle.setText(mForItem.getTitle());
        }
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setTitle(@StringRes int stringId) {
        mTitle.setText(stringId);
    }
}
