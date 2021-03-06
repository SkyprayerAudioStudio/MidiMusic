package com.jfleischer.midimusic.views;

import com.jfleischer.midimusic.R;
import com.jfleischer.midimusic.audio.SoundManager;
import com.jfleischer.midimusic.fragments.DrumFragment;
import com.jfleischer.midimusic.model.DrumSound;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DrumPad extends RelativeLayout{

	private	ImageView wrench;
	private TextView label;
	private RelativeLayout base;

	private DrumSound drumSound;
	private boolean isEditMode;

	public DrumPad(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void init(DrumSound ds, boolean isEditM){
		drumSound = ds;
		isEditMode = isEditM;
		wrench = findViewById(R.id.drum_pad_wrench);
		base = findViewById(R.id.drum_pad_base);
		label = findViewById(R.id.drum_pad_label);
		label.setText(drumSound.getName());
		setOnTouch();
	}

	public void setMode(boolean mode){

		isEditMode = mode;
		setOnTouch();
	}
	public void setSound(DrumSound ds){
		drumSound = ds;
		label.setText(drumSound.getName());
		setOnTouch();
	}

	private void setOnTouch(){
		if(isEditMode){
			wrench.setVisibility(View.VISIBLE);
			setOnTouchListener(null);
			setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					DrumFragment.deploySpinner(drumSound);
				}
			});
		}else{
			wrench.setVisibility(View.GONE);
			setOnClickListener(null);
			setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					int action = event.getAction();
					v.performClick();
					if(action == MotionEvent.ACTION_UP){
						base.setBackgroundResource(R.drawable.container_shape);
						return true;
					}
					else if(action == MotionEvent.ACTION_CANCEL){
						base.setBackgroundResource(R.drawable.container_shape);
						return true;
					}
					else if(action == MotionEvent.ACTION_DOWN){
						base.setBackgroundResource(R.drawable.drum_shape_pressed);
						SoundManager.getInstance().playDrumSound(drumSound.getSoundID());
						return true;
					}
					return false;
				}
			});
		}
	}
}
