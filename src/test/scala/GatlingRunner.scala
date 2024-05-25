import scala.sys.process.Process

object GatlingRunner extends App {

    val simulationClass = "Debug"

    Process(s"sbt \"Gatling/testOnly simulation.$simulationClass\"").!

}