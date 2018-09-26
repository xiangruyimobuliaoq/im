package net.wrappy.im.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.yalantis.ucrop.UCrop;

import net.wrappy.im.BaseActivity;
import net.wrappy.im.BaseFragment;
import net.wrappy.im.R;
import net.wrappy.im.contants.ConsUtils;
import net.wrappy.im.ui.fragment.EmptyFragment;
import net.wrappy.im.ui.fragment.ProfileFragment;
import net.wrappy.im.ui.view.Layout;
import net.wrappy.im.ui.view.NoScrollViewPager;
import net.wrappy.im.util.AppFuncs;
import net.wrappy.im.util.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public static ImageButton btnHeaderEdit;
//    @BindView(R.id.btnHeaderEdit)
//    ImageButton btnHeaderEdit;
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
//    @BindView(R.id.fab)
//    FloatingActionButton mFab;
    private FragmentManager fragmentManager;
    private Adapter adapter;
    private Map<Integer, Fragment> mCache = new HashMap<>();
    public String MODEL = "";

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
        btnHeaderEdit = findViewById(R.id.btnHeaderEdit);
        btnHeaderEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AppFuncs.dismissKeyboard(MainActivity.this);
                    ProfileFragment page = (ProfileFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + mViewPager.getId() + ":" + mViewPager                               .getCurrentItem());
                    page.onDataEditChange(true);
                    btnHeaderEdit.setVisibility(View.GONE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void initViewPager() {
        fragmentManager = getSupportFragmentManager();
        adapter = new Adapter(fragmentManager);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mViewPager.setCurrentItem(2);
        mViewPager.setOffscreenPageLimit(1);
    }

    public class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();
        private final List<Integer> mFragmentIcons = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title, int icon) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
            mFragmentIcons.add(icon);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new EmptyFragment();
            } else if (position == 1) {
                return new EmptyFragment();
            } else if (position == 2) {
                return new EmptyFragment();
            } else {
                return new ProfileFragment();
            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }

        @Override
        public int getItemPosition(Object object) {
            if (object instanceof BaseFragment) {
                ((BaseFragment) object).reloadFragment();
            }
            return super.getItemPosition(object);
        }
    }

    private void initTabLayout() {

        // main menu tab
        TabLayout.Tab tab;
        for (int i = 0; i < 4; i++) {
            tab = mTabLayout.newTab();
            mTabLayout.addTab(tab);
        }
//        createTabIcons(0, R.mipmap.ic_menu_normal, getString(R.string.tab_menu_menu));
        createTabIcons(0, R.mipmap.ic_promotion, getString(R.string.tab_menu_contact));
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
                        MODEL = ConsUtils.MAIN_PROMOTION;
                        appTextView.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_menu_active, 0, 0);
                    } else if (tab.getPosition() == 1) {
                        btnHeaderSearch.setVisibility(View.VISIBLE);
                        MODEL = ConsUtils.MAIN_CHAT;
//                        mFab.setVisibility(View.VISIBLE);
                        appTextView.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_menu_conversation_active, 0, 0);
                    } else if (tab.getPosition() == 2) {
                        MODEL = ConsUtils.MAIN_PROMOTION;
                        appTextView.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_promotion_h, 0, 0);
                    } else {
                        MODEL = ConsUtils.MAIN_MY_PAGE;
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
//                        mFab.setVisibility(View.GONE);
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

    @Override
    public void onResultPickerImage(boolean isAvatar, Intent data, boolean isSuccess) {
        super.onResultPickerImage(isAvatar, data, isSuccess);
        ProfileFragment page = (ProfileFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + mViewPager.getId() + ":" + mViewPager.getCurrentItem());
        page.onResultPickerImage(isAvatar, data, isSuccess);
    }
}
