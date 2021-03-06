package Messages;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.thomas.kotr_android.ArcadeModeActivity;
import com.example.thomas.kotr_android.MainMenuActivity;
import com.example.thomas.kotr_android.R;
import com.example.thomas.kotr_android.TimeAttackModeActivity;

import Gameplay.Sound.MediaPlayerPlayer;

/**
 * Created by jeanine on 5/22/17.
 */

public class ReplayDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        final Bundle bundle = getArguments();
        int score = bundle.getInt("SCORE");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppAlertTheme);

        // Add the buttons
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked 'replay'

                MediaPlayerPlayer mediaPlayerPlayer = new MediaPlayerPlayer();
                mediaPlayerPlayer.pauseAudio();

                Intent intent = null;
                if (bundle.getString("MODE").equals("arcade")) {
                    intent = new Intent(getContext(), ArcadeModeActivity.class);
                }
                else if (bundle.getString("MODE").equals("timeAttack")) {
                    intent = new Intent(getContext(), TimeAttackModeActivity.class);
                }
                startActivity(intent);
            }
        });
        builder.setNeutralButton(R.string.returnToMenu, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked 'go back to menu'

                MediaPlayerPlayer mediaPlayerPlayer = new MediaPlayerPlayer();
                mediaPlayerPlayer.stopAudio();
                mediaPlayerPlayer.length = 0;
                Intent intent = new Intent(getContext(), MainMenuActivity.class);
                startActivity(intent);
            }
        });

        builder.setTitle("Score: " + score)
                .setMessage(R.string.replayPrompt);
        // Set other dialog properties

        // Create the AlertDialog
        Dialog dialog = builder.create();

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }
}
