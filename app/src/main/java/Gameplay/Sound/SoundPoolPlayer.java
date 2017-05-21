package Gameplay.Sound;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.example.thomas.kotr_android.R;

import java.util.HashMap;

/**
 * Created by jeanine on 5/19/17.
 */

public class SoundPoolPlayer {
    private SoundPool soundPool = null;
    private HashMap sounds = new HashMap();

    public SoundPoolPlayer(Context context)
    {
        // setup SoundPool based on API level
        createSoundPool();

        // load sounds
        loadResources(context);

    }

    protected void createSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
        } else {
            createOldSoundPool();
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void createNewSoundPool(){
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    protected void createOldSoundPool(){
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
    }

    public void playShortResource(int piResource) {
        int iSoundId = (Integer) sounds.get(piResource);
        this.soundPool.play(iSoundId, 0.99f, 0.99f, 0, 0, 1);
    }

    private void loadResources(Context context) {
        sounds.put(R.raw.correct, this.soundPool.load(context, R.raw.correct, 1));
        sounds.put(R.raw.incorrect, this.soundPool.load(context, R.raw.incorrect, 1));
    }

    // Cleanup
    public void release() {
        // Cleanup
        this.soundPool.release();
        this.soundPool = null;
    }
}
