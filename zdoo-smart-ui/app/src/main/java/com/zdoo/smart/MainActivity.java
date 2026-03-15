package com.zdoo.smart;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final long SPLASH_DELAY_MILLIS = 2_000L;

    private ViewPager2 bannerViewPager;
    private LinearLayout indicatorLayout;
    private Button getStartedButton;
    private final List<ImageView> indicators = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        final long splashStartTime = SystemClock.uptimeMillis();
        splashScreen.setKeepOnScreenCondition(() ->
                SystemClock.uptimeMillis() - splashStartTime < SPLASH_DELAY_MILLIS
        );

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        boolean isDarkMode = (getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;

        WindowInsetsControllerCompat insetsController =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(!isDarkMode);
        insetsController.setAppearanceLightNavigationBars(!isDarkMode);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupBanner();
        setupGetStartedButton();
        setupLoginLink();
    }

    private void setupLoginLink() {
        // Login link → navigate to Login
        TextView tvLoginLink = findViewById(R.id.loginLink);
        tvLoginLink.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void setupGetStartedButton() {
        // Get Started button → navigate to Signup
        Button btnGetStarted = findViewById(R.id.btnGetStarted);
        btnGetStarted.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }

    private void setupBanner() {
        bannerViewPager = findViewById(R.id.bannerViewPager);
        indicatorLayout = findViewById(R.id.indicatorLayout);

        List<BannerItem> bannerItems = new ArrayList<>();
        bannerItems.add(new BannerItem(
                R.drawable.banner1,
                getString(R.string.banner1_title),
                getString(R.string.banner1_subtitle)));
        bannerItems.add(new BannerItem(
                R.drawable.banner2,
                getString(R.string.banner2_title),
                getString(R.string.banner2_subtitle)));
        bannerItems.add(new BannerItem(
                R.drawable.banner3,
                getString(R.string.banner3_title),
                getString(R.string.banner3_subtitle)));
        bannerItems.add(new BannerItem(
                R.drawable.banner4,
                getString(R.string.banner4_title),
                getString(R.string.banner4_subtitle)));
        bannerItems.add(new BannerItem(
                R.drawable.banner5,
                getString(R.string.banner5_title),
                getString(R.string.banner5_subtitle)));

        BannerAdapter adapter = new BannerAdapter(bannerItems);
        bannerViewPager.setAdapter(adapter);

        setupIndicators(bannerItems.size());
        updateIndicator(0);

        bannerViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateIndicator(position);
            }
        });
    }

    private void setupIndicators(int count) {
        indicatorLayout.removeAllViews();
        indicators.clear();

        for (int i = 0; i < count; i++) {
            ImageView dot = new ImageView(this);
            dot.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.indicator_inactive));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMarginStart(8);
            params.setMarginEnd(8);
            dot.setLayoutParams(params);

            indicatorLayout.addView(dot);
            indicators.add(dot);
        }
    }

    private void updateIndicator(int position) {
        for (int i = 0; i < indicators.size(); i++) {
            indicators.get(i).setImageDrawable(ContextCompat.getDrawable(this,
                    i == position ? R.drawable.indicator_active : R.drawable.indicator_inactive));
        }
    }
}