package bakpakin.egf.tilemap;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class TMXLoader implements TileMapLoader {

	@Override
	public TileMapBean load(URL url) throws IOException {

		Document doc = null;
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(url.openStream());
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		doc.getDocumentElement().normalize();
		
		//load basic map properties
		System.out.println(doc.getDocumentElement());

		return null;
	}

}
