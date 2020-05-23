#version 330

uniform sampler2D texture_0;
uniform sampler2D texture_1;

uniform int operation;
const int OPERATION_NONE=0;
const int OPERATION_ADD=1;
const int OPERATION_SUB=2;
const int OPERATION_MUL=3;
const int OPERATION_OVERLAY=4;

in vec2 vs_out_uv;
out vec4 fs_out_color;

vec4 Add(vec4 color_0,vec4 color_1){
    return clamp(color_0+color_1,0.0,1.0);
}
vec4 Sub(vec4 color_0,vec4 color_1){
    return clamp(color_0-color_1,0.0,1.0);
}
vec4 Mul(vec4 color_0,vec4 color_1){
    return clamp(color_0*color_1,0.0,1.0);
}
vec4 Overlay(vec4 color_0,vec4 color_1){
    vec4 color=color_0*color_0.a+color_1*(1.0-color_0.a);
    return clamp(color,0.0,1.0);
}

void main(){
    vec4 color_0=texture(texture_0,vs_out_uv);
    vec4 color_1=texture(texture_1,vs_out_uv);

    vec4 color;
    switch(operation){
    case OPERATION_ADD:
        color=Add(color_0,color_1);
        break;
    case OPERATION_SUB:
        color=Sub(color_0,color_1);
        break;
    case OPERATION_MUL:
        color=Mul(color_0,color_1);
        break;
    case OPERATION_OVERLAY:
        color=Overlay(color_0,color_1);
        break;
    default:
        color=vec4(0.0);
        break;
    }

    fs_out_color=color;
}
