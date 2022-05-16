package maxwainer.college.gui.common;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;
import static java.time.temporal.ChronoField.YEAR;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;
import maxwainer.college.gui.object.web.User;
import org.jetbrains.annotations.NotNull;

public final class MoreFormats {

  private MoreFormats() {
    throw new UnsupportedOperationException();
  }

  // simple format
  private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
      .appendValue(DAY_OF_MONTH)
      .appendLiteral('.')
      .appendValue(MONTH_OF_YEAR)
      .appendLiteral('.')
      .appendValue(YEAR)
      .appendLiteral(' ')
      .appendValue(HOUR_OF_DAY, 2)
      .appendLiteral(':')
      .appendValue(MINUTE_OF_HOUR, 2)
      .optionalStart()
      .appendLiteral(':')
      .appendValue(SECOND_OF_MINUTE, 2)
      .toFormatter();

  private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###");

  public static @NotNull String formatTime(final @NotNull TemporalAccessor temporal) {
    return DATE_TIME_FORMATTER.format(temporal);
  }

  public static @NotNull String formatUser(final @NotNull User user) {
    return user.firstName() + ' ' + user.secondName() + ' ' + user.patronymic() + " (Passport: "
        + user.passportId() + ") (Role: " + user.role() + ")";
  }

  public static @NotNull String formatNumber(final @NotNull Number number) {
    return DECIMAL_FORMAT.format(number);
  }

}
