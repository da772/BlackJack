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
							"col.r = texture(u_Texture,vec2(uv.x+0.003,-uv.y)).x;\r\n" + 
							"col.g = texture(u_Texture,vec2(uv.x+0.000,-uv.y)).y;\r\n" + 
							"col.b = texture(u_Texture,vec2(uv.x-0.003,-uv.y)).z;\r\n" + 
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
	
	
	public final static String[] Shader_GUIQuad_VHS = new String[] {
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
							"#define PI 3.14159265\r\n" + 
							"#define WAVEPOWER1 .005\r\n" + 
							"#define WAVEPOWER2 .5\r\n" + 
							"#define BLOOMPOWER .6\r\n" + 
							"\r\n" + 
							"\r\n" + 
							"vec3 tex2D( sampler2D _tex, vec2 _p ){\r\n" + 
							"  vec3 col = texture( _tex, _p ).xyz;\r\n" + 
							"  if ( 0.5 < abs( _p.x - 0.5 ) ) {\r\n" + 
							"    col = vec3( 0.1 );\r\n" + 
							"  }\r\n" + 
							"  return col;\r\n" + 
							"}\r\n" + 
							"\r\n" + 
							"float hash( vec2 _v ){\r\n" + 
							"  return fract( sin( dot( _v, vec2( 89.44, 19.36 ) ) ) * 22189.22 );\r\n" + 
							"}\r\n" + 
							"\r\n" + 
							"float iHash( vec2 _v, vec2 _r ){\r\n" + 
							"  float h00 = hash( vec2( floor( _v * _r + vec2( 0.0, 0.0 ) ) / _r ) );\r\n" + 
							"  float h10 = hash( vec2( floor( _v * _r + vec2( 1.0, 0.0 ) ) / _r ) );\r\n" + 
							"  float h01 = hash( vec2( floor( _v * _r + vec2( 0.0, 1.0 ) ) / _r ) );\r\n" + 
							"  float h11 = hash( vec2( floor( _v * _r + vec2( 1.0, 1.0 ) ) / _r ) );\r\n" + 
							"  vec2 ip = vec2( smoothstep( vec2( 0.0, 0.0 ), vec2( 1.0, 1.0 ), mod( _v*_r, 1. ) ) );\r\n" + 
							"  return ( h00 * ( 1. - ip.x ) + h10 * ip.x ) * ( 1. - ip.y ) + ( h01 * ( 1. - ip.x ) + h11 * ip.x ) * ip.y;\r\n" + 
							"}\r\n" + 
							"\r\n" + 
							"float noise( vec2 _v ){\r\n" + 
							"  float sum = 0.;\r\n" + 
							"  for( int i=1; i<9; i++ )\r\n" + 
							"  {\r\n" + 
							"    sum += iHash( _v + vec2( i ), vec2( 2. * pow( 2., float( i ) ) ) ) / pow( 2., float( i ) );\r\n" + 
							"  }\r\n" + 
							"  return sum;\r\n" + 
							"}\r\n" + 
							"\r\n" + 
							"void main(){\r\n" + 
							"  vec2 uv = v_TexCoord.xy;\r\n" + 
							"  vec2 uvn = uv;\r\n" + 
							"  vec3 col = vec3( 0.0 );\r\n" + 
							"\r\n" + 
							"  // tape wave\r\n" + 
							"  uvn.x += ( noise( vec2( uvn.y, time ) ) - 0.5 )* WAVEPOWER1;\r\n" + 
							"  uvn.x += ( noise( vec2( uvn.y * 100.0, time * 10.0 ) ) - 0.5 ) * 0.01;\r\n" + 
							"\r\n" + 
							"  // tape crease\r\n" + 
							"  float tcPhase = clamp( ( sin( uvn.y * 8.0 - time * PI * 1.2 ) - 0.92 ) * noise( vec2( time ) ), 0.0, 0.01 ) * 1.0;\r\n" + 
							"  float tcNoise = max( noise( vec2( uvn.y * 100.0, time * 10.0 ) ) - WAVEPOWER2, 0.0 );\r\n" + 
							"  uvn.x = uvn.x - tcNoise * tcPhase;\r\n" + 
							"\r\n" + 
							"  // switching noise\r\n" + 
							"  float snPhase = smoothstep( 0.005, 0.0, uvn.y );\r\n" + 
							"  uvn.y += snPhase * 0.3;\r\n" + 
							"  uvn.x += snPhase * ( ( noise( vec2( uv.y * 100.0, time * 10.0 ) ) - 0.5 ) * 0.2 );\r\n" + 
							"    \r\n" + 
							"  col = tex2D( u_Texture, uvn );\r\n" + 
							"  col *= 1.0 - tcPhase;\r\n" + 
							"  col = mix(\r\n" + 
							"    col,\r\n" + 
							"    col.yzx,\r\n" + 
							"    snPhase\r\n" + 
							"  );\r\n" + 
							"\r\n" + 
							"  // bloom\r\n" + 
							"  for( float x = -4.0; x < 2.5; x += 1.0 ){\r\n" + 
							"    col.xyz += vec3(\r\n" + 
							"      tex2D( u_Texture, uvn + vec2( x - 0.0, 0.0 ) * 7E-3 ).x,\r\n" + 
							"      tex2D( u_Texture, uvn + vec2( x - 2.0, 0.0 ) * 7E-3 ).y,\r\n" + 
							"      tex2D( u_Texture, uvn + vec2( x - 4.0, 0.0 ) * 7E-3 ).z\r\n" + 
							"    ) * 0.05;\r\n" + 
							"  }\r\n" + 
							"  col *= BLOOMPOWER;\r\n" + 
							"\r\n" + 
							"  // ac beat\r\n" + 
							"  col *= 1.0 + clamp( noise( vec2( 0.0, uv.y + time * 0.2 ) ) * 0.6 - 0.25, 0.0, 0.1 );\r\n" + 
							"\r\n" + 
							"  color = vec4( col, 1.0 );\r\n" + 
							"}\r\n" 
						
					};
	
	
	public final static String[] Shader_GUIQuad_CRTTV = new String[] {
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
							"// change these values to 0.0 to turn off individual effects\r\n" + 
							"float vertJerkOpt = 2.0;\r\n" + 
							"float vertMovementOpt = 0.0;\r\n" + 
							"float bottomStaticOpt = .5;\r\n" + 
							"float scalinesOpt = 1.0;\r\n" + 
							"float rgbOffsetOpt = .75;\r\n" + 
							"float horzFuzzOpt = 1.5;\r\n" + 
							"\r\n" + 
							"// Noise generation functions borrowed from: \r\n" + 
							"// https://github.com/ashima/webgl-noise/blob/master/src/noise2D.glsl\r\n" + 
							"\r\n" + 
							"vec3 mod289(vec3 x) {\r\n" + 
							"  return x - floor(x * (1.0 / 289.0)) * 289.0;\r\n" + 
							"}\r\n" + 
							"\r\n" + 
							"vec2 mod289(vec2 x) {\r\n" + 
							"  return x - floor(x * (1.0 / 289.0)) * 289.0;\r\n" + 
							"}\r\n" + 
							"\r\n" + 
							"vec3 permute(vec3 x) {\r\n" + 
							"  return mod289(((x*34.0)+1.0)*x);\r\n" + 
							"}\r\n" + 
							"\r\n" + 
							"float snoise(vec2 v)\r\n" + 
							"  {\r\n" + 
							"  const vec4 C = vec4(0.211324865405187,  // (3.0-sqrt(3.0))/6.0\r\n" + 
							"                      0.366025403784439,  // 0.5*(sqrt(3.0)-1.0)\r\n" + 
							"                     -0.577350269189626,  // -1.0 + 2.0 * C.x\r\n" + 
							"                      0.024390243902439); // 1.0 / 41.0\r\n" + 
							"// First corner\r\n" + 
							"  vec2 i  = floor(v + dot(v, C.yy) );\r\n" + 
							"  vec2 x0 = v -   i + dot(i, C.xx);\r\n" + 
							"\r\n" + 
							"// Other corners\r\n" + 
							"  vec2 i1;\r\n" + 
							"  //i1.x = step( x0.y, x0.x ); // x0.x > x0.y ? 1.0 : 0.0\r\n" + 
							"  //i1.y = 1.0 - i1.x;\r\n" + 
							"  i1 = (x0.x > x0.y) ? vec2(1.0, 0.0) : vec2(0.0, 1.0);\r\n" + 
							"  // x0 = x0 - 0.0 + 0.0 * C.xx ;\r\n" + 
							"  // x1 = x0 - i1 + 1.0 * C.xx ;\r\n" + 
							"  // x2 = x0 - 1.0 + 2.0 * C.xx ;\r\n" + 
							"  vec4 x12 = x0.xyxy + C.xxzz;\r\n" + 
							"  x12.xy -= i1;\r\n" + 
							"\r\n" + 
							"// Permutations\r\n" + 
							"  i = mod289(i); // Avoid truncation effects in permutation\r\n" + 
							"  vec3 p = permute( permute( i.y + vec3(0.0, i1.y, 1.0 ))\r\n" + 
							"		+ i.x + vec3(0.0, i1.x, 1.0 ));\r\n" + 
							"\r\n" + 
							"  vec3 m = max(0.5 - vec3(dot(x0,x0), dot(x12.xy,x12.xy), dot(x12.zw,x12.zw)), 0.0);\r\n" + 
							"  m = m*m ;\r\n" + 
							"  m = m*m ;\r\n" + 
							"\r\n" + 
							"// Gradients: 41 points uniformly over a line, mapped onto a diamond.\r\n" + 
							"// The ring size 17*17 = 289 is close to a multiple of 41 (41*7 = 287)\r\n" + 
							"\r\n" + 
							"  vec3 x = 2.0 * fract(p * C.www) - 1.0;\r\n" + 
							"  vec3 h = abs(x) - 0.5;\r\n" + 
							"  vec3 ox = floor(x + 0.5);\r\n" + 
							"  vec3 a0 = x - ox;\r\n" + 
							"\r\n" + 
							"// Normalise gradients implicitly by scaling m\r\n" + 
							"// Approximation of: m *= inversesqrt( a0*a0 + h*h );\r\n" + 
							"  m *= 1.79284291400159 - 0.85373472095314 * ( a0*a0 + h*h );\r\n" + 
							"\r\n" + 
							"// Compute final noise value at P\r\n" + 
							"  vec3 g;\r\n" + 
							"  g.x  = a0.x  * x0.x  + h.x  * x0.y;\r\n" + 
							"  g.yz = a0.yz * x12.xz + h.yz * x12.yw;\r\n" + 
							"  return 130.0 * dot(m, g);\r\n" + 
							"}\r\n" + 
							"\r\n" + 
							"float fnoise(vec2 v) {\r\n" + 
							"	return fract(sin(dot(v, vec2(12.9898, 78.233))) * 43758.5453) * 0.55;\r\n" + 
							"}\r\n" + 
							"\r\n" + 
							"float staticV(vec2 uv) {\r\n" + 
							"    float staticHeight = fnoise(vec2(9.0,time*1.2+3.0))*0.3+5.0;\r\n" + 
							"    float staticAmount = fnoise(vec2(1.0,time*1.2-6.0))*0.1+0.3;\r\n" + 
							"    float staticStrength = fnoise(vec2(-9.75,time*0.6-3.0))*2.0+2.0;\r\n" + 
							"	return (1.0-step(fnoise(vec2(5.0*pow(time,2.0)+pow(uv.x*7.0,1.2),pow((mod(time,100.0)+100.0)*uv.y*0.3+3.0,staticHeight))),staticAmount))*staticStrength;\r\n" + 
							"}\r\n" + 
							"\r\n" + 
							"void main()\r\n" + 
							"{\r\n" + 
							"\r\n" + 
							"	vec2 uv =  v_TexCoord.xy;\r\n" + 
							"	\r\n" + 
							"	float fuzzOffset = fnoise(vec2(time*15.0,uv.y*80.0))*0.003;\r\n" + 
							"	float largeFuzzOffset = fnoise(vec2(time*1.0,uv.y*25.0))*0.004;\r\n" + 
							"    \r\n" + 
							"    float vertMovementOn = (1.0-step(snoise(vec2(time*0.2,8.0)),0.4))*vertMovementOpt;\r\n" + 
							"    float vertJerk = (1.0-step(fnoise(vec2(time*1.5,5.0)),0.6))*vertJerkOpt;\r\n" + 
							"    float vertJerk2 = (1.0-step(fnoise(vec2(time*5.5,5.0)),0.2))*vertJerkOpt;\r\n" + 
							"    float yOffset = abs(sin(time)*4.0)*vertMovementOn+vertJerk*vertJerk2*0.3;\r\n" + 
							"    float y = mod(uv.y+yOffset,1.0);\r\n" + 
							"	\r\n" + 
							"	float xOffset = (fuzzOffset + largeFuzzOffset) * horzFuzzOpt;\r\n" + 
							"    \r\n" + 
							"    float staticVal = 0.0;\r\n" + 
							"   \r\n" + 
							"    for (float y = -1.0; y <= 1.0; y += 1.0) {\r\n" + 
							"        float maxDist = 5.0/200.0;\r\n" + 
							"        float dist = y/200.0;\r\n" + 
							"    	staticVal += staticV(vec2(uv.x,uv.y+dist))*(maxDist-abs(dist))*1.5;\r\n" + 
							"    }\r\n" + 
							"        \r\n" + 
							"    staticVal *= bottomStaticOpt;\r\n" + 
							"	\r\n" + 
							"	float red 	=   texture(	u_Texture, 	vec2(uv.x + xOffset -0.01*rgbOffsetOpt,y)).r+staticVal;\r\n" + 
							"	float green = 	texture(	u_Texture, 	vec2(uv.x + xOffset,	  y)).g+staticVal;\r\n" + 
							"	float blue 	=	texture(	u_Texture, 	vec2(uv.x + xOffset +0.01*rgbOffsetOpt,y)).b+staticVal;\r\n" + 
							"	\r\n" + 
							"	vec3 fcolor = vec3(red,green,blue);\r\n" + 
							"	float scanline = sin(uv.y*800.0)*0.04*scalinesOpt;\r\n" + 
							"	fcolor -= scanline;\r\n" + 
							"	\r\n" + 
							"	color = vec4(fcolor,1.0);\r\n" + 
							"}\r\n" 
						
					};
	

	

	

	
	
}
