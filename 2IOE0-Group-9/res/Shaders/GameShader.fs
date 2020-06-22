#version 330

in  vec3 exColour;

out vec4 fragColor;

uniform sampler2D texture_sampler;


void main()
{
	// fragColor = vec4(exColour.x, exColour.x, exColour.x, 1.0);
	fragColor = texture2D(texture_sampler, exColour.xy / 0.5) * vec4(exColour.xy, 0.0f, 1.0f);
}