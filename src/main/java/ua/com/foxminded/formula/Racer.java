package ua.com.foxminded.formula;

public class Racer {

	private String name;
	private String team;
	private String abbreviation;
	private LapTime lapTime;

	public Racer(String name, String team, String abbreviation, LapTime lapTime) {
		this.name = name;
		this.team = team;
		this.abbreviation = abbreviation;
		this.lapTime = lapTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public LapTime getLapTime() {
		return lapTime;
	}

	public void setLapTime(LapTime lapTime) {
		this.lapTime = lapTime;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Racer other = (Racer) obj;
		if (abbreviation == null) {
			if (other.abbreviation != null) {
				return false;
			}
		} else if (!abbreviation.equals(other.abbreviation)) {
			return false;
		}
		if (lapTime == null) {
			if (other.lapTime != null) {
				return false;
			}
		} else if (!lapTime.equals(other.lapTime)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (team == null) {
			if (other.team != null) {
				return false;
			}
		} else if (!team.equals(other.team)) {
			return false;
		}
		return true;
	}
}