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

    void "Test findLocationInList throws exception, if element is not found"() {
        setup:
        Checklist checklist = new Checklist()

        when:
        checklist.findLocationInList(new ListItem('Not existing',2))

        then:
        thrown(ItemNotFoundException)
    }

    void "Find item in list returns the correct location"() {
        setup:
        Checklist checklist = new Checklist()
        5.times { checklist.addItemToUnfinished(new ListItem("Item $it", 10))}

        expect:
        checklist.findLocationInList(checklist.unfinishedItems[i]) == i

        where:
        i << [0,1,2,3,4]
    }

    void "Test toString ikke kaster exceptions når listen er tom"() {
        expect:
        new Checklist().toString()
    }

    void "Test toString ikke kaster exceptions når listen er ikke tom"() {
        setup:
        Checklist checklist = new Checklist()
        checklist.addItemToUnfinished(new ListItem("Item 1", 10))
        checklist.completedItems[0] = new ListItem("Item 2", 10)
        checklist.completedItemsCount = 1

        expect:
        checklist.unfinishedItemsCount == 1
        checklist.completedItemsCount == 1
        checklist.toString()
    }


    void "Test removeFromUncompleted removes item from the list"() {
        setup:
        Checklist checklist = new Checklist()
        3.times { checklist.addItemToUnfinished(new ListItem("Item $it", 10))}
        String description = checklist.unfinishedItems[1].description ;

        when:
        checklist.removeFromUncompleted(1)

        then:
        checklist.unfinishedItemsCount == old(checklist.unfinishedItemsCount) -1
        !checklist.allUnfinishedItems.description.any { it == description }

    }

    void "Test addToCompleted"() {
        given:
        Checklist checklist = new Checklist('Dummy name')

        expect:
        checklist.completedItemsCount == 0

        when:
        checklist.addToCompleted(new ListItem('Dummy item',0))

        then:
        checklist.completedItemsCount == 1
    }

    void "Test findUnfinishedItem returns null when not empty"() {
        given:
        Checklist checklist = new Checklist('Dummy name')

        expect:
        checklist.findUnfinishedItem('') == null

    }

    void "Test findCompletedItem returns null when not empty"() {
        given:
        Checklist checklist = new Checklist('Dummy name')

        expect:
        checklist.findCompletedItem('') == null

    }

    void "Test findItemInList returns the item"() {
        setup:
        Checklist checklist = new Checklist()
        ListItem[] items = new ListItem[10]
        5.times { items[it] = new ListItem("Item $it", 10) }

        expect:
        checklist.findItemInList('Item 2', items, 5)
    }

    void "Test getFirstUncompletedItem returns null when there are no uncompleted items"() {
        setup:
        Checklist checklist = new Checklist()

        expect:
        checklist.getFirstUncompletedItem() == null
    }

    void "Test getFirstUncompletedItem returns an item when not empty"() {
        setup:
        Checklist checklist = new Checklist()
        3.times { checklist.addItemToUnfinished(new ListItem("Item $it", 10))}

        expect:
        checklist.getFirstUncompletedItem().description == 'Item 0'
    }

    void "Test completeItem without calling statistics service"() {
        setup:
        Checklist checklist = new Checklist()
        3.times { checklist.addItemToUnfinished(new ListItem("Item $it", 10))}
        checklist.statisticsService = Mock(StatisticsService)

        when:
        checklist.completeItem(checklist.firstUncompletedItem)

        then:
        1 * checklist.statisticsService.reportStatistics(_,_) >> { a,b -> return a+b }
        checklist.completedItemsCount == 1
        checklist.unfinishedItemsCount == 2

    }


}
