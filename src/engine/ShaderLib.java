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
	
	
	public final static String[] Shader_GUIQuad_Wave = new String[] {
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
							"uniform float offset;\r\n" + 
							"\r\n" + 
							"void main() {\r\n" +
							"vec2 texcoord = v_TexCoord*v_uvScale;\r\n" + 
							"texcoord.x += sin(texcoord.y * 4*2*3.14159 + offset) / 100;\r\n"+
							"color = texture(u_Texture, texcoord) * v_color;\r\n" + 
							"}" 
					};
	
	public final static String[] Shader_GUIQuad_ScanLines = new String[] {
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
							"uniform float time;\r\n" + 
							"\r\n" + 
							"void main() {\r\n" +
							"vec2 q = vec2(v_TexCoord.x, -v_TexCoord.y) ;\r\n" + 
							"vec2 uv = vec2(v_TexCoord.x, -v_TexCoord.y);\r\n" + 
							"\r\n" + 
							"vec3 oricol = texture( u_Texture, vec2(q.x,1.0+q.y) ).xyz;\r\n" + 
							"vec3 col;\r\n" + 
							"\r\n" + 
							"col.r = texture(u_Texture,vec2(uv.x+0.006,-uv.y)).x;\r\n" + 
							"col.g = texture(u_Texture,vec2(uv.x+0.000,-uv.y)).y;\r\n" + 
							"col.b = texture(u_Texture,vec2(uv.x-0.006,-uv.y)).z;\r\n" + 
							"\r\n" + 
							"col = clamp(col*0.5+0.5*col*col*1.2,0.0,1.0);\r\n" + 
							"\r\n" + 
							"col *= 0.5 + 0.5*16.0*uv.x*-uv.y*(1.0-uv.x)*(1.0+uv.y);\r\n" + 
							"\r\n" + 
							"col *= vec3(0.95,1.05,0.95);\r\n" + 
							"\r\n" + 
							"col *= 0.9+0.1*sin(10.0*time+uv.y*1000.0);\r\n" + 
							"\r\n" + 
							"col *= 0.99+0.01*sin(110.0*time);\r\n" + 
							"\r\n" + 
							"\r\n" + 
							"color = vec4(col,1.0);"+
							"}" 
					};
	

	

	

	
	
}
