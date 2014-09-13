package io.github.ledge.render.camera;

import javax.vecmath.Vector3f;

public class Camera {

    protected final Vector3f position = new Vector3f(0, 0, 0);

    // ViewFrustum
    protected final ViewFrustum viewFrustum = new ViewFrustum();
}
