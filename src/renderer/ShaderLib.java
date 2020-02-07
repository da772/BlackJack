package renderer;

public class ShaderLib {

	
	public final static String[] PositionF3_ColorF3_V_Shader = new String[] {
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
	
	public final static String[] Texture_PositionF3_CoordF2_V_Shader = new String[] {
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
	
	public final static String[] Texture_PositionF3_CoordF2_V_T_Shader = new String[] {
			"#version 330 core\r\n" + 
			"\r\n" + 
			"layout(location = 0) in vec3 a_Position;\r\n" + 
			"layout(location = 1) in vec2 a_TexCoord;\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"uniform mat4 u_Projection;\r\n" + 
			"uniform mat4 u_View;\r\n" + 
			"uniform mat4 u_Transform;\r\n" + 
			"\r\n" + 
			"out vec2 v_TexCoord;\r\n" + 
			"\r\n" + 
			"void main() {\r\n" + 
			"	v_TexCoord = a_TexCoord;\r\n" + 
			"	gl_Position = u_Projection * u_View * u_Transform * vec4(a_Position, 1.0);\r\n" + 
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
	

	
	public final static String[] PositionF3_ColorF4_V_Shader = new String[] {
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
	
	public final static String[] PositionF3_V_Shader = new String[] {
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
