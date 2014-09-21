package dk.grydeske;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class StatisticsService {


	public StatisticsService() {
	}

	public void reportStatistics(String description, int total) {
		try {
			URL url = new URL(/* STATISTICS ETC report*/ "http://google.com?description=" + description + "&total=" + total);
			BufferedReader in = new BufferedReader(
					new InputStreamReader(url.openStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
			}
			in.close();
		} catch( Exception ignore) {
			//Ignore...
			System.out.println("Ugly shit happened");
		}
	}

}
