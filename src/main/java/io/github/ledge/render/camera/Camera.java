package io.github.ledge.render.camera;

import io.github.ledge.utils.MatrixUtil;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

public abstract class Camera implements ICamera {

    protected Vector3f position = new Vector3f(0, 0, 0);
    protected Vector3f up = new Vector3f(0, 1, 0);
    protected Vector3f direction = new Vector3f(1, 0, 0);

    protected Matrix4f projection = new Matrix4f();
    protected Matrix4f view = new Matrix4f();

    protected float near = 0;
    protected float far = 100;

    protected float targetFov = 100; // TODO: make configuration
    protected float activeFov = targetFov / 4;

    protected ViewFrustum viewFrustum = new ViewFrustum();

    @Override
    public void lookTrough() {
        loadProjectionMatrix();
        loadViewMatrix();
    }

    @Override
    public void updateFrustum() {
        if (this.view == null || this.projection == null)
            return;

        this.viewFrustum.update(MatrixUtil.toFloatBuffer(this.view), MatrixUtil.toFloatBuffer(this.projection));
    }

    public abstract void loadProjectionMatrix();

    public abstract void loadViewMatrix();

    public abstract void update();

    @Override
    public Vector3f getPosition() {
        return this.position;
    }

    @Override
    public Vector3f getUp() {
        return this.up;
    }

    @Override
    public Vector3f getViewDirection() {
        return this.direction;
    }

    @Override
    public ViewFrustum getViewFrustum() {
        return this.viewFrustum;
    }

    @Override
    public ICamera extendFov(float fov) {
        this.targetFov = this.targetFov + fov;
        return this;
    }

    @Override
    public Matrix4f getViewMatrix() {
        return this.view;
    }

    @Override
    public Matrix4f getProjectionMatrix() {
        return this.projection;
    }

    @Override
    public float getZFar() {
        return this.far;
    }

    @Override
    public void setZFar(float z) {
        this.far = z;
    }

    @Override
    public float getZNear() {
        return this.near;
    }

    @Override
    public void setZNear(float z) {
        this.near = z;
    }
}
