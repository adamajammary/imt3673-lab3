package com.ntnu.imt3673.imt3673_lab3;

import android.content.Context;
import android.media.SoundPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Sound Manager
 */
public final class SoundManager {

    private static Map       sounds = new HashMap<Integer, Integer>();
    private static SoundPool soundPool;

    /**
     * Sets up the sound pool.
     * @param resources List of resource IDs
     * @param context Context
     */
    public static void init(final ArrayList<Integer> resources, final Context context) {
        SoundManager.soundPool = new SoundPool.Builder().setMaxStreams(2).build();

        for (Integer resID : resources)
            sounds.put(resID, SoundManager.soundPool.load(context, resID, 1));
    }

    /**
     * Plays the selected sound effect.
     * NB! Must first be initialized with SoundManager.init(ArrayList<Integer>, Context)
     * @param resourceID Resource ID
     */
    public static void playSound(final int resourceID, final Context context) {
        if (SoundManager.soundPool != null) {
            int id = (int)sounds.get(resourceID);

            SoundManager.soundPool.stop(id);
            SoundManager.soundPool.play(id, 1.0f, 1.0f, 1, 0, 1.0f);
        }
    }

}
