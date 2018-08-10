package net.wrappy.im.ui.activity;



import net.wrappy.im.BaseActivity;
import net.wrappy.im.ui.view.Layout;
import net.wrappy.im.R;

@Layout(layoutId = R.layout.activity_router)
public class RouterActivity extends BaseActivity {
    @Override
    protected void init() {
        overlay(WelcomActivity.class);
    }
}
