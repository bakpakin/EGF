#version 110
uniform sampler2D tex0;

// The width and height of each pixel in texture coordinates
const float pixelWidth = 1.0 / 1024.0;
const float pixelHeight = 1.0 / 1024.0;

void main()
{
	// Current texture coordinate
	vec2 texel = gl_TexCoord[0].xy;
	vec4 pixel = vec4(texture2D(tex0, texel));
	
	// Larger constant = bigger glow
	float glow = 4.0 * ((pixelWidth + pixelHeight) / 2.0);
	
	// The vector to contain the new, "bloomed" colour values
	vec4 bloom = vec4(0, 0, 0, 0);
	
	// Loop over all the pixels on the texture in the area given by the constant in glow
	float count = 0.0;
	
	for(float x = texel.x - glow; x < texel.x + glow; x += pixelWidth)
	{
		for(float y = texel.y - glow; y < texel.y + glow; y += pixelHeight)
		{
			// Add that pixel's value to the bloom vector
			bloom += (texture2D(tex0, vec2(x, y)) - 0.4) * 30.0;
			// Add 1 to the number of pixels sampled
			count += 1.0;
		}
	}
	
	// Divide by the number of pixels sampled to average out the value
	// The constant being multiplied with count here will dim the bloom effect a bit, with higher values
	// Clamp the value between a 0.0 to 1.0 range
	float val = 1.0 / (count * 30.0);
	vec4 factor = vec4(val, val, val, val);
	bloom *= factor;
	
	// Set the current fragment to the original texture pixel, with our bloom value added on
	gl_FragColor = pixel + bloom;
}
