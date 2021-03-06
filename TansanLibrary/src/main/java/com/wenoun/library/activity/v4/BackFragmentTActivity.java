package com.wenoun.library.activity.v4;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wenoun.library.R;
import com.wenoun.library.image.ImageUtils;

import java.util.ArrayList;

/**
 * Created by jeyhoon on 16. 2. 7..
 */
public class BackFragmentTActivity extends FragmentActivity {
    protected Context ctx=null;

    private LinearLayout menuRoot=null;
    private LinearLayout mainRoot=null;
    private LinearLayout menuBarRoot=null;

    private FragmentManager fragmentManager=null;

    private int customButtonCnt=0;

    private ArrayList<String> titleList=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx=this;
        super.setContentView(R.layout.layout_back_fragmentv4_tactivity);
        fragmentManager = getSupportFragmentManager();
        menuRoot=(LinearLayout)findViewById(R.id.back_fragment_menu_root);
        mainRoot=(LinearLayout)findViewById(R.id.back_fragment_container);
        menuBarRoot=(LinearLayout)findViewById(R.id.back_fragment_menu_bar_root);
        setTitle("");
        findViewById(R.id.back_fragment_back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void setFragment(Fragment fragment){
        setFragment(fragment,"");
    }
    public void setFragment(Fragment fragment,String title){
        addTitle(title);
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.back_fragment_container, fragment);
        fragmentTransaction.commit();
    }
    public void addFragment(Fragment fragment, String title){
        addTitle(title);
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.back_fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        removeCustomButton();
    }
    public void addFragment(Fragment fragment){
        addFragment(fragment,"");
    }

    public void removeCustomButton(){
        menuRoot.removeAllViews();
    }

    public void addTitle(String title){
        titleList.add(title);
        setTitle(title);
    }
    public void setTitle(String title){
        ((TextView)findViewById(R.id.back_fragment_title)).setText(title);
    }

    public void addMenuButton(View v,View.OnClickListener listener){
        v.setOnClickListener(listener);
        addMenuButton(v);
    }

    public void addMenuButton(View v){
        if(null!=menuRoot){
            menuRoot.addView(v);
            customButtonCnt++;
        }
    }

    public void addMenuButton(String menu,float textSize,int textColor,View.OnClickListener listener){
        addMenuButton(getMenuButton(menu,textSize,textColor),listener);
    }

    public void addMenuButton(String menu,View.OnClickListener listener){
        addMenuButton(getMenuButton(menu),listener);
    }

    public View getMenuButton(String menu){
        return getMenuButton(menu,20, Color.WHITE);
    }
    public View getMenuButton(String menu,float textSize,int textColor){
        TextView item=new TextView(ctx);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight=0;
        item.setLayoutParams(params);
        item.setTextSize(textSize);
        item.setTextColor(textColor);
        item.setText(menu);
        item.setGravity(Gravity.CENTER);
        final int dip10= ImageUtils.dpToPx(ctx,10);
        item.setPadding(dip10,0,dip10,0);
        return item;
    }

    @Override
    public void onBackPressed() {
        if(fragmentManager.getBackStackEntryCount()>0) {
            fragmentManager.popBackStack();
            try {
                titleList.remove(titleList.size() - 1);
                setTitle(titleList.get(titleList.size() - 1));
            }catch(Exception e){}
        }else
            super.onBackPressed();
    }
    private LinearLayout barRoot=null;
    public void setBarLayout(int layoutID){
        setBarLayout(getLayoutInflater().inflate(layoutID,null));
    }
    public void setBarLayout(View v){
        if(null==barRoot)
            barRoot=(LinearLayout)findViewById(R.id.back_fragment_bar);
        if(null!=barRoot) {
            barRoot.removeAllViews();
            LinearLayout.LayoutParams vParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            vParams.weight=1;
            v.setLayoutParams(vParams);
            barRoot.addView(v);
        }
    }
    public void removeBarLayout(){
        if(null==barRoot)
            barRoot=(LinearLayout)findViewById(R.id.back_fragment_bar);
        if(null!=barRoot) {
            barRoot.removeAllViews();
        }
    }
    public void setMenuBar(int layoutResId){
        menuBarRoot.removeAllViews();
        menuBarRoot.addView(getLayoutInflater().inflate(layoutResId,null));
    }
    public void setMenuBarBackgroundColor(int color){
        menuBarRoot.setBackgroundColor(color);
    }
    public void setMenuBarBackgroundResources(int resId){
        menuBarRoot.setBackgroundResource(resId);
    }

}
