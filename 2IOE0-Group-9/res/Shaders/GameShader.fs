#version 330

in  vec3 exColour;

out vec4 fragColor;

uniform sampler2D texture_sampler;

struct Light {
    vec3 position;

    vec3 ambient;
    vec3 diffuse;
    vec3 specular;

    float constant;
    float linear;
    float quadratic;
};

void main()
{


	// fragColor = vec4(exColour.x, exColour.x, exColour.x, 1.0);

	fragColor = texture2D(texture_sampler, exColour.xy)*vec4(exColour.x,exColour.y,0.0f,1.0f);
	
}