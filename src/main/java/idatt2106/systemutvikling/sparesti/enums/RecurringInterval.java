package idatt2106.systemutvikling.sparesti.enums;

import lombok.Getter;

@Getter
public enum RecurringInterval {
  NONE(0),
  DAILY(24 * 60 * 60),
  WEEKLY(7 * 24 * 60 * 60),
  MONTHLY(30 * 24 * 60 * 60);

  private final int seconds;

  RecurringInterval(int seconds) {
    this.seconds = seconds;
  }

}
