package app.graphics;

public class TexturedModel {

    private RawModel rawModel;
    private Texture texture;

    public TexturedModel(RawModel model, Texture texture){
        this.rawModel = model;
        this.texture = texture;

    }

    public Texture getTexture() {
        return texture;
    }

    public RawModel getRawModel() {
        return rawModel;
    }
}
