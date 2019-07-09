package ua.com.foxminded.formula;

import java.time.Duration;
import java.time.LocalDateTime;

public class LapTime {

	private LocalDateTime start;
	private LocalDateTime end;
	private Duration duration;

	public LapTime(LocalDateTime start, LocalDateTime end) {
		if (start == null) {
			throw new IllegalArgumentException("Start time is null");
		}
		if (end == null) {
			throw new IllegalArgumentException("End time is null");
		}
		this.start = start;
		this.end = end;
		this.duration = Duration.between(start, end);
	}

	public Duration getDuration() {
		return duration;
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
		LapTime other = (LapTime) obj;
		if (duration == null) {
			if (other.duration != null) {
				return false;
			}
		} else if (!duration.equals(other.duration)) {
			return false;
		}
		if (end == null) {
			if (other.end != null) {
				return false;
			}
		} else if (!end.equals(other.end)) {
			return false;
		}
		if (start == null) {
			if (other.start != null) {
				return false;
			}
		} else if (!start.equals(other.start)) {
			return false;
		}
		return true;
	}

}