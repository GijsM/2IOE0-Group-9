#version 140

uniform sampler2D sampler;

in vec4 color;
in vec2 uvCoords;

void main() {
    gl_FragColor = color * texture2D(sampler, uvCoords);
}