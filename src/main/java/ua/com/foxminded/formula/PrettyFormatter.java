package ua.com.foxminded.formula;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

public class PrettyFormatter {

	private static final char SPACE = ' ';
	private static final char VERTICAL_LINE = '|';
	private static final char DASH = '-';
	private static final char DOT = '.';
	private static final int DOT_LENGTH = 1;
	private static final int SPACE_LENGTH = 1;
	private static final int VERTICAL_LINE_LENGTH = 1;
	private static final String DURATION_FORMAT = "mm:ss.SSS";
	private List<Racer> racers;

	public List<String> format(List<Racer> raceData) {
		if (raceData == null) {
			throw new IllegalArgumentException("List with race data is null");
		}
		this.racers = raceData;
		List<String> result = new ArrayList<>();
		addLapsData(result, 15);
		return result;
	}

	private void addLapsData(List<String> result, int eliminationDelimiter) {
		for (int i = 0; i < racers.size(); i++) {
			if (i == eliminationDelimiter) {
				result.add(getDashesLine());
			}
			result.add(getRacerLine(racers.get(i), i));
		}
	}

	private String getDashesLine() {
		int positionPartLength = ((int) (Math.log10(racers.size()))) + DOT_LENGTH + SPACE_LENGTH;
		int racerPartLength = getMaxRacerNameLength() + VERTICAL_LINE_LENGTH;
		int teamPartLength = getMaxTeamNameLength() + VERTICAL_LINE_LENGTH;
		int durationPartLength = DURATION_FORMAT.length();

		int dashesLineLength = positionPartLength + racerPartLength + teamPartLength + durationPartLength;
		String dash = "-";
		return addSymbolsToString(dash, dashesLineLength, DASH);
	}

	private String getRacerLine(Racer racer, int position) {
		StringBuilder racerData = new StringBuilder();
		racerData.append(getPositionPart(position));
		racerData.append(getRacerPart(racer));
		racerData.append(getTeamPart(racer));
		racerData.append(getDurationPart(racer));
		return racerData.toString();
	}

	private String getPositionPart(int position) {
		StringBuilder positionPart = new StringBuilder();
		positionPart.append(position + 1);
		positionPart.append(DOT);
		if (((position + 1) / 10) > 0) {
			positionPart.append(SPACE);
		} else {
			positionPart.append(Character.toString(SPACE) + Character.toString(SPACE));
		}
		return positionPart.toString();
	}

	private String getRacerPart(Racer racer) {
		StringBuilder racerPart = new StringBuilder();
		racerPart.append(addSymbolsToString(racer.getName(), getMaxRacerNameLength(), SPACE));
		racerPart.append(VERTICAL_LINE);
		return racerPart.toString();
	}

	private String getTeamPart(Racer racer) {
		StringBuilder teamPart = new StringBuilder();
		teamPart.append(addSymbolsToString(racer.getTeam(), getMaxTeamNameLength(), SPACE));
		teamPart.append(VERTICAL_LINE);
		return teamPart.toString();
	}

	private String getDurationPart(Racer racer) {
		return String.format("%2d:%02d.%03d", 
				racer.getLapTime().getDuration().toMinutes(),
				racer.getLapTime().getDuration().toSecondsPart(), 
				racer.getLapTime().getDuration().toMillisPart());
	}
	
	private int getMaxRacerNameLength() {
		OptionalInt max = racers.stream()
							.map(Racer::getName)
							.mapToInt(String::length)
							.max(); 
		return max.orElse(0);
	}
	
	private int getMaxTeamNameLength() {
		OptionalInt max = racers.stream()
							.map(Racer::getTeam)
							.mapToInt(String::length)
							.max(); 
		return max.orElse(0);
	}

	private String addSymbolsToString(String string, int totalLength, char symbol) {
		StringBuilder result = new StringBuilder(string);
		int spaces = totalLength - string.length();
		for (int i = 0; i < spaces; i++) {
			result.append(symbol);
		}
		return result.toString();
	}
}