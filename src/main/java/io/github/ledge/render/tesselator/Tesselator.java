package io.github.ledge.render.tesselator;

import org.lwjgl.opengl.GL11;

import java.nio.*;

public class Tesselator implements ITesselator {

    private static final Tesselator instance = new Tesselator(0x200000);

    private int bufferSize;

    private boolean isDrawing = false;

    private DrawMode drawMode;

    private boolean hasTextures;
    private double textureU;
    private double textureV;

    private boolean hasBrightness;
    private int brightness;

    private int addedVertices;
    private int vertexCount;

    private boolean hasColorsEnabled;

    private boolean hasNormals;
    private int normals;

    private boolean hasColors;
    private int colorRgb;

    private double xOffset;
    private double yOffset;
    private double zOffset;

    // Buffers etc
    private int rawBufferIndex;
    private int[] rawBuffer;

    private ByteBuffer byteBuffer;
    private IntBuffer intBuffer;
    private FloatBuffer floatBuffer;
    private ShortBuffer shortBuffer;

    // VBO stuff/ TODO
    private boolean useVbo;
    private IntBuffer vertexBuffer;
    private int vboIndex;
    private int vboCount;

    public Tesselator(int bufferSize) {
        this.bufferSize = bufferSize;

        this.rawBuffer = new int[bufferSize];
        this.rawBufferIndex = 0;

        this.byteBuffer = ByteBuffer.allocate(bufferSize * 4).order(ByteOrder.nativeOrder());
        this.intBuffer = this.byteBuffer.asIntBuffer();
        this.floatBuffer = this.byteBuffer.asFloatBuffer();
        this.shortBuffer = this.byteBuffer.asShortBuffer();
    }

    public static Tesselator getInstance() {
        return instance;
    }

    @Override
    public void draw() {
        if (!this.isDrawing) {
            throw new IllegalStateException("Not tesselating!");
        } else {
            this.isDrawing = false;

            if (this.vertexCount > 0) {

                this.intBuffer.clear();
                this.intBuffer.put(this.rawBuffer, 0, this.rawBufferIndex);
                this.byteBuffer.position(0);
                this.byteBuffer.limit(this.rawBufferIndex * 4);

                if (this.useVbo) {

                }

                if (this.hasTextures) {
                    // TODO: VBO

                    GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
                }
            }

            this.reset();
        }
    }

    private void reset() {
        this.vertexCount = 0;
        this.addedVertices = 0;
        this.rawBufferIndex = 0;
        this.byteBuffer.clear();
    }

    @Override
    public void startDrawingQuads() {
        this.startDrawing(DrawMode.QUADS);
    }

    @Override
    public void startDrawing(DrawMode mode) {
        if (this.isDrawing) {
            throw new IllegalStateException("Already tesselating!");
        } else {
            this.isDrawing = true;
            this.reset();
            this.drawMode = mode;
            this.hasTextures = false;
            this.hasBrightness = false;
            this.hasNormals = false;
            this.hasColors = false;
            this.hasColorsEnabled = true;
        }
    }

    @Override
    public void setTextureUV(double u, double v) {
        this.hasTextures = true;
        this.textureU = u;
        this.textureV = v;
    }

    @Override
    public void setBrightness(int brightness) {
        this.hasBrightness = true;
        this.brightness = brightness;
    }

    @Override
    public void disableColors() {
        this.hasColorsEnabled = false;
    }

    @Override
    public void enableColors() {
        this.hasColorsEnabled = true;
    }

    @Override
    public void setColorRGBA(int r, int g, int b, int a) {
        if (!this.hasColorsEnabled)
            return;

        if (r >  255)
            r = 255;

        if (r < 0)
            r = 0;

        if (g > 255)
            g = 255;

        if (g < 0)
            g = 0;

        if (b > 255)
            b = 255;

        if (b < 0)
            b = 0;

        if (a > 255)
            a = 255;

        if (a < 0)
            a = 0;

        if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            this.colorRgb = a << 24 | b << 16 | g << 8 | r;
        } else {
            this.colorRgb = r << 24 | g << 16 | b << 8 | a;
        }
    }

    @Override
    public void addVertex(int x, int y, int z) {
        this.addedVertices++;

        // TODO: VBO

        this.rawBuffer[this.rawBufferIndex] = Float.floatToRawIntBits((float) (this.xOffset + x));
        this.rawBuffer[this.rawBufferIndex + 1] = Float.floatToRawIntBits((float) (this.yOffset + y));
        this.rawBuffer[this.rawBufferIndex + 2] = Float.floatToIntBits((float) (this.zOffset + z));

        if (this.hasTextures) {
            this.rawBuffer[this.rawBufferIndex + 3] = Float.floatToRawIntBits((float) this.textureU);
            this.rawBuffer[this.rawBufferIndex + 4] = Float.floatToRawIntBits((float) this.textureV);
        }

        if (this.hasColors) {
            this.rawBuffer[this.rawBufferIndex + 5] = this.colorRgb;
        }

        if (this.hasNormals) {
            this.rawBuffer[this.rawBufferIndex + 6] = this.normals;
        }

        if (this.hasBrightness) {
            this.rawBuffer[this.rawBufferIndex + 7] = this.brightness;
        }

        this.rawBufferIndex += 8;
        this.vertexCount++;

        if (this.vertexCount % 4 == 0 && this.rawBufferIndex >= (this.bufferSize - 32)) {
            this.draw();
            this.isDrawing = true;
        }
    }

    @Override
    public void addVertexUV(int x, int y, int z, double u, double v) {
        this.addVertex(x, y, z);
        this.setTextureUV(u, v);
    }

    @Override
    public void setNormals(float x, float y, float z) {
        this.hasNormals = true;
        byte x1 = (byte) ((int) (x * 127.0f));
        byte y1 = (byte) ((int) (y * 127.0f));
        byte z1 = (byte) ((int) (z * 127.0f));
        this.normals = (x1 & 255) | (y1 & 255) << 8 | (z1 & 255) << 16;
    }

    @Override
    public void setTranslation(double x, double y, double z) {
        this.xOffset = x;
        this.yOffset = y;
        this.zOffset = z;
    }

    @Override
    public void addTranslation(double x, double y, double z) {
        this.xOffset += x;
        this.yOffset += y;
        this.zOffset += z;
    }
}
