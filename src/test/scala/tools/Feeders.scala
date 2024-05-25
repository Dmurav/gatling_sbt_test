package tools

import io.gatling.core.Predef._
import io.gatling.core.feeder.{BatchableFeederBuilder, FeederBuilderBase}

object Feeders {

  private final val sql:String = "select * from parameters"

  val comSearchFeeder: BatchableFeederBuilder[String] = csv("search.csv").random

  val comSearchDBFeeder: FeederBuilderBase[Any] = DbManager.selectPostgresSQL(sql).circular

}
