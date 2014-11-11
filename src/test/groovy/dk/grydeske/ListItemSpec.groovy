package dk.grydeske

import spock.lang.Specification
import spock.lang.Unroll

import javax.rmi.CORBA.Util

class ListItemSpec extends Specification{

	void "Test adding minutes to an item"() {
		given:
		ListItem item = new ListItem('Dummy description', 0);

		expect:
		item.getTotalMinutesUsed() == 0;
		item.getNumberOfTimesHandled() == 0;

		when:
		item.addToMinuttesUsed(30);

		then:
		item.getTotalMinutesUsed() == 30
		item.getNumberOfTimesHandled() == 1;
	}

    void "Test the completeItem method"() {
        given:
        ListItem item = new ListItem('Dummy description', 0);

        expect:
        !item.dateCompleted

        when:
        item.completeItem()

        then:
        (item.dateCompleted.time - new Date().time ).abs() < 1000
    }

	@Unroll
	void "Test getTotalPrice, where items with minutes #minutes and price #price is #total"() {
		given:
		ListItem listItem = new ListItem("Dummy description", price)
		minutes.each {
			listItem.addToMinuttesUsed(it)
		}

		expect:
		listItem.totalPrice == total

		where:
		minutes         | price     || total
		[0]             | 0         || 0
		[0]             | 10        || 0
		[1]             | 1         || 1
		[1,2,3]         | 0         || 0
		[1,2,3]         | 1         || 6
		[1,2,3]         | 2         || 12
		[1,2,3]         | 1.5       || 9
		[1,2,3]         | 1.75      || 10.5

	}

}
