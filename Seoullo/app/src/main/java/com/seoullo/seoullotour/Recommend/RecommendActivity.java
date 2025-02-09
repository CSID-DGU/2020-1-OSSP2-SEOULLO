package com.seoullo.seoullotour.Recommend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.seoullo.seoullotour.Models.Place;
import com.seoullo.seoullotour.R;
import com.seoullo.seoullotour.Utils.BottomNavigationViewHelper;
import com.seoullo.seoullotour.Utils.SectionsPagerAdapter;

import java.util.ArrayList;

public class RecommendActivity extends AppCompatActivity {

    private static final String TAG = "RecommendActivity";
    public static final int ACTIVITY_NUM = 0;
    private Context mContext = RecommendActivity.this;
    //widget
    private ViewPager mViewPager;
    private FrameLayout mFrameLayout;
    private RelativeLayout mRelativeLayout;
    private ImageButton mImageButton;

    //get intent
    private String getLocation;
    private ArrayList<Place> mPlaces = new ArrayList<>();
    private String UserId;
    private String ImageName;
    private String PhotoId;

    private Place mPlace = new Place();

    //TODO: link to fragment -> done
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        System.out.println("==================================Recommend Activity");
        //상단
        mFrameLayout = (FrameLayout) findViewById(R.id.container);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relLayout1);
        mImageButton = (ImageButton) findViewById(R.id.add_post);
        mImageButton.setVisibility(View.GONE);
        //중앙
        mViewPager = (ViewPager) findViewById(R.id.viewpager_container);

        Intent intent = getIntent();
        //location

        this.mPlace = (Place) intent.getSerializableExtra("firstPlace");

        ArrayList<Place> tempPlace = (ArrayList<Place>) intent.getSerializableExtra("places");
        if( tempPlace.size() != 0) Log.d(TAG,"tempPlace is not null!\n");
            this.mPlaces = (ArrayList<Place>) tempPlace.clone();
        //firebase info
        this.UserId = intent.getStringExtra("user_id");
        this.ImageName = intent.getStringExtra("image_name");
        this.PhotoId = intent.getStringExtra("photo_id");

        System.out.println("got Intent !");

        //TODO: 뷰페이저 vertical scrolling : 뷰페이저를 수직으로 땡길 수 있게끔 해보자 !

        setupBottomNavigationView();
        setupViewPager();
    }
    /**
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, this,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
    private void setupViewPager(){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new RecommendFragment(mPlace ,mPlaces, UserId, ImageName, PhotoId));

        mViewPager.setAdapter(adapter);
    }
    public void hideLayout(){
        Log.d(TAG, "hideLayout: hiding layout");
        mRelativeLayout.setVisibility(View.GONE);
        mFrameLayout.setVisibility(View.VISIBLE);
    }


    public void showLayout(){
        Log.d(TAG, "hideLayout: showing layout");
        mRelativeLayout.setVisibility(View.VISIBLE);
        mFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mFrameLayout.getVisibility() == View.VISIBLE){
            showLayout();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    public void onStop() {
        super.onStop();
    }
    @Override
    public void onRestart() {
        super.onRestart();
    }
    public void onDestroy() {
        super.onDestroy();
    }
}
