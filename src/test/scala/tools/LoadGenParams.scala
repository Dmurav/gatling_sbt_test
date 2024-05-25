package tools

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.protocol.HttpProtocolBuilder
import ru.tinkoff.gatling.config.SimulationConfig._

trait LoadGenParams extends Simulation {

  val testType: String = getStringParam("type")

  // Метод загрузки сценария
  def load(scenario: ScenarioBuilder, httpProtocol: HttpProtocolBuilder): SetUp = {
    testType match {
      case "max" => setUp(
        scenario.inject(
          nothingFor(4),
          incrementUsersPerSec((intensity / stagesNumber).toInt) // интенсивность на ступень
            .times(stagesNumber) // Количество ступеней
            .eachLevelLasting(stageDuration) // Длительность полки
            .separatedByRampsLasting(rampDuration) // Длительность разгона
            .startingFrom(0) // Начало нагрузки с
        ),
      ).protocols(httpProtocol)
    .maxDuration(testDuration+30)
      case "stable" =>
        setUp(
          scenario.inject(
            nothingFor(4), // пауза
            rampUsersPerSec(0) to intensity.toInt during rampDuration, //разгон
            constantUsersPerSec(intensity.toInt) during stageDuration //полка
          ),
        ).protocols(httpProtocol)
          .maxDuration(testDuration+30) //длительность теста = разгон + полка
      case "constantUsers" =>
        setUp(
          scenario.inject(
            rampConcurrentUsers(0).to(intensity.toInt).during(rampDuration),
            constantConcurrentUsers(intensity.toInt).during(stageDuration)
          ),
        ).protocols(httpProtocol)
          .maxDuration(testDuration+30)
      case "debug" =>
        setUp(
          scenario.inject(atOnceUsers(1))
        ).protocols(httpProtocol)
          .maxDuration(testDuration+10)
    }
  }


}
