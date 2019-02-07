/*
 *
 * M411 LB03 Bier Webservice
 *
 */

package ch.oconnor.WebService;

import java.util.LinkedHashMap;
import java.util.List;


/**
 *
 * @author Christopher O'Connor
 * @date 22/01/2019
 *
 *
 *
 *
 * A simple Class to match the Json import and store multiple {@link Item} in {@link #data}.
 * {@link Item} can contain either Styles or Beers.
 *
 * {@link #data} is used by {@link BeerAdmin} to Fill {@link BeerAdmin#map} if it contains Styles.
 * {@link #data} is used by {@link BeerAdmin} to Fill {@link MyMap#setMap(LinkedHashMap)} if it contains Beers after being Sorted by {@link Item#getName()}.
 *
 */
public class Data {

	private List<Item> data;

	/**
	 * Constructs a new {@link Data}.
	 *
	 * @param data List Containing {@link Item} from Json either Styles or Beers.
	 */
	public Data(List<Item> data) {

		this.data = data;

	}

	public List<Item> getData() {

		return data;

	}

}
