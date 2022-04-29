 package the.winchesters.taxiii.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import the.winchesters.taxiii.LoginAdapter;
import the.winchesters.taxiii.R;

 public class LoginActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionButton facebook,google,twitter;
    float opacity=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        initializeComponents();
        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Sign up"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final LoginAdapter loginAdapter = new LoginAdapter(getSupportFragmentManager(),this, tabLayout.getTabCount());
        viewPager.setAdapter(loginAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        facebook.setTranslationY(300);
        twitter.setTranslationY(300);
        google.setTranslationY(300);
        tabLayout.setTranslationY(300);

        facebook.setAlpha(opacity);
        twitter.setAlpha(opacity);
        google.setAlpha(opacity);
        tabLayout.setAlpha(opacity);

        google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        facebook.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        twitter.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
    }

     private void initializeComponents() {
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        facebook = findViewById(R.id.fab_fck);
        twitter =findViewById(R.id.fab_twitr);
        google = findViewById(R.id.fab_goog);
     }
 }