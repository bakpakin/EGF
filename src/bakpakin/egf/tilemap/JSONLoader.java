package bakpakin.egf.tilemap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import bakpakin.egf.util.AssetManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Loads JSON map files
 * @author Calvin
 * @see TileMapLoader
 *
 */
public class JSONLoader implements TileMapLoader {

	@Override
	public TileMapBean load(URL url) throws IOException {
		AssetManager.initProtocols();
		InputStream in = url.openStream();
		Gson gson = new GsonBuilder().create();
        TileMapBean ret = gson.fromJson(new BufferedReader(new InputStreamReader(in)), TileMapBean.class);
        ret.setURL(url);
        return ret;
	}

}
