package com.ntnu.imt3673.imt3673_lab3;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Helper utilities.
 */
public final class Utils {

    /**
     * Displays the message in a pop-up alert dialog.
     * @param message Message to display
     */
    public static final void alertMessage(final String message, final Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        dialog.setTitle(R.string.app_name);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setMessage(message);
        dialog.setPositiveButton("OK", (d, i) -> d.dismiss());
        dialog.show();
    }

}
