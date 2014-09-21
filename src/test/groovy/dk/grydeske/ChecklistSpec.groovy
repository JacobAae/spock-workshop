package dk.grydeske

import spock.lang.Specification

class ChecklistSpec extends Specification {


	void "Check all new items are added with 0 minutes"() {
		setup:
		Checklist checklist = new Checklist()

		when:
		3.times { checklist.addItemToUnfinished(new ListItem("Item $it", 10))}

		// To make it fail
		// checklist.unfinishedItems[1].addToMinuttesUsed(1)

		then:
		checklist.allUnfinishedItems.every { it.totalMinutesUsed == 0 }

		// But better to do this (for reporting errors
		// checklist.allUnfinishedItems.totalMinutesUsed.every { it == 0 }
	}
}
