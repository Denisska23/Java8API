package ua.com.foxminded.formula;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class LapsDataReaderTest {

	private ClassLoader classloader;

	@Before
	public void setUp() {
		classloader = Thread.currentThread().getContextClassLoader();
	}

	@Test
	public void givenCorrectFiles_whenGetRacers_thenGetRacers() throws ParseException {
		List<Racer> expected = new ArrayList<>();
		expected.add(new Racer("Sebastian Vettel", "FERRARI", "SVF",
					 new LapTime(LocalDateTime.parse("2018-05-24T12:02:58.917", DateTimeFormatter.ISO_DATE_TIME),
							 	 LocalDateTime.parse("2018-05-24T12:04:03.332", DateTimeFormatter.ISO_DATE_TIME))));
		expected.add(new Racer("Daniel Ricciardo", "RED BULL RACING TAG HEUER", "DRR",
					 new LapTime(LocalDateTime.parse("2018-05-24T12:14:12.054", DateTimeFormatter.ISO_DATE_TIME),
							 	 LocalDateTime.parse("2018-05-24T12:15:24.067", DateTimeFormatter.ISO_DATE_TIME))));
		expected.add(new Racer("Lewis Hamilton", "MERCEDES", "LHM",
					 new LapTime(LocalDateTime.parse("2018-05-24T12:18:20.125", DateTimeFormatter.ISO_DATE_TIME),
							 LocalDateTime.parse("2018-05-24T12:19:32.585", DateTimeFormatter.ISO_DATE_TIME))));

		LapsDataReader compiler = new LapsDataReader(new File(classloader.getResource("abbreviations.txt").getFile()),
													 new File(classloader.getResource("start.log").getFile()), 
													 new File(classloader.getResource("end.log").getFile()));
		List<Racer> actual = compiler.getRaceData();

		assertThat(actual, equalTo(expected));
	}

	@Test(expected = IllegalArgumentException.class)
	public void givenEmptyFiles_whenGetRacers_thenIllegalArgumentException() {
		LapsDataReader compiler = new LapsDataReader(new File(classloader.getResource("empty.txt").getFile()),
													 new File(classloader.getResource("start.log").getFile()), 
													 new File(classloader.getResource("end.log").getFile()));
		List<Racer> actual = compiler.getRaceData();
	}

	@Test(expected = IllegalArgumentException.class)
	public void givenNoFiles_whenGetRacers_thenIllegalArgumentException() {
		LapsDataReader compiler = new LapsDataReader(new File(""),
													 new File(classloader.getResource("start.log").getFile()), 
													 new File(classloader.getResource("end.log").getFile()));
		List<Racer> actual = compiler.getRaceData();
	}
}