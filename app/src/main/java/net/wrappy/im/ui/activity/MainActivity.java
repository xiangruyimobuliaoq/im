package net.wrappy.im.ui.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import net.wrappy.im.BaseActivity;
import net.wrappy.im.R;
import net.wrappy.im.ui.fragment.EmptyFragment;
import net.wrappy.im.ui.fragment.ProfileFragment;
import net.wrappy.im.ui.view.Layout;
import net.wrappy.im.ui.view.NoScrollViewPager;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 创建者     彭龙
 * 创建时间   2018/7/9 下午2:01
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
@Layout(layoutId = R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @BindView(R.id.btnHeaderEdit)
    ImageButton btnHeaderEdit;
    @BindView(R.id.btnHeaderSearch)
    ImageButton btnHeaderSearch;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.edSearchConversation)
    TextView edSearchConversation;
    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    private FragmentManager fragmentManager;
    private Adapter adapter;
    private Map<Integer, Fragment> mCache = new HashMap<>();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void init() {
        initViewPager();
        initTabLayout();
    }

    private void initViewPager() {
        fragmentManager = getSupportFragmentManager();
        adapter = new Adapter(fragmentManager);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mViewPager.setCurrentItem(2);
        mViewPager.setOffscreenPageLimit(1);
    }

    class Adapter extends FragmentStatePagerAdapter {

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return getFragment(position);
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    public Fragment getFragment(int position) {
        //加上缓存功能,优先取缓存
        Fragment fragment = mCache.get(position);
        if (fragment != null) {
            return fragment;
        }
        switch (position) {
            case 0:
                fragment = new EmptyFragment();
                break;
            case 1:
                fragment = new EmptyFragment();
                break;
            case 2:
                fragment = new EmptyFragment();
                break;
            case 3:
                fragment = new ProfileFragment();
                break;
        }
        mCache.put(position, fragment);
        return fragment;
    }

    private void initTabLayout() {

        // main menu tab
        TabLayout.Tab tab;
        for (int i = 0; i < 4; i++) {
            tab = mTabLayout.newTab();
            mTabLayout.addTab(tab);
        }
        createTabIcons(0, R.mipmap.ic_menu_normal, getString(R.string.tab_menu_menu));
        createTabIcons(1, R.mipmap.ic_menu_conversation_normal, getString(R.string.tab_menu_conversation));
        createTabIcons(2, R.mipmap.ic_promotion, getString(R.string.tab_menu_promotion));
        createTabIcons(3, R.mipmap.ic_menu_info_normal, getString(R.string.tab_menu_my_page));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                try {
//                    AppFuncs.dismissKeyboard(MainActivity.this);
                    TextView appTextView = (TextView) tab.getCustomView();
                    appTextView.setTextColor(getResources().getColor(R.color.menu_text_active));
                    edSearchConversation.setText("");
                    edSearchConversation.setVisibility(View.GONE);
                    btnHeaderEdit.setVisibility(View.GONE);
                    imgLogo.setVisibility(View.VISIBLE);
                    if (tab.getPosition() == 0) {
                        appTextView.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_menu_active, 0, 0);
                    } else if (tab.getPosition() == 1) {
                        btnHeaderSearch.setVisibility(View.VISIBLE);
                        mFab.setVisibility(View.VISIBLE);
                        appTextView.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_menu_conversation_active, 0, 0);
                    } else if (tab.getPosition() == 2) {
                        appTextView.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_promotion_h, 0, 0);
                    } else {
                        btnHeaderEdit.setVisibility(View.VISIBLE);
                        appTextView.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_menu_info_active, 0, 0);
                    }


                    mViewPager.setCurrentItem(tab.getPosition());
                    if (tab.getPosition() == 2) {
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                try {
                    TextView tv = (TextView) tab.getCustomView();
                    tv.setTextColor(getResources().getColor(R.color.menu_text_normal));
                    if (tab.getPosition() == 0) {
                        tv.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_menu_normal, 0, 0);
                    } else if (tab.getPosition() == 1) {
                        btnHeaderSearch.setVisibility(View.GONE);
                        mFab.setVisibility(View.GONE);
                        tv.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_menu_conversation_normal, 0, 0);
                    } else if (tab.getPosition() == 2) {
                        tv.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_promotion, 0, 0);
                    } else {
                        tv.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_menu_info_normal, 0, 0);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        mTabLayout.getTabAt(2).select();
    }

    private void createTabIcons(int index, int isResIcon, String title) {
        TextView tv = new TextView(getApplicationContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(layoutParams);
        tv.setTextColor(getResources().getColor(R.color.menu_text_normal));
        tv.setText(title);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        tv.setCompoundDrawablesWithIntrinsicBounds(0, isResIcon, 0, 0);
        mTabLayout.getTabAt(index).setCustomView(tv);
    }
}
