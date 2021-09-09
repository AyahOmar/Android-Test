package com.datechnologies.androidtest.animation;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

/**
 * Screen that displays the D & A Technologies logo.
 * The icon can be moved around on the screen as well as animated.
 */

public class AnimationActivity extends AppCompatActivity {
    private ImageView img;
    Button fadeInButton;
    private KonfettiView konfettiView;

    String msg = "AnimationActivity";
    ConstraintLayout constraintLayout;

    //==============================================================================================
    // Class Properties
    //==============================================================================================

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context) {
        Intent starter = new Intent(context, AnimationActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);


        img = (ImageView) findViewById(R.id.logo);
        fadeInButton = (Button) findViewById(R.id.fadeIn);
        konfettiView = findViewById(R.id.viewKonfetti);
        constraintLayout = findViewById(R.id.constraintLayout);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Animation");


        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.   //Done
        // TODO: Add a ripple effect when the buttons are clicked  //Done

        // TODO: When the fade button is clicked, you must animate the D & A Technologies logo.    //Done
        // TODO: It should fade from 100% alpha to 0% alpha, and then from 0% alpha to 100% alpha  //Done

        // TODO: The user should be able to touch and drag the D & A Technologies logo around the screen.   //Done

        // TODO: Add a bonus to make yourself stick out. Music, color, fireworks, explosions!!!           //DOne


        //==============================================================================================
        // Fade from 100% alpha to 0% alpha, and then from 0% alpha to 100% alpha and fireworks
        //==============================================================================================

        fadeInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation1 =
                        AnimationUtils.loadAnimation(getApplicationContext(),
                                R.anim.fade_in);
                img.startAnimation(animation1);
                konfettiView.build()
                        .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                        .setDirection(0.0, 359.0)
                        .setSpeed(1f, 5f)
                        .setFadeOutEnabled(true)
                        .setTimeToLive(2000L)
                        .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE)
                        .addSizes(new Size(12, 5f))
                        .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                        .streamFor(300, 5000L);


            }
        });


        //==============================================================================================
        // Drag the D & A Technologies logo around the screen.
        //==============================================================================================
        constraintLayout.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DROP:
                        Log.d(msg, "ACTION_DROP event");
                        View tvState = (View) event.getLocalState();
                        Log.d(msg, "onDrag:viewX" + event.getX() + "viewY" + event.getY());
                        Log.d(msg, "onDrag: Owner-&gt;" + tvState.getParent());
                        ViewGroup tvParent = (ViewGroup) tvState.getParent();
                        tvParent.removeView(tvState);
                        ConstraintLayout container = (ConstraintLayout) v;
                        container.addView(tvState);
                        tvParent.removeView(tvState);
                        tvState.setX(event.getX());
                        tvState.setY(event.getY());
                        ((ConstraintLayout) v).addView(tvState);
                        v.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });


        img.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(img);
                    v.startDrag(data, shadowBuilder, v, 0);
                    v.setVisibility(View.VISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        });

    }

    //==============================================================================================
    // Handle back button in action bar
    //==============================================================================================
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return true;
    }

}
