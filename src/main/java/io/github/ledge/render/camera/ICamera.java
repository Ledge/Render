package io.github.ledge.render.camera;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

public interface ICamera {

    public Vector3f getPosition();

    public Vector3f getViewDirection();

    public ICamera setFOV(float fov);

    public Matrix4f getViewMatrix();

    public Matrix4f getProjectionMatrix();

    public float getZFar();

    public void setZFar(float z);

    public float getZNear();

    public void setZNear(float z);
}
