package io.github.ledge.render.tesselator;

public interface ITesselator {

    public void draw();

    public void startDrawingQuads();

    public void startDrawing(DrawMode mode);

    public void setTextureUV(double u, double v);

    public void setBrightness(int brightness);

    public void disableColors();

    public void enableColors();

    public void setColorRGBA(int r, int g, int b, int a);

    public void addVertex(int x, int y, int z);

    public void addVertexUV(int x, int y, int z, double u, double v);

    public void setNormals(float n1, float n2, float n3);

    public void setTranslation(double x, double y, double z);

    public void addTranslation(double x, double y, double z);
}
