# Gatling load test (SBT build)

## Description:
- Simulations - MainSimulation for load tests, Debug for debug
- LoadGenParams allows you set load type - "debug", "max", "stable", "constantUsers"
- db and csv feeder on your choice, sql file to create db for feeder (postgres)
- simulation.conf for test parameters
- GatlingRunner - tool for debug, you can use different simulations, default - Debug

## Build tool:
- sbt
- 
## Build, execute commands:

Start test from sbt
```chatinput
sbt "Gatling/testOnly simulation.MainSimulation"
```
Build executable package load-test-assembly-0.0.1.jar
```chatinput
sbt assembly
```
Start executable jar
```chatinput
java -jar load-test-assembly-0.0.1.jar -s simulation.MainSimulation -rf ./test_results
```
