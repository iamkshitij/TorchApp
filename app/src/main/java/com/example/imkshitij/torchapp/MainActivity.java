package com.example.imkshitij.torchapp;

import android.graphics.Camera;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.security.Policy;


public class MainActivity extends ActionBarActivity {
    ToggleButton toggleButton;
    android.hardware.Camera camera=null;
    private boolean isFlashOn;
    private boolean hasCam;
   android.hardware.Camera.Parameters parameters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toggleButton= (ToggleButton) findViewById(R.id.onOff);
        getCamera();
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    turnOnFlash();
                }
                    else {
                    turnOffFlash();
                }

            }
        });

    }
    private void getCamera(){
        if(camera==null) {
            try {
                camera = android.hardware.Camera.open();
                android.hardware.Camera.Parameters parameters1 = camera.getParameters();
            } catch (RuntimeException e) {

            }
        }
    }
       private void turnOnFlash(){
           if(!isFlashOn){
               if(camera == null ){
                return;
               }
               parameters=camera.getParameters();
               parameters.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_TORCH);
               camera.setParameters(parameters);
               camera.startPreview();
               isFlashOn=true;
           }

       }
        private void turnOffFlash(){
            if(isFlashOn){
            if(camera == null ){
                return;
            }
            parameters=camera.getParameters();
            parameters.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.stopPreview();
            isFlashOn=false;
        }

        }
    protected void onDestroy(){
        super.onDestroy();
    }
    protected void onPause(){
        super.onPause();
        turnOffFlash();
    }
    protected void onResume(){
        super.onResume();
        turnOnFlash();
    }
    protected void onStop(){
        super.onStop();
        if(camera!=null)
            camera.release();
        camera=null;
    }
    protected void onRestart(){
        super.onRestart();
    }
    protected void onStart(){
        super.onStart();
        getCamera();
    }
}
