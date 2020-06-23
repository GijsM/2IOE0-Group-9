#version 330

in  vec3 exColour;
in  vec3 fragpos;
in vec3 outNormals;
out vec4 fragColor;

uniform sampler2D texture_sampler;
uniform vec3 lightPos;

void main()

{
    vec3 lightColor = vec3(0.7f,0.5f,0.5f);
    float ambientStrength = 0.1f;
    vec3 ambient = ambientStrength*lightColor;
    vec3 norm = normalize(outNormals);
    vec3 lightDir = normalize(lightPos - fragpos);
    float diff = max(dot(norm,lightDir),0.0);
    vec3 diffuse = diff*lightColor;
    vec3 objectColor = texture2D(texture_sampler, exColour.xy / 0.5).xyz;
    	// fragColor = vec4(exColour.x, exColour.x, exColour.x, 1.0);
    vec3 result = (ambient+diffuse)*objectColor;
	fragColor = vec4(result,1.0);
}