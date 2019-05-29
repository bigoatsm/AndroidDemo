package com.bigoat.demo.cameraX;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.TextureView;

import androidx.camera.core.CameraX;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreviewConfig config = new PreviewConfig.Builder().build();
        Preview preview = new Preview(config);

        final TextureView textureView = findViewById(R.id.textureView);

        preview.setOnPreviewOutputUpdateListener(new Preview.OnPreviewOutputUpdateListener() {
                    @Override
                    public void onUpdated(Preview.PreviewOutput previewOutput) {
                        // The output data-handling is configured in a listener.
                        textureView.setSurfaceTexture(previewOutput.getSurfaceTexture());
                        // Your custom code here.
                    }
                });

        // The use case is bound to an Android Lifecycle with the following code.
        CameraX.bindToLifecycle(new CustomLifecycle(), preview);


    }

    public class CustomLifecycle implements LifecycleOwner {
        private LifecycleRegistry mLifecycleRegistry;
        public CustomLifecycle() {
            mLifecycleRegistry = new LifecycleRegistry(this);
            mLifecycleRegistry.markState(Lifecycle.State.CREATED);
        }
        public void doOnResume() {
            mLifecycleRegistry.markState(Lifecycle.State.RESUMED);
        }
        public Lifecycle getLifecycle() {
            return mLifecycleRegistry;
        }
    }

}
