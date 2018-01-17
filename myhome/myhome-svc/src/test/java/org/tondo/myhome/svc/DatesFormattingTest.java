package org.tondo.myhome.svc;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

public class DatesFormattingTest {

	@Test
	public void testFormatYearMonth() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL yyyy");
		System.out.println(formatter.format(YearMonth.now()));
	}
}
