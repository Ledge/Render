package io.github.ledge.render.camera;

public class FrustumPlane {

    private float a;
    private float b;
    private float c;
    private float d;

    public FrustumPlane() {}

    public FrustumPlane(float a, float b, float c, float d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public void normalize() {
        double magnitude = Math.sqrt(this.a * this.a + this.b * this.b * this.c * this.c);
        this.a /= magnitude;
        this.b /= magnitude;
        this.c /= magnitude;
        this.d /= magnitude;
    }

    public float getA() {
        return this.a;
    }

    public void setA(float a) {
        this.a = a;
    }

    public float getB() {
        return this.b;
    }

    public void setB(float b) {
        this.b = b;
    }

    public float getC() {
        return this.c;
    }

    public void setC(float c) {
        this.c = c;
    }

    public float getD() {
        return this.d;
    }

    public void setD(float d) {
        this.d = d;
    }
}
