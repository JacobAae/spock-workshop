package dk.grydeske;

import java.util.ArrayList;
import java.util.Date;

public class ListItem {

	String description;
	Date dateCompleted;
	ArrayList<Integer> minutesUsed;
	Double pricePerUsedMinute;

	public ListItem(String description, double pricePerUsedMinute) {
		this.description = description;
		dateCompleted = null;
		minutesUsed = new ArrayList<Integer>();
		this.pricePerUsedMinute = pricePerUsedMinute;
	}

	public void completeItem() {
		dateCompleted = new Date();
	}

	public Date getDateCompleted() {
		return dateCompleted;
	}

	public int addToMinuttesUsed(int used) {
		minutesUsed.add(used);
		return getTotalMinutesUsed();
	}

	public int getTotalMinutesUsed() {
		int used = 0;
		for( Integer i : minutesUsed) {
			used += i;
		}
		return used;
	}

	public int getNumberOfTimesHandled() {
		return minutesUsed.size();
	}

	public double getTotalPrice() {
		// FIXME Can be optimzed
		double total = 0;
		for( Integer i : minutesUsed) {
			total += i * pricePerUsedMinute;
		}
		return total;
	}

	@Override
	public String toString() {
		return "'" + description + "' pricePerUsedMinute: " + pricePerUsedMinute + " minutesUsed: " + minutesUsed;
	}

}
