import sbt.Keys.libraryDependencies
import sbtassembly.AssemblyKeys.assembly

import scala.collection.Seq

enablePlugins(GatlingPlugin)
enablePlugins(AssemblyPlugin) // плагин для сборки

// секция для создания jar  // sbt assembly
lazy val root = (project in file("."))
  .settings(
    inThisBuild(
      List(
        organization := "dima.test.example",
        scalaVersion := "2.13.13",
        version := "0.0.1"
      )),
    name := "load-test",
    assembly / mainClass := Some("io.gatling.app.Gatling"),
    assembly / fullClasspath := (assembly / fullClasspath).value ++ (Test / fullClasspath).value,
    assembly / assemblyOutputPath := file(s"./${(assembly/assemblyJarName).value}"),
    ThisBuild / assemblyMergeStrategy := {
      case x if Assembly.isConfigFile(x) =>
        MergeStrategy.concat
      case PathList(ps@_*) if Assembly.isReadme(ps.last) || Assembly.isLicenseFile(ps.last) =>
        MergeStrategy.rename
      case PathList("META-INF", xs@_*) =>
        xs map {
          _.toLowerCase
        } match {
          case "manifest.mf" :: Nil | "index.list" :: Nil | "dependencies" :: Nil =>
            MergeStrategy.discard
          case ps@x :: xs if ps.last.endsWith(".sf") || ps.last.endsWith(".dsa") =>
            MergeStrategy.discard
          case "plexus" :: xs =>
            MergeStrategy.discard
          case "services" :: xs =>
            MergeStrategy.filterDistinctLines
          case "spring.schemas" :: Nil | "spring.handlers" :: Nil =>
            MergeStrategy.filterDistinctLines
          case _ => MergeStrategy.first
        }
      case PathList(ps@_*) if ps.last endsWith ".html" => MergeStrategy.last
      case "application.conf" => MergeStrategy.discard
      case "unwanted.txt" => MergeStrategy.discard
      case "gatling.conf" => MergeStrategy.concat
      case "gatling-akka-defaults.conf" => MergeStrategy.concat
      case "gatling-defaults.conf" => MergeStrategy.concat
      case "reference.conf" => MergeStrategy.concat
      case _ => MergeStrategy.first
    },
    Gatling / javaOptions := overrideDefaultJavaOptions("-Xms1024m", "-Xmx6144m", "-XX:-HeapDumpOnOutOfMemoryError", "-XX:HeapDumpPath=/dev/null", "-Dfile.encoding=UTF-8"),
    scalacOptions := Seq(
      "-encoding",
      "UTF-8",
      "-target:jvm-1.8",
      "-deprecation",
      "-feature",
      "-unchecked",
      "-language:implicitConversions",
      "-language:postfixOps"
    ),
      libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "3.11.2" % "test",
      libraryDependencies += "io.gatling"            % "gatling-test-framework"    % "3.11.2" % "test",
      libraryDependencies += "io.gatling"            % "gatling-recorder"          % "3.11.2" % "test",
      libraryDependencies += "ru.tinkoff"            %% "gatling-picatinny"        % "0.13.0" % "test",
      libraryDependencies += "org.postgresql"         % "postgresql"               % "42.3.1" % "test"
  )

// секция для запуска тестов из IntelliJ Idea // sbt Gatling/testOnly

name := "demo"

scalaVersion := "2.13.13"
scalacOptions ++= Seq("encoding", "utf-8")
libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "3.11.2" % "test"
libraryDependencies += "io.gatling"            % "gatling-test-framework"    % "3.11.2" % "test"
libraryDependencies += "io.gatling"            % "gatling-recorder"          % "3.11.2" % "test"
libraryDependencies += "ru.tinkoff"            %% "gatling-picatinny"        % "0.13.0" % "test"
libraryDependencies += "org.postgresql"         % "postgresql"               % "42.3.1" % "test"





