package dk.grydeske;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class Checklist {

	private String name;

	private ListItem[] completedItems;
	private ListItem[] unfinishedItems;
	private int completedItemsCount;
	private int unfinishedItemsCount;
	private StatisticsService statisticsService;

	public Checklist(String name) {
		this.name = name;
		completedItems = new ListItem[10];
		unfinishedItems = new ListItem[10];
		completedItemsCount = 0;
		unfinishedItemsCount = 0;
		statisticsService = new StatisticsService();
	}

	public void addItemToUnfinished(ListItem item) {
		unfinishedItems[unfinishedItemsCount] = item;
		unfinishedItemsCount++;
	}

	public ListItem findUnfinishedItem(String description) {
		return findItemInList(description, unfinishedItems, unfinishedItemsCount);
	}

	public ListItem findCompletedItem(String description) {
		return findItemInList(description, unfinishedItems, unfinishedItemsCount);
	}

    private ListItem findItemInList(String description, ListItem[] items, int count) {
        for( int i = 0; i < count; i++) {
            if( items[i].description.equals(description)) {
                return items[i];
            }
        }
        return null;
    }

    /**
     * Removes the item from the unfinished and places it with the finished items.
     *
     * Also reports the completion to remote URL
     * @param item
     */
    public void completeItem(ListItem item) throws ItemNotFoundException {
        int location = findLocationInList(item);
        removeFromUncompleted(location);
        addToCompleted(item);
        statisticsService.reportStatistics(item.description, item.getTotalMinutesUsed());
    }

    private int findLocationInList(ListItem item) throws ItemNotFoundException {
        int location = -1;
        for( int i = 0; i < unfinishedItemsCount; i++) {
            if( unfinishedItems[i] == item) {
                location = i;
                break;
            }
        }

        if( location == -1) {
            throw new ItemNotFoundException("Item not located in unfinished items");
        }
        return location;
    }

    private void removeFromUncompleted(int location) {
        for( int i = location; i < unfinishedItemsCount; i++) {
            unfinishedItems[i] = unfinishedItems[i+1];
        }
        unfinishedItemsCount--;
    }

    private void addToCompleted(ListItem item) {
        completedItems[completedItemsCount] = item;
        completedItemsCount++;
    }

	// unfinishedCount
	// completedCount
	// getFirst task

	public ListItem getFirstUncompletedItem() {
		return unfinishedItems[0];
	}


	public ArrayList<ListItem> getAllUnfinishedItems() {
		ArrayList<ListItem> items = new ArrayList<ListItem>();
		for(int i = 0; i < unfinishedItemsCount; i++) {
			items.add(unfinishedItems[i]);
		}
		return items;
	}

	// FIXME Replace by StringBuilder
	@Override
	public String toString() {
		String stringValue = "Checklist: " + name + "\n";
		stringValue += "\tUnfinished items\n";
		for(ListItem i : unfinishedItems) {
            if( i != null) {
                stringValue += "\t\t" + i.toString() +"\n";
            }
		}
		stringValue += "\tCompleted items\n";
		for(ListItem i : completedItems) {
            if( i != null) {
                stringValue += "\t\t" + i.toString() +"\n";
            }
		}
		return stringValue;
	}

}
