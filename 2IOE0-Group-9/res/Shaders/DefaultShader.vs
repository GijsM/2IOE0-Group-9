#version 140

in vec2 position;
in vec2 textureCoords;

out vec4 color;
out vec2 uvCoords;

uniform vec4 matColor;
uniform mat4 projection;
uniform vec4 offset;
uniform vec2 pixelScale;
uniform vec2 screenPos;

void main() {
    color = matColor;

    gl_Position = projection * vec4((position.xy * pixelScale) + screenPos, 0, 1);
    uvCoords = (textureCoords * offset.zw) + offset.xy;
}