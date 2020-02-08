package renderer.font;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

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
			GL30.glActiveTexture(GL30.GL_TEXTURE0);
			GL30.glBindTexture(GL30.GL_TEXTURE_2D, font.getTextureAtlas());
			for(GUIText text : texts.get(font)){
				renderText(text);
			}
		}
		endRendering();
	}

	private void prepare(){
		GL30.glDisable(GL11.GL_DEPTH_TEST);
		shader.Bind();
	}
	
	private void renderText(GUIText text){
		text.getVertexArray().Bind();
		shader.UploadUniformFloat3("color",text.getColor());
		shader.UploadUniformMat4("u_transform",text.getTransform());
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVerticesSize());
	}
	
	private void endRendering(){
		shader.UnBind();
		GL30.glEnable(GL11.GL_DEPTH_TEST);
	}

}
