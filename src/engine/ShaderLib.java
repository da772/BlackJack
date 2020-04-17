package engine;

/**
 * 
 * Library of static shader sources
 *
 */
public class ShaderLib {

	public final static String[] Shader_Font = new String[] {
			"#version 330\r\n" + 
					" \r\n" + 
					"layout(location = 0) in vec3 a_Position;\r\n" + 
					"layout(location = 1) in vec2 a_TexCoord;\r\n" + 
					" \r\n" + 
					"uniform mat4 u_Transform;\r\n" + 
					"uniform vec4 u_Color;\r\n" + 
					"out vec2 v_TexCoord;\r\n" + 
					"out vec4 v_color;\r\n" + 
					" \r\n" + 
					"void main() \r\n" + 
					"{\r\n" + 
					"	v_TexCoord = a_TexCoord;\r\n" + 
					"	v_color = u_Color;\r\n" + 
					"    gl_Position = u_Transform * vec4(a_Position,1.f);\r\n" + 
					"}",
					
					
					"#version 330 core\r\n" + 
					"\r\n" + 
					"layout(location = 0) out vec4 color;\r\n" + 
					"\r\n" + 
					"in vec2 v_TexCoord;\r\n" + 
					"in vec4 v_color;\r\n" + 
					"\r\n" + 
					"uniform sampler2D u_Texture;\r\n" + 
					"\r\n" + 
					"\r\n" + 
					"void main() {\r\n" + 
					"	color = v_color*vec4(v_color.r, v_color.g, v_color.b, texture(u_Texture, v_TexCoord).a);\r\n" + 
					"}" 
					};
	
	
	public final static String[] Shader_2DQuad = new String[] {
			"#version 330\r\n" + 
			" \r\n" + 
			"layout(location = 0) in vec3 a_Position;\r\n" + 
			"layout(location = 1) in vec2 a_TexCoord;\r\n" + 
			" \r\n" + 
			"uniform mat4 u_viewProjection;\r\n" + 
			"uniform mat4 u_transform;\r\n" + 
			"uniform vec2 u_UVScale;\r\n" + 
			"uniform vec4 u_Color;\r\n" + 
			"out vec2 v_TexCoord;\r\n" + 
			"out vec2 v_UVScale;\r\n" + 
			"out vec4 v_Color;\r\n" + 
			" \r\n" + 
			"void main() \r\n" + 
			"{\r\n" + 
			"	v_TexCoord = a_TexCoord;\r\n" + 
			"	v_UVScale = u_UVScale;\r\n" + 
			"	v_Color = u_Color;\r\n" + 
			"    gl_Position = u_viewProjection * u_transform * vec4(a_Position,1.f);\r\n" + 
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
	
	public final static String[] Shader_2DQuadInstanced = new String[] {
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
	
	
	
	public final static String[] Shader_2DBackground = new String[] {
			"#version 330\r\n" + 
					" \r\n" + 
					"layout(location = 0) in vec3 a_Position;\r\n" + 
					"layout(location = 1) in vec2 a_TexCoord;\r\n" + 
					" \r\n" + 
					"uniform mat4 u_viewProjection;\r\n" + 
					"uniform mat4 u_transform;\r\n" + 
					"uniform vec2 u_UVScale;\r\n" + 
					"uniform vec4 u_Color;\r\n" + 
					"out vec2 v_TexCoord;\r\n" + 
					"out vec2 v_UVScale;\r\n" + 
					"out vec4 v_Color;\r\n" + 
					" \r\n" + 
					"void main() \r\n" + 
					"{\r\n" + 
					"	v_TexCoord = a_TexCoord;\r\n" + 
					"	v_UVScale = u_UVScale;\r\n" + 
					"	v_Color = u_Color;\r\n" + 
					"    gl_Position = u_viewProjection * u_transform * vec4(a_Position,1.f);\r\n" + 
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
	
	

	public final static String[] Shader_GUIQuad = new String[] {
			"#version 330\r\n" + 
					" \r\n" + 
					"layout(location = 0) in vec3 a_Position;\r\n" + 
					"layout(location = 1) in vec2 a_TexCoord;\r\n" + 
					" \r\n" + 
					"uniform mat4 u_Transform;\r\n" + 
					"uniform vec4 u_Color;\r\n" + 
					"uniform vec2 u_UVScale;\r\n" + 
					"out vec2 v_TexCoord;\r\n" + 
					"out vec4 v_color;\r\n" + 
					"out vec2 v_uvScale;\r\n" + 
					" \r\n" + 
					"void main() \r\n" + 
					"{\r\n" + 
					"	v_uvScale = u_UVScale;\r\n" + 
					"	v_TexCoord = a_TexCoord;\r\n" + 
					"	v_color = u_Color;\r\n" + 
					"    gl_Position = u_Transform * vec4(a_Position,1.f);\r\n" + 
					"}",
					
					
					"#version 330 core\r\n" + 
					"\r\n" + 
					"layout(location = 0) out vec4 color;\r\n" + 
					"\r\n" + 
					"in vec2 v_TexCoord;\r\n" + 
					"in vec4 v_color;\r\n" + 
					"in vec2 v_uvScale;\r\n" + 
					"\r\n" + 
					"uniform sampler2D u_Texture;\r\n" + 
					"\r\n" + 
					"\r\n" + 
					"void main() {\r\n" + 
					"	color = texture(u_Texture, v_TexCoord*v_uvScale) * v_color;\r\n" + 
					"}" 
					};
	
	
	
	public final static String[] Shader_GUIQuad_FishEye = new String[] {
			"#version 330\r\n" + 
					" \r\n" + 
					"layout(location = 0) in vec3 a_Position;\r\n" + 
					"layout(location = 1) in vec2 a_TexCoord;\r\n" + 
					" \r\n" + 
					"uniform mat4 u_Transform;\r\n" + 
					"uniform vec4 u_Color;\r\n" + 
					"uniform vec2 u_UVScale;\r\n" + 
					"out vec2 v_TexCoord;\r\n" + 
					"out vec4 v_color;\r\n" + 
					"out vec2 v_uvScale;\r\n" + 
					" \r\n" + 
					"void main() \r\n" + 
					"{\r\n" + 
					"	v_uvScale = u_UVScale;\r\n" + 
					"	v_TexCoord = a_TexCoord;\r\n" + 
					"	v_color = u_Color;\r\n" + 
					"    gl_Position = u_Transform * vec4(a_Position,1.f);\r\n" + 
					"}",
					
					
					"#version 330 core\r\n" + 
							"\r\n" + 
							"layout(location = 0) out vec4 color;\r\n" + 
							"\r\n" + 
							"in vec2 v_TexCoord;\r\n" + 
							"in vec4 v_color;\r\n" + 
							"in vec2 v_uvScale;\r\n" + 
							"\r\n" + 
							"uniform sampler2D u_Texture;\r\n" + 
							"\r\n" + 
							"void main() {\r\n" + 
							"float aperture = 178.0;\r\n" + 
							"float PI = 3.1415926535;\r\n" + 
							"  float apertureHalf = 0.5 * aperture * (PI / 180.0);\r\n" + 
							"  float maxFactor = sin(apertureHalf);\r\n" + 
							"  \r\n" + 
							"  vec2 uv;\r\n" + 
							"  vec2 xy = 2.0 * v_TexCoord.xy - 1.0;\r\n" + 
							"  float d = length(xy);\r\n" + 
							"  if (d < (2.0-maxFactor))\r\n" + 
							"  {\r\n" + 
							"    d = length(xy * maxFactor);\r\n" + 
							"    float z = sqrt(1.0 - d * d);\r\n" + 
							"    float r = atan(d, z) / PI;\r\n" + 
							"    float phi = atan(xy.y, xy.x);\r\n" + 
							"    \r\n" + 
							"    uv.x = r * cos(phi) + 0.5;\r\n" + 
							"    uv.y = r * sin(phi) + 0.5;\r\n" + 
							"  }\r\n" + 
							"  else\r\n" + 
							"  {\r\n" + 
							"    uv = v_TexCoord.xy;\r\n" + 
							"  }\r\n" + 
							"  vec4 c = texture2D(u_Texture, uv);\r\n" + 
							"  color = c;" +
							"}" 
					};
	
	
	
	
}
