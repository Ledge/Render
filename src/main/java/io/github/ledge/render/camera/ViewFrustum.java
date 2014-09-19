package io.github.ledge.render.camera;

import io.github.ledge.common.math.collision.BoundingBox;
import org.lwjgl.BufferUtils;

import javax.vecmath.Vector3f;
import java.nio.FloatBuffer;

public class ViewFrustum {

    public final FrustumPlane[] planes = new FrustumPlane[6];
    private FloatBuffer clip = BufferUtils.createFloatBuffer(16);

    public ViewFrustum() {
        for(int i = 0; i < 6; i++) {
            this.planes[i] = new FrustumPlane();
        }
    }

    /**
     * Borrowed from https://github.com/MovingBlocks/Terasology/blob/develop/engine/src/main/java/org/terasology/rendering/cameras/ViewFrustum.java#L48
     * @param viewMatrix
     * @param projectionMatrix
     */
    public void update(FloatBuffer viewMatrix, FloatBuffer projectionMatrix) {
        clip.put(0, viewMatrix.get(0) * projectionMatrix.get(0) + viewMatrix.get(1) * projectionMatrix.get(4)
                + viewMatrix.get(2) * projectionMatrix.get(8) + viewMatrix.get(3) * projectionMatrix.get(12));
        clip.put(1, viewMatrix.get(0) * projectionMatrix.get(1) + viewMatrix.get(1) * projectionMatrix.get(5)
                + viewMatrix.get(2) * projectionMatrix.get(9) + viewMatrix.get(3) * projectionMatrix.get(13));
        clip.put(2, viewMatrix.get(0) * projectionMatrix.get(2) + viewMatrix.get(1) * projectionMatrix.get(6)
                + viewMatrix.get(2) * projectionMatrix.get(10) + viewMatrix.get(3) * projectionMatrix.get(14));
        clip.put(3, viewMatrix.get(0) * projectionMatrix.get(3) + viewMatrix.get(1) * projectionMatrix.get(7)
                + viewMatrix.get(2) * projectionMatrix.get(11) + viewMatrix.get(3) * projectionMatrix.get(15));

        clip.put(4, viewMatrix.get(4) * projectionMatrix.get(0) + viewMatrix.get(5) * projectionMatrix.get(4)
                + viewMatrix.get(6) * projectionMatrix.get(8) + viewMatrix.get(7) * projectionMatrix.get(12));
        clip.put(5, viewMatrix.get(4) * projectionMatrix.get(1) + viewMatrix.get(5) * projectionMatrix.get(5)
                + viewMatrix.get(6) * projectionMatrix.get(9) + viewMatrix.get(7) * projectionMatrix.get(13));
        clip.put(6, viewMatrix.get(4) * projectionMatrix.get(2) + viewMatrix.get(5) * projectionMatrix.get(6)
                + viewMatrix.get(6) * projectionMatrix.get(10) + viewMatrix.get(7) * projectionMatrix.get(14));
        clip.put(7, viewMatrix.get(4) * projectionMatrix.get(3) + viewMatrix.get(5) * projectionMatrix.get(7)
                + viewMatrix.get(6) * projectionMatrix.get(11) + viewMatrix.get(7) * projectionMatrix.get(15));

        clip.put(8, viewMatrix.get(8) * projectionMatrix.get(0) + viewMatrix.get(9) * projectionMatrix.get(4)
                + viewMatrix.get(10) * projectionMatrix.get(8) + viewMatrix.get(11) * projectionMatrix.get(12));
        clip.put(9, viewMatrix.get(8) * projectionMatrix.get(1) + viewMatrix.get(9) * projectionMatrix.get(5)
                + viewMatrix.get(10) * projectionMatrix.get(9) + viewMatrix.get(11) * projectionMatrix.get(13));
        clip.put(10, viewMatrix.get(8) * projectionMatrix.get(2) + viewMatrix.get(9) * projectionMatrix.get(6)
                + viewMatrix.get(10) * projectionMatrix.get(10) + viewMatrix.get(11) * projectionMatrix.get(14));
        clip.put(11, viewMatrix.get(8) * projectionMatrix.get(3) + viewMatrix.get(9) * projectionMatrix.get(7)
                + viewMatrix.get(10) * projectionMatrix.get(11) + viewMatrix.get(11) * projectionMatrix.get(15));

        clip.put(12, viewMatrix.get(12) * projectionMatrix.get(0) + viewMatrix.get(13) * projectionMatrix.get(4)
                + viewMatrix.get(14) * projectionMatrix.get(8) + viewMatrix.get(15) * projectionMatrix.get(12));
        clip.put(13, viewMatrix.get(12) * projectionMatrix.get(1) + viewMatrix.get(13) * projectionMatrix.get(5)
                + viewMatrix.get(14) * projectionMatrix.get(9) + viewMatrix.get(15) * projectionMatrix.get(13));
        clip.put(14, viewMatrix.get(12) * projectionMatrix.get(2) + viewMatrix.get(13) * projectionMatrix.get(6)
                + viewMatrix.get(14) * projectionMatrix.get(10) + viewMatrix.get(15) * projectionMatrix.get(14));
        clip.put(15, viewMatrix.get(12) * projectionMatrix.get(3) + viewMatrix.get(13) * projectionMatrix.get(7)
                + viewMatrix.get(14) * projectionMatrix.get(11) + viewMatrix.get(15) * projectionMatrix.get(15));

        // RIGHT
        planes[0].setA(clip.get(3) - clip.get(0));
        planes[0].setB(clip.get(7) - clip.get(4));
        planes[0].setC(clip.get(11) - clip.get(8));
        planes[0].setD(clip.get(15) - clip.get(12));
        planes[0].normalize();

        // LEFT
        planes[1].setA(clip.get(3) + clip.get(0));
        planes[1].setB(clip.get(7) + clip.get(4));
        planes[1].setC(clip.get(11) + clip.get(8));
        planes[1].setD(clip.get(15) + clip.get(12));
        planes[1].normalize();

        // BOTTOM
        planes[2].setA(clip.get(3) + clip.get(1));
        planes[2].setB(clip.get(7) + clip.get(5));
        planes[2].setC(clip.get(11) + clip.get(9));
        planes[2].setD(clip.get(15) + clip.get(13));
        planes[2].normalize();

        // TOP
        planes[3].setA(clip.get(3) - clip.get(1));
        planes[3].setB(clip.get(7) - clip.get(5));
        planes[3].setC(clip.get(11) - clip.get(9));
        planes[3].setD(clip.get(15) - clip.get(13));
        planes[3].normalize();

        // FAR
        planes[4].setA(clip.get(3) - clip.get(2));
        planes[4].setB(clip.get(7) - clip.get(6));
        planes[4].setC(clip.get(11) - clip.get(10));
        planes[4].setD(clip.get(15) - clip.get(14));
        planes[4].normalize();

        // NEAR
        planes[5].setA(clip.get(3) + clip.get(2));
        planes[5].setB(clip.get(7) + clip.get(6));
        planes[5].setC(clip.get(11) + clip.get(10));
        planes[5].setD(clip.get(15) + clip.get(14));
        planes[5].normalize();
    }

    public boolean intersects(BoundingBox box) {
        for (int i = 0; i < 6; i++) {
            FrustumPlane plane = this.planes[i];

            if (plane.getA() * box.maxX() + plane.getB() * box.maxY() + plane.getC() * box.maxZ() + plane.getD() <= 0)
                return false;

            if (plane.getA() * box.minX() + plane.getB() * box.maxY() + plane.getC() * box.maxZ() + plane.getD() <= 0)
                return false;

            if (plane.getA() * box.maxX() + plane.getB() * box.minY() + plane.getC() * box.maxZ() + plane.getD() <= 0)
                return false;

            if (plane.getA() * box.maxX() + plane.getB() * box.maxY() + plane.getC() * box.minZ() + plane.getD() <= 0)
                return false;

            if (plane.getA() * box.minX() + plane.getB() * box.minY() + plane.getC() * box.maxZ() + plane.getD() <= 0)
                return false;

            if (plane.getA() * box.maxX() + plane.getB() * box.minY() + plane.getC() * box.minZ() + plane.getD() <= 0)
                return false;

            if (plane.getA() * box.minX() + plane.getB() * box.maxY() + plane.getC() * box.minZ() + plane.getD() <= 0)
                return false;

            if (plane.getA() * box.minX() + plane.getB() * box.minY() + plane.getC() * box.minZ() + plane.getD() <= 0)
                return false;

        }

        return true;
    }

    public boolean intersects(Vector3f position, float radius) {
        for (int i = 0; i < 6; i++) {
            FrustumPlane plane = this.planes[i];
            if (plane.getA() * position.x + plane.getB() * position.y + plane.getC() * position.z + plane.getD() >= -radius)
                return false;
        }

        return true;
    }
}
