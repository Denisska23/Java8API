package ua.com.foxminded.formula;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LapsDataReader {

	private static final String DATE_TIME_PATTERN = "yyyy-MM-dd_HH:mm:ss.SSS";
	private List<Racer> racers;
	private Map<String, LocalDateTime> startsByAbbreviations;
	private Map<String, LocalDateTime> endsByAbbreviations;
	private File abbreviations;
	private File starts;
	private File ends;

	public LapsDataReader(File abbreviations, File starts, File ends) {
		racers = new ArrayList<>();
		this.abbreviations = abbreviations;
		this.starts = starts;
		this.ends = ends;
	}

	public List<Racer> getRaceData() {
		checkFiles();
		processFiles();
		sortByTime();
		return racers;
	}

	private void checkFiles() {
		checkFilesForExistence();
		checkFilesForEmptiness();
	}

	private void checkFilesForExistence() {
		if (!abbreviations.exists()) {
			throw new IllegalArgumentException("Abbreviations file not found");
		}
		if (!starts.exists()) {
			throw new IllegalArgumentException("Starts file not found");
		}
		if (!ends.exists()) {
			throw new IllegalArgumentException("Ends file not found");
		}
	}

	private void checkFilesForEmptiness() {
		if (abbreviations.length() == 0) {
			throw new IllegalArgumentException("Abbreviations file is empty");
		}
		if (starts.length() == 0) {
			throw new IllegalArgumentException("Starts file is empty");
		}
		if (ends.length() == 0) {
			throw new IllegalArgumentException("Ends file is empty");
		}
	}

	private void processFiles() {
		processStarts();
		processEnds();
		processAbbreviations();
	}
	
	private void processStarts() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
		try (Stream<String> stream = Files.lines(Paths.get(starts.getAbsolutePath()))) {
			startsByAbbreviations = stream
					.collect(Collectors.toMap(line -> line.substring(0, 3), 
											  line -> LocalDateTime.parse(line.substring(3), formatter)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void processEnds() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
		try (Stream<String> stream = Files.lines(Paths.get(ends.getAbsolutePath()))) {
			endsByAbbreviations = stream
					.collect(Collectors.toMap(line -> line.substring(0, 3), 
											  line -> LocalDateTime.parse(line.substring(3), formatter)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void processAbbreviations() {
		try (Stream<String> stream = Files.lines(Paths.get(abbreviations.getAbsolutePath()))) {
			racers = stream.map(this::getRacer).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Racer getRacer(String line) {
		String[] racerData = line.split("_");

		String abbreviation = racerData[0];
		String name = racerData[1];
		String team = racerData[2];
		LapTime time = getLapTime(abbreviation);

		return new Racer(name, team, abbreviation, time);
	}

	private LapTime getLapTime(String abbreviation) {
		LocalDateTime start = startsByAbbreviations.get(abbreviation);
		LocalDateTime end = endsByAbbreviations.get(abbreviation);

		return new LapTime(start, end);
	}
	
	private void sortByTime() {
		racers.sort(Comparator.comparing(racer -> racer.getLapTime().getDuration()));
	}
}
