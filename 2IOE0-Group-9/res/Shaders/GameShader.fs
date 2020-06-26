#version 330

in  vec3 exColour;
in  vec3 fragpos;
in vec3 outNormals;

out vec4 fragColor;

uniform sampler2D texture_sampler;
uniform vec3 lightPos;
uniform vec3 viewPos;
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
void main()

{



    vec3 lightColor = vec3(0.7,0.5,0.7f);
    float ambientStrength = 0.2f;
    vec3 ambient = ambientStrength*lightColor;
    vec3 norm = normalize(outNormals);



    vec3 lightDir = normalize(lightPos-fragpos);



    float diff = max(dot(norm,lightDir),0.0);
    vec3 diffuse = diff*lightColor;
    vec3 objectColor = texture2D(texture_sampler, exColour.xy / 0.5).xyz;
    	// fragColor = vec4(exColour.x, exColour.x, exColour.x, 1.0);




    float specularStrength = 0.5;
    vec3 viewDir = normalize(viewPos - fragpos);
    vec3 reflectDir = reflect(-lightDir,norm);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), 32);
    vec3 specular = specularStrength * spec * lightColor;
    vec3 result = (ambient + diffuse + specular)*objectColor;
	fragColor = vec4(result,1.0);
}