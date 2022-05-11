package maxwainer.college.gui.common.gson;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.NANO_OF_SECOND;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;
import static java.time.temporal.ChronoField.YEAR;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public final class LocalDateTimeSerializer extends TypeAdapter<LocalDateTime> {

  // hard-coded pattern based on C# ISO format
  private static final DateTimeFormatter CS_PATTERN =
      new DateTimeFormatterBuilder()
          .appendValue(YEAR)
          .appendLiteral('-')
          .appendValue(MONTH_OF_YEAR)
          .appendLiteral('-')
          .appendValue(DAY_OF_MONTH)
          .appendLiteral('T')
          .appendValue(HOUR_OF_DAY, 2)
          .appendLiteral(':')
          .appendValue(MINUTE_OF_HOUR, 2)
          .optionalStart()
          .appendLiteral(':')
          .appendValue(SECOND_OF_MINUTE, 2)
          .optionalStart()
          .appendFraction(NANO_OF_SECOND, 0, 9, true)
          .toFormatter();

  @Override
  public void write(JsonWriter out, LocalDateTime value) throws IOException {
    out.value(value.format(CS_PATTERN));
  }

  @Override
  public LocalDateTime read(JsonReader in) throws IOException {
    return LocalDateTime.parse(in.nextString(), CS_PATTERN);
  }
}
