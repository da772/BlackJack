package renderer;

public class ShaderLib {

	
	public final static String[] Shader_L_V3Pos_V3Col = new String[] {
			"#version 330 core\r\n" + 
			"layout (location = 0) in vec3 aPos; // the position variable has attribute position 0\r\n" + 
			"layout (location = 1) in vec3 aColor; // the position variable has attribute position 0\r\n" + 
			"  \r\n" + 
			"out vec4 vertexColor; // specify a color output to the fragment shader\r\n" + 
			"\r\n" + 
			"void main()\r\n" + 
			"{\r\n" + 
			"    gl_Position = vec4(aPos, 1.0); // see how we directly give a vec3 to vec4's constructor\r\n" + 
			"    vertexColor = vec4(aColor, 1.0); // set the output variable to a dark-red color\r\n" + 
			"}",
			"#version 330 core\r\n" + 
			"out vec4 FragColor;\r\n" + 
			"  \r\n" + 
			"in vec4 vertexColor; // the input variable from the vertex shader (same name and same type)  \r\n" + 
			"\r\n" + 
			"void main()\r\n" + 
			"{\r\n" + 
			"    FragColor = vertexColor;\r\n" + 
			"} " 
			};
	
	public final static String[] Shader_U_Texture_L_V3Pos_V2Coord = new String[] {
			"#version 330 core\r\n" + 
			"\r\n" + 
			"layout(location = 0) in vec3 a_Position;\r\n" + 
			"layout(location = 1) in vec2 a_TexCoord;\r\n" + 
			"\r\n" + 
			"out vec2 v_TexCoord;\r\n" + 
			"\r\n" + 
			"void main() {\r\n" + 
			"	v_TexCoord = a_TexCoord;\r\n" + 
			"	gl_Position = vec4(a_Position, 1.0);\r\n" + 
			"}",
			"#version 330 core\r\n" + 
			"\r\n" + 
			"layout(location = 0) out vec4 color;\r\n" + 
			"\r\n" + 
			"in vec2 v_TexCoord;\r\n" + 
			"\r\n" + 
			"uniform sampler2D u_Texture;\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"void main() {\r\n" + 
			"	vec4 c = texture(u_Texture, v_TexCoord);\r\n" + 
			"	color = c;\r\n" + 
			"}" 
			};
	
	public final static String[] Shader_U_Texture_ViewProj_Transform_L_V3Pos_V2Coord = new String[] {
			"#version 330\r\n" + 
			" \r\n" + 
			"layout(location = 0) in vec3 a_Position;\r\n" + 
			"layout(location = 1) in vec2 a_TexCoord;\r\n" + 
			" \r\n" + 
			"uniform mat4 u_viewProjection;\r\n" + 
			"uniform mat4 u_transform;\r\n" + 
			"out vec2 v_TexCoord;\r\n" + 
			" \r\n" + 
			"void main() \r\n" + 
			"{\r\n" + 
			"	v_TexCoord = a_TexCoord;\r\n" + 
			"    gl_Position = u_viewProjection * u_transform * vec4(a_Position,1.f);\r\n" + 
			"}",
			
			
			"#version 330 core\r\n" + 
			"\r\n" + 
			"layout(location = 0) out vec4 color;\r\n" + 
			"\r\n" + 
			"in vec2 v_TexCoord;\r\n" + 
			"\r\n" + 
			"uniform sampler2D u_Texture;\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"void main() {\r\n" + 
			"	color = texture(u_Texture, v_TexCoord);\r\n" + 
			"}" 
			};
	
	public final static String[] Shader_U_Texture_ViewProj_UV_Transform_L_V3Pos_V2Coord = new String[] {
			"#version 330\r\n" + 
			" \r\n" + 
			"layout(location = 0) in vec3 a_Position;\r\n" + 
			"layout(location = 1) in vec2 a_TexCoord;\r\n" + 
			" \r\n" + 
			"uniform mat4 u_viewProjection;\r\n" + 
			"uniform mat4 u_transform;\r\n" + 
			"out vec2 v_TexCoord;\r\n" + 
			" \r\n" + 
			"void main() \r\n" + 
			"{\r\n" + 
			"	v_TexCoord = a_TexCoord;\r\n" + 
			"    gl_Position = u_viewProjection * u_transform * vec4(a_Position,1.f);\r\n" + 
			"}",
			
			
			"#version 330 core\r\n" + 
			"\r\n" + 
			"layout(location = 0) out vec4 color;\r\n" + 
			"\r\n" + 
			"in vec2 v_TexCoord;\r\n" + 
			"\r\n" + 
			"uniform sampler2D u_Texture;\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"void main() {\r\n" + 
			"	color = texture(u_Texture, v_TexCoord * vec2(1000,1000));\r\n" + 
			"}" 
			};
	
	public final static String[] Shader_U_Texture_ViewProj_UV2_Transform_L_V3Pos_V2Coord = new String[] {
			"#version 330\r\n" + 
			" \r\n" + 
			"layout(location = 0) in vec3 a_Position;\r\n" + 
			"layout(location = 1) in vec2 a_TexCoord;\r\n" + 
			" \r\n" + 
			"uniform mat4 u_viewProjection;\r\n" + 
			"uniform mat4 u_transform;\r\n" + 
			"out vec2 v_TexCoord;\r\n" + 
			" \r\n" + 
			"void main() \r\n" + 
			"{\r\n" + 
			"	v_TexCoord = a_TexCoord;\r\n" + 
			"    gl_Position = u_viewProjection * u_transform * vec4(a_Position,1.f);\r\n" + 
			"}",
			
			
			"#version 330 core\r\n" + 
			"\r\n" + 
			"layout(location = 0) out vec4 color;\r\n" + 
			"\r\n" + 
			"in vec2 v_TexCoord;\r\n" + 
			"\r\n" + 
			"uniform sampler2D u_Texture;\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"void main() {\r\n" + 
			"	color = texture(u_Texture, v_TexCoord * vec2(.25,.25));\r\n" + 
			"}" 
			};
	
	
	public final static String[] Shader_U_Texture_Transform_L_Vec3Pos_Vec2Coord = new String[] {
			"#version 330\r\n" + 
			" \r\n" + 
			"layout(location = 0) in vec3 a_Position;\r\n" + 
			"layout(location = 1) in vec2 a_TexCoord;\r\n" + 
			" \r\n" + 
			"uniform mat4 u_transform;\r\n" + 
			"out vec2 v_TexCoord;\r\n" + 
			" \r\n" + 
			"void main() \r\n" + 
			"{\r\n" + 
			"	v_TexCoord = a_TexCoord;\r\n" + 
			"    gl_Position = u_transform * vec4(a_Position,1.f);\r\n" + 
			"}",
			
			
			"#version 330 core\r\n" + 
			"\r\n" + 
			"layout(location = 0) out vec4 color;\r\n" + 
			"\r\n" + 
			"in vec2 v_TexCoord;\r\n" + 
			"\r\n" + 
			"uniform sampler2D u_Texture;\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"void main() {\r\n" + 
			"	color = texture(u_Texture, v_TexCoord);\r\n" + 
			"}" 
			};
	
	public final static String[] Shader_Font = new String[] {
			"#version 330\r\n" + 
			"\r\n" + 
			"in vec2 position;\r\n" + 
			"in vec2 textureCoords;\r\n" + 
			"\r\n" + 
			"out vec2 pass_textureCoords;\r\n" + 
			"out vec4 pass_color;\r\n" + 
			"\r\n" + 
			"uniform mat4 u_transform;\r\n" + 
			"uniform vec4 color;\r\n" + 
			"\r\n" + 
			"void main(void){\r\n" + 
			"\r\n" + 
			"	gl_Position = u_transform * vec4(position, 0f, 1.f);\r\n" + 
			"	pass_textureCoords = textureCoords;\r\n" + 
			"	pass_color = color;\r\n" + 
			"\r\n" + 
			"}","#version 330\r\n" + 
					"\r\n" + 
					"in vec2 pass_textureCoords;\r\n" + 
					"in vec4 pass_color;\r\n" + 
					"\r\n" + 
					"out vec4 out_colour;\r\n" + 
					"\r\n" + 
					"uniform sampler2D fontAtlas;\r\n" + 
					"\r\n" + 
					"void main(void){\r\n" + 
					"\r\n" + 
					"	out_colour =  pass_color*vec4(vec3(pass_color.r, pass_color.g, pass_color.b), texture(fontAtlas, pass_textureCoords).a);\r\n" + 
					"\r\n" + 
					"}"
	};
	
	

	
	public final static String[] Shader_L_V3Pos_V4Color = new String[] {
			"#version 330 core\r\n" + 
			"layout (location = 0) in vec3 aPos; // the position variable has attribute position 0\r\n" + 
			"layout (location = 1) in vec4 aColor; // the position variable has attribute position 0\r\n" + 
			"  \r\n" + 
			"out vec4 vertexColor; // specify a color output to the fragment shader\r\n" + 
			"\r\n" + 
			"void main()\r\n" + 
			"{\r\n" + 
			"    gl_Position = vec4(aPos, 1.0); // see how we directly give a vec3 to vec4's constructor\r\n" + 
			"    vertexColor = aColor; // set the output variable to a dark-red color\r\n" + 
			"}",
			"#version 330 core\r\n" + 
			"out vec4 FragColor;\r\n" + 
			"  \r\n" + 
			"in vec4 vertexColor; // the input variable from the vertex shader (same name and same type)  \r\n" + 
			"\r\n" + 
			"void main()\r\n" + 
			"{\r\n" + 
			"    FragColor = vertexColor;\r\n" + 
			"} " 
			};
	
	public final static String[] Shader_L_V3Pos = new String[] {
			"#version 330 core\r\n" + 
			"layout (location = 0) in vec3 aPos; // the position variable has attribute position 0\r\n" + 
			"  \r\n" + 
			"out vec4 vertexColor; // specify a color output to the fragment shader\r\n" + 
			"\r\n" + 
			"void main()\r\n" + 
			"{\r\n" + 
			"    gl_Position = vec4(aPos, 1.0); // see how we directly give a vec3 to vec4's constructor\r\n" + 
			"    vertexColor = vec4(1.0, 1.0, 1.0, 1.0); // set the output variable to a dark-red color\r\n" + 
			"}",
			"#version 330 core\r\n" + 
			"out vec4 FragColor;\r\n" + 
			"  \r\n" + 
			"in vec4 vertexColor; // the input variable from the vertex shader (same name and same type)  \r\n" + 
			"\r\n" + 
			"void main()\r\n" + 
			"{\r\n" + 
			"    FragColor = vertexColor;\r\n" + 
			"} " 
			};
	

	
	
}
