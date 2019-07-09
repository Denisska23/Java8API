package ua.com.foxminded.formula;

import java.io.File;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();

		File abbreviations = new File(classloader.getResource("abbreviations.txt").getFile());
		File starts = new File(classloader.getResource("start.log").getFile());
		File ends = new File(classloader.getResource("end.log").getFile());

		LapsDataReader compiler = new LapsDataReader(abbreviations, starts, ends);
		List<Racer> raceData = compiler.getRaceData();

		PrettyFormatter formatter = new PrettyFormatter();
		List<String> result = formatter.format(raceData);

		result.forEach(System.out::println);
	}
}