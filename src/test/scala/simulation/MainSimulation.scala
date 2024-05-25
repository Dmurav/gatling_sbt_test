package simulation

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import scripts.ComputerDatabaseActions.{browse, edit, printSession, search}
import tools.{Feeders, LoadGenParams, Protocols}

/**
 * Основная симуляция
 */
class MainSimulation extends LoadGenParams {

  val users: ScenarioBuilder = scenario("Users").exec(search, browse)
  val admins: ScenarioBuilder = scenario("Admins").exec(search, browse, edit)

  lazy val scn: ScenarioBuilder = scenario("computerDatabase")
    .randomSwitch(possibilities =
      75.0 -> feed(Feeders.comSearchDBFeeder).exec(users),
      25.0 -> feed(Feeders.comSearchDBFeeder).exec(admins))

  load(scn, Protocols.computerDatabaseHttpProtocol)

}
