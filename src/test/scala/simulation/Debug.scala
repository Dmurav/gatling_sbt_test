package simulation

import io.gatling.core.Predef._
import io.gatling.core.structure.PopulationBuilder
import scripts.ComputerDatabaseActions.{browse, edit, printSession, search}
import tools.{Feeders, Protocols}

/**
 * Класс для отладки сценариев
 */
class Debug extends Simulation {

  val scnSearch: PopulationBuilder =
    scenario("Search")
      .repeat(1) {
        feed(Feeders.comSearchDBFeeder)
          .exec(printSession)
          .exec(search)
      }
      .inject(atOnceUsers(1))

  val scnBrowse: PopulationBuilder =
    scenario("Browse")
      .repeat(1) {
          //.exec(printSession)
          exec(browse)
      }
      .inject(atOnceUsers(1))

  val scnEdit: PopulationBuilder =
    scenario("Edit")
      .repeat(1) {
          //.exec(printSession)
          exec(edit)
      }
      .inject(atOnceUsers(1))

  setUp(
    scnSearch
    ,
    scnBrowse
    ,
    scnEdit
  ).protocols(Protocols.computerDatabaseHttpProtocol)

}
