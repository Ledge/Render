package io.github.ledge.render.tesselator;

public interface ITesselator {

    public void draw();

    public void startDrawingQuads();

    public void startDrawing(int type);
}
