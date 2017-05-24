package Gameplay.Sound;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by jeanine on 5/24/17.
 */

public class MediaPlayerPlayer {

    public static MediaPlayer mediaPlayer;

    public static boolean isPlayingAudio = false;

    public static int length;

    public static void createAudio(Context c, int id) {
        mediaPlayer = MediaPlayer.create(c, id);
    }

    public static void playAudio(Context c, int id) {

        if(!mediaPlayer.isPlaying())
        {
            isPlayingAudio = true;
            mediaPlayer.start();
        }
    }

    public static void setLooping(boolean loop) {
        mediaPlayer.setLooping(loop);
    }

    public static void stopAudio(){
        isPlayingAudio = false;
        mediaPlayer.stop();
    }

    public static void pauseAudio() {
        isPlayingAudio = false;
        mediaPlayer.pause();
        length = mediaPlayer.getCurrentPosition();
    }

    public static void resumePlayingAudio() {
        isPlayingAudio = true;
        mediaPlayer.seekTo(length);
        mediaPlayer.start();
    }
}
