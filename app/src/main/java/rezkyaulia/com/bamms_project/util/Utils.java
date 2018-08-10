package rezkyaulia.com.bamms_project.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import rezkyaulia.com.bamms_project.di.application.ApplicationContext;

import static java.lang.Math.round;


/**
 * Created by Rezky Aulia Pratama on 15/7/18.
 */

public class Utils {

    private final Context context;

    public Utils(@ApplicationContext Context context) {
        this.context = context;
    }

    public String getUniqueID() {
        return getUniqueID(context, "");
    }

    @SuppressLint("HardwareIds")
    private String getUniqueID(Context context, String i) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "Error";
        }

        tmDevice = "" + Objects.requireNonNull(tm).getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString() + System.currentTimeMillis() + i;


        return UUID.nameUUIDFromBytes(deviceId.getBytes()).toString();
    }


    public Locale createLocale(String locale) {
        if (locale == null)
            return Locale.getDefault();
        else
            return new Locale(locale, Locale.getDefault().getCountry());
    }


    public Locale setLocale(String code) {
        Locale locale;


        if (code != null)
            locale = new Locale(code, Locale.getDefault().getCountry());
        else
            locale = Locale.getDefault();

        Locale.setDefault(locale);

        Configuration configuration = new Configuration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else
            configuration.locale = locale;

        context.getResources().updateConfiguration(
                configuration,
                context.getResources().getDisplayMetrics()
        );

        return locale;
    }

    public float convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return round(px);
    }
}
