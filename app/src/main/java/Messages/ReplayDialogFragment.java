package Messages;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.Button;

import com.example.thomas.kotr_android.MainActivity;
import com.example.thomas.kotr_android.MainMenuActivity;
import com.example.thomas.kotr_android.R;

/**
 * Created by jeanine on 5/22/17.
 */

public class ReplayDialogFragment extends DialogFragment {

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup oontainer,
//                             Bundle savedInstanceState) {
//        return getActivity().getLayoutInflater().inflate(R.layout.)
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        int score = bundle.getInt("SCORE");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppAlertTheme);

        // Add the buttons
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked 'replay'

                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setNeutralButton(R.string.returnToMenu, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked 'go back to menu'

                Intent intent = new Intent(getContext(), MainMenuActivity.class);
                startActivity(intent);
            }
        });

        builder.setTitle("Score: " + score)
                .setMessage(R.string.replayPrompt);
        // Set other dialog properties

        // Create the AlertDialog
        Dialog dialog = builder.create();


//        // Make some UI changes for AlertDialog
//        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(final DialogInterface dialog) {
//
//                // Add or create your own background drawable for AlertDialog window
//                Window view = ((AlertDialog)dialog).getWindow();
////                view.setBackgroundDrawableResource(R.drawable.your_drawable);
//
//                // Customize POSITIVE, NEGATIVE and NEUTRAL buttons.
//                Button positiveButton = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_POSITIVE);
////                positiveButton.setTextColor(getResources().getColor(R.color.colorPrimary));
//                positiveButton.setTypeface(Typeface.DEFAULT);
//                positiveButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
////                positiveButton.setTypeface(Typeface.DEFAULT_BOLD);
//                positiveButton.invalidate();
//
//                Button neutralButton = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_NEUTRAL);
////                neutralButton.setTextColor(getResources().getColor(R.color.colorPrimary));
//                neutralButton.setTypeface(Typeface.DEFAULT);
//                neutralButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
////                neutralButton.setTypeface(Typeface.DEFAULT_BOLD);
//                neutralButton.invalidate();
//            }
//        });

        return dialog;
    }
}
