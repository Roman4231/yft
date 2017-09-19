package com.yft.admin.myapplication.dialog;

import android.app.DialogFragment;
        import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.yft.admin.myapplication.R;
import com.yft.admin.myapplication.classes.ExerciseClass;

public class TrainingDetailsExerciseInfoDialog extends DialogFragment implements OnClickListener {

    VideoView video;
    ImageView image;
    ExerciseClass exercise;
    View v;
    TextView name;
    TextView text;
    String str;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        exercise=ExerciseClass.getExerciseByName(getTag());
        v = inflater.inflate(R.layout.training_details_exercise_info_dialog, null);
        video=(VideoView) v.findViewById(R.id.training_details_exercise_info_dialog_video);
        image=(ImageView) v.findViewById(R.id.training_details_exercise_info_dialog_plank_photo);
        name=(TextView) v.findViewById(R.id.training_details_exercise_info_dialog_name);
        text=(TextView) v.findViewById(R.id.training_details_exercise_info_dialog_text);
        name.setText(exercise.name);
        str=getString(exercise.info);
        text.setText(str);
        if(exercise.name.equals("Plank")){
            video.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
        }else {
            video.setZOrderOnTop(true);
            video.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + exercise.url1));
            video.setMediaController(null);
            video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    video.start();
                }
            });
            video.start();
        }
        return v;
    }

    public void onClick(View v) {
        dismiss();
    }

    public void onDismiss(DialogInterface dialog) {
        if(exercise.name.equals("Plank")){
            image.setVisibility(View.GONE);
            video.setVisibility(View.VISIBLE);
        }else{
            video.suspend();
        }
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        if(str.equals("Plank")){
            image.setVisibility(View.GONE);
            video.setVisibility(View.VISIBLE);
        }else{
            video.suspend();
        }
        super.onCancel(dialog);
    }
}