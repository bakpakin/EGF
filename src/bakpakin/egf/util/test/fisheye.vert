varying vec4 Vertex_UV;
void main()
{
    gl_Position = ftransform();
    Vertex_UV = gl_MultiTexCoord0;
}