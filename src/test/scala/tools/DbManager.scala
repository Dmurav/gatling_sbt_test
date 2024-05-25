package tools

import io.gatling.core.feeder.FeederBuilderBase
import io.gatling.jdbc.Predef._
import io.gatling.core.Predef._
import ru.tinkoff.gatling.config.SimulationConfig.getStringParam

/**
 * Объект для управления подключениями к базам с тестовыми данными
 */
object DbManager {

  private final val infraPgUrl: String = getStringParam("pgUrl")
  private final val infraPgUser: String = getStringParam("pgUser")
  private final val infraPgPass: String = getStringParam("pgPass")

  // инфраструктурынй postgres
  def selectPostgresSQL(sql: String): FeederBuilderBase[Any] = {
    jdbcFeeder(infraPgUrl, infraPgUser, infraPgPass, sql)
  }

}
