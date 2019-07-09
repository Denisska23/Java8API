package ua.com.foxminded.formula;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PrettyFormatterTest {

	private ClassLoader classloader;
	private PrettyFormatter formatter;

	@Before
	public void setUp() {
		classloader = Thread.currentThread().getContextClassLoader();
		formatter = new PrettyFormatter();
	}
	
	@Test
	public void givenCorrectList_whenFormatList_thenFormattedList() {
		List<String> expected = Arrays.asList("1.  Sebastian Vettel|FERRARI                  | 1:04.415",
											  "2.  Daniel Ricciardo|RED BULL RACING TAG HEUER| 1:12.013",
											  "3.  Lewis Hamilton  |MERCEDES                 | 1:12.460");
		
		LapsDataReader compiler = new LapsDataReader(new File(classloader.getResource("abbreviations.txt").getFile()),
													 new File(classloader.getResource("start.log").getFile()),
													 new File(classloader.getResource("end.log").getFile()));
		List<Racer> raceData = compiler.getRaceData();
		List<String> actual = formatter.format(raceData);

		assertThat(actual, equalTo(expected));
	}

	@Test(expected = IllegalArgumentException.class)
	public void givenNullList_whenFormatList_thenIllegalArgumentException() {
		List<String> actual = formatter.format(null);
	}

	@Test
	public void givenEmptyList_whenFormatList_thenIllegalArgumentException() {
		List<String> actual = formatter.format(new ArrayList<>());

		assertThat(actual, is(empty()));
	}
}
