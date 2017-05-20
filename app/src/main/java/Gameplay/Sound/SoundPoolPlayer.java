package Gameplay.Sound;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.thomas.kotr_android.R;

import java.util.HashMap;

/**
 * Created by jeanine on 5/19/17.
 */

public class SoundPoolPlayer {
    private SoundPool mShortPlayer = null;
    private HashMap mSounds = new HashMap();

    public SoundPoolPlayer(Context pContext)
    {
        // setup Soundpool
        this.mShortPlayer = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);


        mSounds.put(R.raw.correct, this.mShortPlayer.load(pContext, R.raw.correct, 1));
        mSounds.put(R.raw.incorrect, this.mShortPlayer.load(pContext, R.raw.incorrect, 1));
    }

    public void playShortResource(int piResource) {
        int iSoundId = (Integer) mSounds.get(piResource);
        this.mShortPlayer.play(iSoundId, 0.99f, 0.99f, 0, 0, 1);
    }

    // Cleanup
    public void release() {
        // Cleanup
        this.mShortPlayer.release();
        this.mShortPlayer = null;
    }
}
