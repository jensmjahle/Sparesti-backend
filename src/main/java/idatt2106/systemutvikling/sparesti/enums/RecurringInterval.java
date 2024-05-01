package idatt2106.systemutvikling.sparesti.enums;

import lombok.Getter;

/**
 * Enum for the recurring interval of a transaction.
 */
@Getter
public enum RecurringInterval {
  NONE(0),
  DAILY(24 * 60 * 60),
  WEEKLY(7 * 24 * 60 * 60),
  MONTHLY(30 * 24 * 60 * 60);

  private final int seconds;

  /**
   * Constructor for RecurringInterval.
   *
   * @param seconds the interval in seconds
   */
  RecurringInterval(int seconds) {
    this.seconds = seconds;
  }

}
