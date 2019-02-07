/*
 *
 * M411 LB03 Bier Webservice
 *
 */

package ch.oconnor.WebService;


/**
 *
 * @author Christopher O'Connor
 * @date 22/01/2019
 *
 *
 *
 *
 * A simple Class to Hold Values from Json.
 *
 * Beers:  Are Created Using {@link #Item(String, String, String)}.
 *         They are Saved as {@code Item}.
 *
 * Styles: Are Created Using {@link #Item(String, String)}.
 *         They are Saved as {@code Item} and then {@link #id} is Used for {@link BeerAdmin#map} & {@link #name} is Filled into {@link MyMap}.
 *         GarbageCollector then collects {@code Item}.
 *
 */
public class Item {

	private String id;
	private String name;
	private String description;

	/**
	 * Constructs a new {@link Item}.
	 *
	 * @param id Holds Unique ID of {@link Item}.
	 * @param name Name of {@link Item}.
	 * @param description Description of {@link Item}.
	 */
	public Item(String id, String name, String description) {

		this.id          = id;
		this.name        = name;
		this.description = description;

	}

	/**
	 * Constructs a new {@code Item} only with ID & Name.
	 *
	 * @param id Holds Unique ID of {@code Item}.
	 * @param name Name of {@code Item}.
	 */
	public Item(String id, String name) {

		this(id, name, null);

	}


	public String getName() {

		return this.name;

	}

	public String getID() {

		return id;

	}

	/**
	 * Returns the String representation of the {@code Item}.
	 *
	 * @return String Representation of {@link Item}.
	 */
	@Override
	public String toString() {

		return String.format("\n\tID: %-8s -> %s\n\t\tDescription\n\t{\n%s\n\t}", id, name, description);

	}

	/**
	 * Returns the String representation of the {@code Item} (short Version) ID + Name.
	 *
	 * @return String Representation of {@link Item }.
	 */
	public String toSmallString() {

		return String.format("\t\t- %8s -> %s", id, name);

	}

}
