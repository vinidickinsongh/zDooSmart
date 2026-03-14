package com.zdoo.smart;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class MainActivity extends AppCompatActivity {
    private static final long SPLASH_DELAY_MILLIS = 2_000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Line 23 to 27 are only used for testing purposes.
        // SplashScreen.installSplashScreen(this);
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
    }
}