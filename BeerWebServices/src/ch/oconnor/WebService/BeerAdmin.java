/*
 *
 * M411 LB03 Bier Webservice
 *
 */

package ch.oconnor.WebService;

import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 *
 * @author Christopher O'Connor
 * @date 22/01/2019
 *
 *
 *
 *
 * BeerAdmin Class
 *
 * Logic:
 * (Load Json, Parse Jason to Objects)
 *
 * Data Handling:
 * (Populate both {@link Map}, [{@link #map} & {@link MyMap#getMap()}]),
 * ({@link java.util.Collections#sort(List)}),
 * ({@link java.util.stream.Stream#filter(Predicate)}).
 *
 */
@SuppressWarnings("Duplicates")
public class BeerAdmin {

	/**
	 *
	 *
	 * TreeMap<Integer, MyMap>: (TreeMap sorted By Style ID)
	 *
	 *     <Integer> : ID of Style
	 *     <MyMap>   :
	 *
	 *                  String Name                 : Name of the Style
	 *                  LinkedHashMap<String, Item> : (LinkedHashMap sorted by Item.name)
	 *
	 *                                                  <String> : ID of Beer
	 *                                                  <Item>   :
	 *
	 *                                                              String id          : ID of Beer
	 *                                                              String name        : Name of Beer
	 *                                                              String description : Description of Beer
	 *
	 */
	TreeMap<Integer, MyMap> map = new TreeMap<>();


	/**
	 * Constructs a new {@link BeerAdmin} & Loads all Styles {@link #loadStyles()}.
	 */
	public BeerAdmin() {

		loadStyles();

	}


	/**
	 * Method to Download Json from Web API and convert to {@link Item} stored in {@link Data#getData()}.
	 * {@link Item} Can either be Styles or Beers depending where the MethodCall comes from.
	 *
	 * @param location Differentiation in URL depending on if Styles or Beers should be loaded. {@code "styles/" or "beers/"}.
	 * @param filter either Filter Beers by Style ID {@code "&styleId=5"} or No Filter (Empty String) {@code ""}.
	 * @throws Exception If Website isn't reachable, URL Malfunctioned or Gson Exceptions.
	 * @return {@link Data#getData()} Filled with Json Objects (Either Styles or Beers)
	 */
	private Data load(String location, String filter) {

		try {

			InputStreamReader reader = new InputStreamReader(new URL("http://api.brewerydb.com/v2/" + location + "?key=1511d0db4a1d6841481c672455358cff" + filter).openStream());

			return new Gson().fromJson(reader, Data.class);

		} catch (Exception e){ e.printStackTrace(); }

		return null;

	}


	/**
	 * Create Object {@link Data} with all Styles stored in {@link Data#getData()}.
	 *
	 * Populate {@link #map} with:
	 *  Integer Key   : id
	 *          Value : {@link MyMap} with Style Name
	 *
	 * {@link #map} is a TreeMap, therefore all Styles get Sorted by {@code Integer Key(ID)}.
	 */
	private void loadStyles() {

		Data data = load("styles/", "");

		for (Item i : data.getData()) {

			map.put(Integer.valueOf(i.getID()), new MyMap(i.getName()));

		}

	}


	/**
	 * {@link #load(String, String)} Download Json from Web API and convert Beers to {@link Item} stored in {@link Data#getData()}.
	 * Sort List {@link Data#getData()} by {@link Item#getName()}
	 * Populate a {@code new LinkedHashMap<String, Item>} Containing all Sorted Beers (0 - n) from StyleID by {@link Item#getName()}.
	 *
	 * @param idStyle Filter all Beers by StyleID in API.
	 * @return {@code new LinkedHashMap<String, Item>} Containing all Beers (0 - n) from Specified StyleID sorted by {@link Item#getName()}.
	 */
	public LinkedHashMap<String, Item> loadBeer(int idStyle) {

		LinkedHashMap<String, Item> currMap = new LinkedHashMap<>();

		Data data = load("beers/", "&styleId=" + idStyle);


		if(data.getData() != null) {

			data.getData().sort(Comparator.comparing(Item::getName));


			for (Item i : data.getData()) {

				currMap.put(i.getID(), i);

			}

		}

		return currMap;

	}


	/**
	 * Check If {@code idStyle} is a Valid idStyle Integer.
	 * Get LinkedHashMap of Specified {@code idStyle} containing all Beers.
	 * If LinkedHashMap == null, (It hasn't been created) creat it with {@link #loadBeer(int)}.
	 *
	 * Last: Print the List.
	 *
	 * @param idStyle Specified StyleID of which Beers should be gotten if not already and Printed.
	 */
	public void getBeerListForStyle(int idStyle) {


		if(map.containsKey(idStyle)) {

			if(map.get(idStyle).getMap() == null) {

				map.get(idStyle).setMap(loadBeer(idStyle));

			}

			printBeerList(map.get(idStyle).getMap());

		}
		else System.out.println("\n\t\t\t//> " + idStyle + "... Doesn't Exist...");

	}


	/**
	 * Method to get All existing Beers of All Styles.
	 * Loop through All Styles and get Beers from index Style.
	 * To Keep User Informed Print: "Loading {@code current index Style ID + Name}"
	 *
	 * Before the Loop starts set a timer and Display Time Elapsed when Method is Finished.
	 */
	public void loadAll() {

		long startTime = System.currentTimeMillis();

		map.forEach((key, value) -> {

			if(value.getMap() == null) {

				System.out.println(String.format(" -- Loading ...%4d -> %s", key, value.getName()));

				value.setMap(loadBeer(key));

			} else {

				System.out.println(String.format(" -- Already here %4d -> %s", key, value.getName()));

				}

		});

		//TODO: Test Double Format ... ?
		System.out.println(String.format("\n\n -- Finished The Download\t-/> Elapsed Time : %1$,.2f sec..",
														((double) (System.currentTimeMillis()-startTime) / 1000F)));

	}


	/**
	 * Simple Iteration of {@link #map}, Print all Keys + Names (Name Stored in Object {@link MyMap#getName()}).
	 */
	public void printStyles() {

		/*
		 * { Normal (Old Java < 8.0) }
		 *
		 *  for (Map.Entry<Integer, MyMap> entry : map.entrySet()) {
		 *
		 *      System.out.println(String.format("%5d -> %s", entry.getKey(), entry.getValue().getName()));
		 *
		 *  }
		 *
		 *
		 *
		 * Lambda (Java 8.0+):
		 */
		map.forEach((key, value) -> System.out.println(String.format("%5d -> %s", key, value.getName())));

	}


	/**
	 * Iterate {@link #map} and Filter out all Names that contain param,
	 * Collect these found results in a String with nice Formatting.
	 * Each Found Object is separated to the next with a newLine (\n).
	 *
	 * If the resultString isBlank (No Matches where found) Print "Not Found"
	 * Else Print the Matching Result String.
	 *
	 * @param search Search String to compare.
	 */
	public void printStyles(String search) {

		String message = "\n\n\t\t\tNo results Found for " + search + " ... :(";


		String result  = map.entrySet().stream() // Stream<Entry<Integer, MyMap>>

							.filter(entry -> entry.getValue().getName().contains(search))

								.map(entry -> String.format("\t/> %4d :: %s", entry.getKey(), entry.getValue().getName()))

									.collect(Collectors.joining("\n"));



		if (!result.isBlank()) message = "\n\n\t\t\tYour Search Matches :/>\n" + result;

		System.out.println(message);

	}


	/**
	 * Nested ForEach for {@link #map} and {@link MyMap#getMap()}
	 *
	 * Print Style Class and all Beers of that Style.
	 * If a Style isNull or Empty Print ("Empty...").
	 *
	 * Lambda
	 */
	public void printAll() {

		map.forEach((key, value) -> {

			System.out.println(String.format("\n%4d -> %s:", key, value.getName()));

			if(value.getMap() != null) {

				if (!(value.getMap().isEmpty())) {

					value.getMap().forEach((subKey, subValue) ->

							System.out.println(subValue.toSmallString())

					);

				}

			} else System.out.println("\t\t\t:> Empty...");

		});

	}


	/**
	 *
	 * @param map
	 */
	private void printBeerList(Map<String, Item> map) {

		/*
		 * { Normal (Old Java < 8.0) }
		 *
		 *  for (Map.Entry<String, Item> entry : map.entrySet()) {
		 *
		 *      System.out.println(String.format("\t%6s -> %s", entry.getKey(), entry.getValue().getName()));
		 *
		 *  }
		 *
		 *
		 *
		 * Lambda:
		 */
		System.out.println(String.format("\n\t\t%s | %12s\n", "Beer:ID", "Beer:Name"));
		map.forEach((key, value) -> System.out.println(String.format("\t\t%6s -> %s", key, value.getName())));


	}


	/**
	 * Iterate through {@link #map} (All Styles and get their {@link MyMap#getMap()}.
	 * Check the Beer Map if its not null and not empty, if the id exists.
	 *
	 * If it exists (can only be once since its an Unique ID) Override String result with matching {@link Item#toString()}.
	 * Print wrp.result.
	 *
	 * @param id Specified ID of Beer Looking for.
	 *
	 * Lambda
	 */
	public void printBeer(String id) {

		// Wrapper to assign value to String result in a Lambda Function.
		var wrp = new Object(){ String result = "\t\t\t!!! -> " + id + " ... Not Found ?... "; };

		map.forEach((key, value) -> {

			if(value.getMap() != null) {

				if(value.getMap().containsKey(id)) {

					wrp.result = value.getMap().get(id).toString();

				}

			}

		});

		System.out.println("\n" + wrp.result);

	}

}
