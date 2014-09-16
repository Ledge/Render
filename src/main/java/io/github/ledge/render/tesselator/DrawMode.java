package io.github.ledge.render.tesselator;

import static org.lwjgl.opengl.GL11.*;

/**
 * An enum-wrapper around the default openGl drawModes.
 *
 * (see: https://github.com/LWJGL/lwjgl3-generated/blob/master/java/org/lwjgl/opengl/GL11.java#L78-L88)
 */
public enum DrawMode {

    POINTS(GL_POINTS),
    LINES(GL_LINES),
    LINE_LOOP(GL_LINE_LOOP),
    LINE_STRIP(GL_LINE_STRIP),
    TRIANGLES(GL_TRIANGLES),
    TRIANGLE_STRIP(GL_TRIANGLE_STRIP),
    TRIANGLE_FAN(GL_TRIANGLE_FAN),
    QUADS(GL_QUADS),
    QUAD_STRIP(GL_QUAD_STRIP),
    POLYGON(GL_POLYGON);


    private final int mode;

    DrawMode(int mode) {
        this.mode = mode;
    }

    public int mode() {
        return this.mode;
    }
}
