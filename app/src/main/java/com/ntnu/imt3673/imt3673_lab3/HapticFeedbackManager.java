package com.ntnu.imt3673.imt3673_lab3;

import android.content.Context;
import android.os.Vibrator;

/**
 * Haptic Feedback Manager
 */
final class HapticFeedbackManager {

    private static Vibrator vibrator;

    /**
     * Sets up haptic feedback using the vibrator if supported by the device.
     * @param context Context
     */
    public static void init(final Context context) {
        HapticFeedbackManager.vibrator = context.getSystemService(Vibrator.class);

        // Tell the user if the device does not support a vibrator
        if ((HapticFeedbackManager.vibrator == null) || !HapticFeedbackManager.vibrator.hasVibrator())
            Utils.alertMessage(context.getString(R.string.error_no_vibrator), context);
    }

    /**
     * Vibrates for the specified duration.
     * NB! Must first be initialized with HapticFeedbackManager.init(Context)
     * @param duration Duration in milliseconds
     */
    public static void vibrate(final long duration) {
        if (HapticFeedbackManager.vibrator != null)
            HapticFeedbackManager.vibrator.vibrate(duration);
    }
}
