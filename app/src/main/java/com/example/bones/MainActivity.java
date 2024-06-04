package com.example.bones;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView diceImageView;
    private TextView resultTextView;
    private int currentDiceResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        diceImageView = findViewById(R.id.dice_image_view);
        Button rollButton = findViewById(R.id.roll_button);
        resultTextView = findViewById(R.id.result_text_view);

        rollButton.setOnClickListener(v -> rollDice());
    }

    private void rollDice() {
        currentDiceResult = new Random().nextInt(6) + 1;

        animateDiceRoll();
    }

    private void animateDiceRoll() {
        ObjectAnimator rotateX = ObjectAnimator.ofFloat(diceImageView, "rotationX", 0f, 360f);
        rotateX.setDuration(500);
        rotateX.setRepeatCount(1);
        rotateX.setRepeatMode(ObjectAnimator.REVERSE);

        ObjectAnimator rotateY = ObjectAnimator.ofFloat(diceImageView, "rotationY", 0f, 360f);
        rotateY.setDuration(1000);
        rotateY.setRepeatCount(1);
        rotateY.setRepeatMode(ObjectAnimator.REVERSE);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rotateX, rotateY);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                updateDiceImage(currentDiceResult);
                updateResultText(currentDiceResult);
            }
        });
        animatorSet.start();
    }

    private void updateDiceImage(int result) {
        int imageResource = getResources().getIdentifier("dice_" + result, "drawable", getPackageName());
        diceImageView.setImageResource(imageResource);
    }

    private void updateResultText(int result) {
        resultTextView.setText(String.valueOf(result));
    }
}

