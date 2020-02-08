package renderer.font;

import java.util.List;
import java.util.Map;

import renderer.Renderer;
import renderer.Shader;
import renderer.ShaderLib;

public class TextBatcher {

	private Shader shader;

	public TextBatcher() {
		shader = new Shader(ShaderLib.Shader_Font);
	}
	
	public void render(Map<FontType, List<GUIText>> texts){
		prepare();
		for(FontType font : texts.keySet()){
			font.getTextureAtlas().Bind();
			for(GUIText text : texts.get(font)){
				renderText(text);
			}
		}
		endRendering();
	}

	private void prepare(){
		Renderer.SetDepth(false);
		shader.Bind();
	}
	
	private void renderText(GUIText text){
		text.getVertexArray().Bind();
		shader.UploadUniformFloat3("color",text.getColor());
		shader.UploadUniformMat4("u_transform",text.getTransform());
		Renderer.Draw(text.getVertexArray(), text.getVerticesSize());
	}
	
	private void endRendering(){
		shader.UnBind();
		Renderer.SetDepth(true);
	}

}
