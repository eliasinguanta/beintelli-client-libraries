package beintelliPlatformSdk.javaSdk;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;


/**
 * Testsuit.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("beintelliPlatformSdk/javaSdk")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
public class RunCucumberTest {
}

/**
 * Contains the parse function from date in String fromat like 2022-01-01 to LocalDateTime.
 */
class Parser {

  /**
   * parse from String like '2022-01-01' to LocalDateTime
   *
   * @param date as String like 2022-01-01
   * @return LocalDateTime
   */
  public static LocalDateTime parse(String date) {
    return LocalDate.parse(date)
    .atStartOfDay()
    .atZone(ZoneId.of("Europe/Berlin"))
    .toOffsetDateTime()
    .toLocalDateTime();
  }
}