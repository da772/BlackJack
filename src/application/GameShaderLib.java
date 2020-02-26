package application;

public class GameShaderLib {

	
	public final static String[] Shader_ChipStack = new String[] {
			"#version 330\r\n" + 
			" \r\n" + 
			"layout(location = 0) in vec3 a_Position;\r\n" + 
			"layout(location = 1) in vec2 a_TexCoord;\r\n" + 
			" \r\n" + 
			"uniform mat4 u_viewProjection;\r\n" + 
			"uniform mat4 u_transform;\r\n" + 
			"uniform vec2 u_UVScale;\r\n" + 
			"uniform vec4 u_Color;\r\n" + 
			"uniform vec3 u_Offsets[100];\r\n" + 
			"out vec2 v_TexCoord;\r\n" + 
			"out vec2 v_UVScale;\r\n" + 
			"out vec4 v_Color;\r\n" + 
			" \r\n" + 
			"void main() \r\n" + 
			"{\r\n" + 
			"	v_TexCoord = a_TexCoord;\r\n" + 
			"	v_UVScale = u_UVScale;\r\n" + 
			"	v_Color = u_Color;\r\n" + 
			"    gl_Position = u_viewProjection * u_transform * vec4(a_Position+u_Offsets[gl_InstanceID],1.f);\r\n" + 
			"}",
			
			
			"#version 330 core\r\n" + 
			"\r\n" + 
			"layout(location = 0) out vec4 color;\r\n" + 
			"\r\n" + 
			"in vec2 v_TexCoord;\r\n" + 
			"in vec2 v_UVScale;\r\n" + 
			"in vec4 v_Color;\r\n" + 
			"\r\n" + 
			"uniform sampler2D u_Texture;\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"void main() {\r\n" + 
			"	color = v_Color * texture(u_Texture, v_TexCoord * v_UVScale);\r\n" + 
			"}" 
			};
	
	
	
	
	
}
