package io.github.ledge.render.camera;

import io.github.ledge.utils.MatrixUtil;

import javax.vecmath.Vector3f;
import java.util.ArrayDeque;
import java.util.Deque;

import static org.lwjgl.opengl.GL11.*;

public class PerspectiveCamera extends Camera {

    // values used from smoother movement
    private Deque<Vector3f> previousPositions = new ArrayDeque<>();
    private Deque<Vector3f> previousViewingDirections = new ArrayDeque<>();

    @Override
    public void loadProjectionMatrix() {
        glMatrixMode(GL_PROJECTION);
        glLoadMatrix(MatrixUtil.toFloatBuffer(getProjectionMatrix()));
        glMatrixMode(GL_MODELVIEW);
    }

    @Override
    public void loadViewMatrix() {
        glMatrixMode(GL_MODELVIEW);
        glLoadMatrix(MatrixUtil.toFloatBuffer(getViewMatrix()));
    }

    @Override
    public void loadNormalizedModelViewMatrix() {
        glMatrixMode(GL_MODELVIEW);
        glLoadMatrix(MatrixUtil.toFloatBuffer(getNormalizedModelViewMatrix()));
    }

    @Override
    public void update(float interpolation) {
        double currDiff = Math.abs(super.activeFov - super.targetFov);
        if (currDiff < 1.0) {
            super.activeFov = super.targetFov;
        } else if (super.activeFov < super.targetFov) {

        }

        calculateViewMatrix(this.targetFov);
    }

    private void calculateViewMatrix(float fov) {
        // Apply all the things!
        updateFrustum();
    }
}
