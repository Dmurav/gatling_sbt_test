package scripts

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

import java.util.concurrent.ThreadLocalRandom

/**
 * Основные действия по работе с базой данных, составные части сценариев
 */
object ComputerDatabaseActions {

  // метод для отладки, выводит данные виртуанного пользователя в сессии
  val printSession: ChainBuilder =
    exec { session =>
      println(session)
      session
    }

  val search: ChainBuilder = {
    exec(
    http("home_page")
      .get("/"),
    pause(1),
    http("find_pc")
      .get("/computers?f=#{searchcriterion}")
      .check(
        css("a:contains('#{searchcomputername}')", "href").saveAs("computerUrl")
      ),
    pause(1),
    http("select_pc")
      .get("#{computerUrl}")
      .check(status.is(200)),
    pause(1)
    )
  }


  val browse: ChainBuilder =
    repeat(4, "i")(
      http("browse_page").get("/computers?p=#{i}"),
      pause(1)
    )


  val edit: ChainBuilder =
    tryMax(2)(
      http("add_pc_form").get("/computers/new"),
      pause(1),
      http("add_pc_submit")
        .post("/computers")
        .formParam("name", "Beautiful Computer")
        .formParam("introduced", "2012-05-30")
        .formParam("discontinued", "")
        .formParam("company", "37")
        .check(status.is(200))
    )
      .exitHereIfFailed

}
