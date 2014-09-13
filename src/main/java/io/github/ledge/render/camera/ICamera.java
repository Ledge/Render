package io.github.ledge.render.camera;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

public interface ICamera {

    public void lookTrough();

    public void updateFrustum();

    public Vector3f getPosition();

    public Vector3f getUp();

    public Vector3f getViewDirection();

    public ViewFrustum getViewFrustum();

    public ICamera extendFov(float fov);

    public Matrix4f getViewMatrix();

    public Matrix4f getProjectionMatrix();

    public float getZFar();

    public void setZFar(float z);

    public float getZNear();

    public void setZNear(float z);
}
