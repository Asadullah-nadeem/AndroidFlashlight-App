package com.example.flashlightapp;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraCharacteristics;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private CameraManager cameraManager;
    private String cameraId;
    private boolean isFlashOn = false;
    private ImageView flashlightIcon;
    private Button toggleFlashlightButton;
    private SeekBar brightnessSeekBar;

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

        flashlightIcon = findViewById(R.id.flashlight_icon);
        toggleFlashlightButton = findViewById(R.id.toggle_flashlight_button);
        brightnessSeekBar = findViewById(R.id.brightness_seekbar);

        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        toggleFlashlightButton.setOnClickListener(v -> toggleFlashlight());

        brightnessSeekBar.setMax(100);
        brightnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                adjustBrightness(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void toggleFlashlight() {
        try {
            if (isFlashOn) {
                cameraManager.setTorchMode(cameraId, false);
                flashlightIcon.setImageResource(R.drawable.flashoff); // Off icon
                flashlightIcon.setColorFilter(getResources().getColor(R.color.white));
                isFlashOn = false;
                brightnessSeekBar.setEnabled(false);
            } else {
                cameraManager.setTorchMode(cameraId, true);
                flashlightIcon.setImageResource(R.drawable.flashon); // On icon
                flashlightIcon.setColorFilter(getResources().getColor(R.color.yellow));
                isFlashOn = true;
                brightnessSeekBar.setEnabled(true);
                brightnessSeekBar.setProgress(100); // Set default to 100%
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void adjustBrightness(int progress) {

        if (isFlashOn) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFlashOn) {
            toggleFlashlight();
        }
    }
}
