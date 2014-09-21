package dk.grydeske;

public class Main {

	public static void main(String[] args) {
		Checklist checklist = new Checklist("TODO List");

		checklist.addItemToUnfinished(new ListItem("My first task",1));
		checklist.addItemToUnfinished(new ListItem("My Second task",2));
		checklist.addItemToUnfinished(new ListItem("My Third task",3));




	}


}
