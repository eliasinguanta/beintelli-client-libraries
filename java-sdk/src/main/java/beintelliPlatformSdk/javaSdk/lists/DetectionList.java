package beintelliPlatformSdk.javaSdk.lists;

import beintelliPlatformSdk.javaSdk.apis.DataApi;
import beintelliPlatformSdk.javaSdk.utils.Access;
import beintelliPlatformSdk.javaSdk.utils.DetectionData;
import beintelliPlatformSdk.javaSdk.utils.attributes.Attribute;
import beintelliPlatformSdk.javaSdk.utils.attributes.NumberAttribute;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/**
 * MeasureList for the special type DetectionData. Used internally to guarantee type safety.
 *
 * @hidden
 * @see MeasureList
 * @see DetectionData
 * @author Elias Inguanta
 * @version 0.1.0
 * @since 0.1.0
 */
class DetectionList extends MeasureList<DetectionData> {
  public DetectionList(DataApi api, Logger logger) {
    super(api, logger);
  }

  public DetectionList(Access access, Logger logger) {
    super(access, logger);
  }

  @Override
  protected MeasureList<DetectionData> getEmptyList() {
    return new DetectionList(api, logger);
  }

  @Override
  protected LocalDateTime getMaxTime() {
    logger.finer("search latest tmpstmpForAdding in current list");
    if (isEmpty()) {
      logger.finest("no tmpstmpForAdding because no data in list");
      return null;
    }
    LocalDateTime max = getFirst().tmpstmpForAdding();

    for (DetectionData e : this) {
      LocalDateTime current = e.tmpstmpForAdding();
      if (max.isBefore(current)) {
        max = current;
      }
    }

    logger.finest("latest tmpstmpForAdding = " + max);
    return max;
  }

  @Override
  protected LocalDateTime getMinTime() {
    logger.finer("search earliest tmpstmpForAdding in current list");
    if (isEmpty()) {
      logger.finest("no tmpstmpForAdding because no data in list");
      return null;
    }
    LocalDateTime min = getFirst().tmpstmpForAdding();

    for (DetectionData e : this) {
      LocalDateTime current = e.tmpstmpForAdding();
      if (min.isAfter(current)) {
        min = current;
      }
    }

    logger.finest("earliest tmpstmpForAdding = " + min);
    return min;
  }

  @Override
  public CompletableFuture<MeasureList<DetectionData>> filtered(Attribute attribute, Object value) {
    logger.fine("entering filtered");
    logger.finer("attribute = " + attribute + ", value = " + value);
    CompletableFuture<List<LocalDateTime>> timesFuture = getfilteredTimes(attribute, value);

    logger.fine("filter current list for measurements with matching times");
    CompletableFuture<MeasureList<DetectionData>> resultFuture = timesFuture.thenApplyAsync(
        (List<LocalDateTime> list) -> {
        DetectionList result = new DetectionList(api, logger);
        for (DetectionData data : this) {
          for (LocalDateTime time : list) {
            if (data.tmpstmpForAdding().equals(time)) {
              logger.finest(data + " meets criteria");
              result.add(data);
            } else {
              logger.finest(data + " does not meet criteria");
            }
          }
        }
        return result;
      });
    return resultFuture;
  }

  @Override
  public CompletableFuture<MeasureList<DetectionData>> filtered(
      NumberAttribute attribute, Number from, Number to) {
    logger.fine("entering filtered");
    logger.finer("attribute = " + attribute + ", from = " + from + ", to = " + to);
    CompletableFuture<List<LocalDateTime>> timesFuture = getfilteredTimes(attribute, from, to);

    logger.fine("filter current list for measurements with matching times");
    CompletableFuture<MeasureList<DetectionData>> resultFuture = timesFuture.thenApplyAsync(
        (List<LocalDateTime> list) -> {
        DetectionList result = new DetectionList(api, logger);
        for (DetectionData data : this) {
          for (LocalDateTime time : list) {
            if (data.tmpstmpForAdding().equals(time)) {
              logger.finest(data + " meets criteria ");
              result.add(data);
            } else {
              logger.finest(data + " does not meet criteria");
            }
          }
        }
        return result;
      }
    );
    return resultFuture;    
  }
}
