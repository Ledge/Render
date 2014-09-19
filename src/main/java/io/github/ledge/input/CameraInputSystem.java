package io.github.ledge.input;

import io.github.ledge.render.camera.ICamera;

import javax.vecmath.Vector3f;

public class CameraInputSystem {

    private ICamera camera;

    public ICamera getCamera() {
        return this.camera;
    }

    public void setCamera(ICamera camera) {
        this.camera = camera;
    }

    public void update(Vector3f direction) {

    }
}
