package net.wrappy.im.ui.activity;


import net.wrappy.im.LauncherActivity;
import net.wrappy.im.ui.view.Layout;
import net.wrappy.im.R;

@Layout(layoutId = R.layout.activity_router)
public class RouterActivity extends LauncherActivity {
    @Override
    protected void init() {
        forward(WelcomActivity.class);
    }
}
