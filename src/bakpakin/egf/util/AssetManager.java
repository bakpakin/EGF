package bakpakin.egf.util;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.ARBShaderObjects.*;
import static org.lwjgl.openal.AL10.alDeleteBuffers;

import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import bakpakin.egf.protocols.CustomURLScheme;

/**
 * This class is a static loader for game assets. Assets can be retrieved by
 * their urls or classpaths.  Loading is done automatically, and this class 
 * keeps a static map of loaded assets to avoid reloading them and
 * creating duplicates in memory. Also provides static methods for
 * deleting loaded assets that cannot be garbage collected due to native dependencies.
 * @author Calvin
 *
 */
public class AssetManager {

	static {
		initProtocols();
	}

	//Regex that splits on the extension of a file.
	private static final String EXTENSION_REGEX = "\\.(?=[^\\.]+$)";

	//If the URL protocol that allows loading from the classpath has been initiated.
	private static boolean initiated;

	private AssetManager() {
		//defeat instantiation
	}

	/**
	 * Allows creation of {@link URL} that reference the classpath with the "classpath:" protocol.
	 * This only needs to be called externally if constructing URLs before loading ths class.
	 * <br/>
	 * For example, to load a sound from in package <code>com.mypackage</code> called "beep.wav",
	 * the url string would be "classpath:com/mypackage/beep.wav".
	 */
	public static void initProtocols() {
		if (!initiated) {
			try {
				CustomURLScheme.add(bakpakin.egf.protocols.classpath.Handler.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			initiated = true;
		}
	}

	/**
	 * Contains a map of all loaded textures keyed by their urls.
	 */
	private static Map<String, Texture> textures;
	
	/**
	 * Contains a map of all loaded fonts keyed by their urls.
	 */
	private static Map<String, TrueTypeFont> fonts;
	
	/**
	 * Contains a map of all loaded shaders keyed by their urls.
	 */
	private static Map<String, ShaderPointer> shaders;
	
	/**
	 * Contains a map of all loaded sounds keyed by their urls.
	 */
	private static Map<String, Audio> sounds;

	/**
	 * Loads a URL from a string. Does the same as the one argument String
	 * constructor for the URL class, but if no protocol is provided, will try
	 * prefixing the "classpath:" protocol and the "file:" protocol in that order.
	 * @param url
	 * @return a valid URL
	 */
	public static URL stringToURL(String url) {
		initProtocols();
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			try {
				return new URL("classpath:" + url);
			} catch (MalformedURLException e2) {
				try {
					return new URL("file:" + url);
				} catch (MalformedURLException e3) {
					e.printStackTrace();
					return null;
				}
			}
		}
	}

	/**
	 * Loads a {@link Texture} from url path. If this Texture has already been loaded, 
	 * returns the already loaded texture.
	 * @param url 
	 * @return the texture
	 */
	public static Texture getTexture(String url) {
		return getTexture(stringToURL(url));
	}

	/**
	 * Loads a {@link Texture} from the url. If this Texture has already been loaded, 
	 * returns the already loaded texture.
	 * @param url
	 * @return the texture
	 */
	public static Texture getTexture(URL url) {
		initProtocols();
		if (textures == null) {
			textures = new HashMap<String, Texture>(50, 1f);
		}
		String key = url.getPath();
		Texture tex = textures.get(key);
		if (tex == null) {
			String[] parts = key.split(EXTENSION_REGEX);
			String extension = parts[parts.length - 1].toUpperCase();
			try {
				InputStream inputStream = new BufferedInputStream(url.openStream());
				tex = TextureLoader.getTexture(extension, inputStream);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			textures.put(key, tex);
		}
		return tex;
	}

	/**
	 * Frees the texture in memory if it has already been loaded.
	 * @param url the URL of the loaded texture
	 */
	public static void freeTexture(URL url) {
		freeTexture(url.getPath());
	}

	/**
	 * Frees the texture in memory if it has already been loaded.
	 * @param url the url path of the loaded texture
	 */
	public static void freeTexture(String url) {
		url = stringToURL(url).getPath();
		if (textures == null) {
			textures = new HashMap<String, Texture>(50, 1f);
		}
		Texture tex = textures.remove(url);
		if (tex != null) {
			tex.release();
		}
	}
	
	/**
	 * Frees all currently loaded Textures from memory.
	 */
	public static void freeAllTextures() {
		if (textures == null) {
			textures = new HashMap<String, Texture>(50, 1f);
		}
		for (String key : textures.keySet()) {
			Texture tex = textures.get(key);
			if (tex != null) {
				tex.release();
			}		
		}
		textures.clear();
	}

	/**
	 * Loads a font from a url. If a font from this url of this size has
	 * already been loaded, returns that.
	 * @param url
	 * @param size
	 * @return the loaded font or previously loaded font.
	 */
	public static TrueTypeFont getFont(String url, float size) {
		return getFont(stringToURL(url), size);
	}

	/**
	 * Loads a font from a url. If a font from this url of this size has
	 * already been loaded, returns that.
	 * @param url url of font file
	 * @param size size of font
	 * @return the loaded font or previously loaded font.
	 */
	public static TrueTypeFont getFont(URL url, float size) {
		initProtocols();
		TrueTypeFont fnt;
		if (fonts == null) {
			fonts = new HashMap<String, TrueTypeFont>(25, 1f);
		}

		String key = url.getPath() + "_" + Float.toString(size);

		if ((fnt = fonts.get(key)) == null) {
			try {
				InputStream inputStream	= new BufferedInputStream(url.openStream());
				Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
				awtFont = awtFont.deriveFont(size); // set font size
				fnt = new TrueTypeFont(awtFont, false);
				fonts.put(key, fnt);
				return fnt;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return fnt;
	}

	/**
	 * Frees an already loaded font from memory.
	 * @param url
	 * @param size
	 */
	public static void freeFont(String url, float size) {
		url = stringToURL(url).getPath();
		if (fonts == null) {
			fonts = new HashMap<String, TrueTypeFont>(25, 1f);
		}
		fonts.remove(url + "_" + Float.toString(size));
	}

	/**
	 * Frees an already loaded font from memory.
	 * @param url
	 * @param size
	 */
	public static void freeFont(URL url, float size) {
		freeFont(url.getPath(), size);
	}
	
	/**
	 * Frees all loaded fonts from memory.
	 */
	public static void freeAllFonts() {
		if (fonts == null) {
			fonts = new HashMap<String, TrueTypeFont>(25, 1f);
		}
		fonts.clear();
	}

	/**
	 * This class stores the openGL pointer to the vertex and fragments shader that
	 * constitute a program.
	 * @author Calvin
	 *
	 */
	private static class ShaderPointer {
		public ShaderPointer(int shaderProgram, int vertexShader, int fragmentShader) {
			this.shaderProgram = shaderProgram;
			this.vertexShader = vertexShader;
			this.fragmentShader = fragmentShader;
		}

		public int vertexShader;
		public int fragmentShader;
		public int shaderProgram;
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + fragmentShader;
			result = prime * result + shaderProgram;
			result = prime * result + vertexShader;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof ShaderPointer))
				return false;
			ShaderPointer other = (ShaderPointer) obj;
			if (fragmentShader != other.fragmentShader)
				return false;
			if (shaderProgram != other.shaderProgram)
				return false;
			if (vertexShader != other.vertexShader)
				return false;
			return true;
		}	
	}

	/**
	 * Loads an ARB shader program from the specified url. Searches for
	 * a vertex and fragment shader with extension ".vert" and
	 * ".frag" respectively.
	 * <br/>
	 * Equivalent to:<br/><code>
	 * AssetManager.getShader(shaderUrl + ".vert", shaderUrl + ".frag")
	 * </code>
	 * @param shaderUrl
	 * @return openGL int pointer to the program
	 */
	public static int getShader(String shaderUrl) {
		return getShader(shaderUrl + ".vert", shaderUrl + ".frag");
	}

	/**
	 * Loads an ARB shader program from the specified urls.
	 * @param vertexShaderUrl - url of the fragment shader
	 * @param fragmentShaderUrl - url of the vertex shader
	 * @return openGL int pointer to the program
	 */
	public static int getShader(String vertexShaderUrl, String fragmentShaderUrl) {
		return getShader(stringToURL(vertexShaderUrl), stringToURL(fragmentShaderUrl));
	}

	/**
	 * Loads an ARB shader program from the specified urls.
	 * @param vertexShaderUrl - url of the fragment shader
	 * @param fragmentShaderUrl - url of the vertex shader
	 * @return openGL int pointer to the program
	 */
	public static int getShader(URL vertexShaderUrl, URL fragmentShaderUrl) {		
		initProtocols();
		if (shaders == null) {
			shaders = new HashMap<String, ShaderPointer>();
		}

		ShaderPointer program = null;
		String key = vertexShaderUrl.getPath() + "_" + fragmentShaderUrl.getPath();

		if ((program = shaders.get(key)) == null) {
			try {
				int shaderProgram = glCreateProgramObjectARB();
				int vertexShader = glCreateShaderObjectARB(GL_VERTEX_SHADER);
				int fragmentShader = glCreateShaderObjectARB(GL_FRAGMENT_SHADER);

				//Load shader source from files
				StringBuilder vertexShaderSource = new StringBuilder();
				StringBuilder fragmentShaderSource = new StringBuilder();

				//Read in vertex shader
				BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(vertexShaderUrl.openStream())));
				String line;
				while((line = reader.readLine()) != null) {
					vertexShaderSource.append(line).append('\n');
				}
				reader.close();

				//Read in fragment shader
				reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(fragmentShaderUrl.openStream())));
				while((line = reader.readLine()) != null) {
					fragmentShaderSource.append(line).append('\n');
				}
				reader.close();

				//Compile vertex shader
				glShaderSourceARB(vertexShader, vertexShaderSource);
				glCompileShaderARB(vertexShader);
				if (glGetObjectParameteriARB(vertexShader, GL_OBJECT_COMPILE_STATUS_ARB) == GL_FALSE) {
					throw new Exception("Vertex shader did not compile.");
				}

				//Compile fragment shader
				glShaderSourceARB(fragmentShader, fragmentShaderSource);
				glCompileShaderARB(fragmentShader);
				if (glGetObjectParameteriARB(fragmentShader, GL_OBJECT_COMPILE_STATUS_ARB) == GL_FALSE) {
					throw new Exception("Fragment shader did not compile.");
				}				
				//Combine vertex and fragment shaders
				glAttachObjectARB(shaderProgram, vertexShader);
				glAttachObjectARB(shaderProgram, fragmentShader);
				glLinkProgramARB(shaderProgram);
				glValidateProgramARB(shaderProgram);

				shaders.put(key, (program = new ShaderPointer(shaderProgram, vertexShader, fragmentShader)));

				glDeleteObjectARB(program.vertexShader);
				glDeleteObjectARB(program.fragmentShader);
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		}

		return program.shaderProgram;
	}

	/**
	 * Frees the loaded shader from memory.<br/>
	 * Equivalent to:<br/><code>
	 * AssetManager.freeShader(shaderUrl + ".vert, shaderUrl + ".frag")
	 * </code>
	 * @param shaderUrl
	 */
	public static void freeShader(String shaderUrl) {
		freeShader(shaderUrl + ".vert", shaderUrl + ".frag");
	}

	/**
	 * Frees the loaded shader from memory.
	 * @param vertexShaderUrl
	 * @param fragmentShaderUrl
	 */
	public static void freeShader(URL vertexShaderUrl, URL fragmentShaderUrl) {
		freeShader(vertexShaderUrl.getPath(), fragmentShaderUrl.getPath());
	}

	
	/**
	 * Frees the loaded shader from memory.
	 * @param vertexShaderUrl
	 * @param fragmentShaderUrl
	 */
	public static void freeShader(String vertexShaderUrl, String fragmentShaderUrl) {
		vertexShaderUrl = stringToURL(vertexShaderUrl).getPath();
		fragmentShaderUrl = stringToURL(fragmentShaderUrl).getPath();
		if (shaders == null) {
			shaders = new HashMap<String, ShaderPointer>();
		}
		String key = vertexShaderUrl + "_" + fragmentShaderUrl;
		ShaderPointer shaderPointer = shaders.remove(key);
		if (shaderPointer != null) {
			glDeleteObjectARB(shaderPointer.shaderProgram);
		}
	}
	
	/**
	 * Frees all loaded shaders from memory.
	 */
	public static void freeAllShaders() {
		if (shaders == null) {
			shaders = new HashMap<String, ShaderPointer>();
		}
		for (ShaderPointer shaderPointer : shaders.values()) {
			if (shaderPointer != null) {
				glDeleteObjectARB(shaderPointer.shaderProgram);
			}
		}
		shaders.clear();
			
	}
	
	/**
	 * Loads a sound from the specified url.
	 * @param url
	 * @return the loaded sound
	 */
	public static Audio getSound(String url) {
		return getSound(stringToURL(url));
	}
	
	/**
	 * Loads a sound from the specified url.
	 * @param url
	 * @return the loaded sound
	 */
	public static Audio getSound(URL url) {
		initProtocols();
		if (sounds == null) {
			sounds = new HashMap<String, Audio>(50, 1f);
		}
		String key = url.getPath();
		Audio audio = sounds.get(key);
		if (audio == null) {
			String[] parts = key.split(EXTENSION_REGEX);
			String extension = parts[parts.length - 1].toUpperCase();
			try {
				InputStream inputStream = new BufferedInputStream(url.openStream());
				audio = AudioLoader.getAudio(extension, inputStream);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			sounds.put(key, audio);
		}
		return audio;
	}
	
	/**
	 * Returns a streaming audio from the specified url. Streaming audio does not load
	 * the whole audio file into memory, but streams it from a source. Good for large files.
	 * @param url
	 * @return the loaded sound
	 */
	public static Audio streamSound(String url) {
		return streamSound(stringToURL(url));
	}
	
	/**
	 * Returns a streaming audio from the specified url. Streaming audio does not load
	 * the whole audio file into memory, but streams it from a source. Good for large files.
	 * @param url
	 * @return the loaded sound
	 */
	public static Audio streamSound(URL url) {
		initProtocols();
		if (sounds == null) {
			sounds = new HashMap<String, Audio>(50, 1f);
		}
		String key = url.getPath();
		Audio audio = sounds.get(key);
		if (audio == null) {
			String[] parts = key.split(EXTENSION_REGEX);
			String extension = parts[parts.length - 1].toUpperCase();
			try {
				audio = AudioLoader.getStreamingAudio(extension, url);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			sounds.put(key, audio);
		}
		return audio;
	}
	
	/**
	 * Frees the sound loaded from the url.
	 * @param url
	 */
	public static void freeSound(URL url) {
		freeSound(url.getPath());
	}
	
	/**
	 * Frees the sound loaded from the url.
	 * @param url
	 */
	public static void freeSound(String url) {
		url = stringToURL(url).getPath();
		if (sounds == null) {
			sounds = new HashMap<String, Audio>();
		}
		Audio audio = sounds.remove(url);
		if (audio != null) {
			alDeleteBuffers(audio.getBufferID());
		}
	}
	
	/**
	 * Frees all currently loaded sounds.
	 */
	public static void freeAllSounds() {
		if (sounds == null) {
			sounds = new HashMap<String, Audio>();
		}
		for (Audio audio : sounds.values()) {
			if (audio != null) {
				alDeleteBuffers(audio.getBufferID());
			}
		}
		sounds.clear();
	}
	
	/**
	 * Frees all resources currently loaded by {@link AssetLoader}.
	 */
	public static void cleanUp() {
		freeAllTextures();
		freeAllShaders();
		freeAllFonts();
		freeAllSounds();
	}

}


