/*
 *
 * M411 LB03 Bier Webservice
 *
 */

package ch.oconnor.WebService;

import java.util.LinkedHashMap;
import java.util.function.BiConsumer;


/**
 *
 * @author Christopher O'Connor
 * @date 22/01/2019
 *
 *
 *
 *
 * A simple {@link LinkedHashMap} Wrapper Class
 * to also hold a String value for the Name of the Beer Styles.
 *
 * {@link #map} Holds all {@link Item} (Beers) of its Style.
 *
 * LinkedHashMap is used because {@link BeerAdmin#loadBeer(int)}
 * Sorts Beers after Name (Alphabetical) and then Constructs a {@code LinkedHashMap}
 * to maintain a nice Order while {@link LinkedHashMap#forEach(BiConsumer)} Looping.
 *
 */
public class MyMap {


	private String name;
	private LinkedHashMap<String, Item> map;


	/**
	 * Constructs a new {@link MyMap}.
	 *
	 * @param name Style Name of the Beer
	 */
	public MyMap(String name) {

		this.name = name;

	}

	public String getName() {

		return name;

	}

	public LinkedHashMap<String, Item> getMap() {

		return map;

	}

	public void setMap(LinkedHashMap<String, Item> map) {

		this.map = map;

	}

}
