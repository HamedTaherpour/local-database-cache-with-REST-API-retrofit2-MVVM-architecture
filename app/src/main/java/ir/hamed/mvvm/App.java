package ir.hamed.mvvm;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/SourceCodePro-Light.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        setInstance(this);
    }

    private static synchronized void setInstance(App app) {
        instance = app;
    }

    public static App getAppContext() {
        return instance;
    }
}
