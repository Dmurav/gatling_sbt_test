import scala.sys.process.Process

object GatlingRunner extends App {

    val simulationClass = "MainSimulation"

    Process(s"sbt \"Gatling/testOnly simulation.$simulationClass\"").!

}