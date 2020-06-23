#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec3 inColour;
layout (location=2) in vec3 normals;

out vec3 exColour;
out vec2 texCoordinates;
out vec3 outNormals;
out vec3 fragpos;

uniform mat4 modelViewMatrix;

uniform mat4 projectionMatrix;

void main()
{


    outNormals = normals;
    gl_Position = projectionMatrix * modelViewMatrix * vec4(position, 1.0);
    exColour = inColour;
    fragpos = vec3(modelViewMatrix*vec4(position,1.0));
}

