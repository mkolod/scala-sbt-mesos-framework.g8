package $package$

import $package$.scheduler.$name;format="Camel"$Scheduler
import $package$.executor.$name;format="Camel"$Executor

import com.typesafe.config.{ Config, ConfigFactory }
import org.apache.mesos.{ MesosSchedulerDriver }
import org.apache.mesos.Protos.{ FrameworkInfo, FrameworkID }

object $name;format="Camel"$ extends Logging {

  val defaultSettings = ConfigFactory.parseString("""
    mesos {
      master = "localhost:5050"
    }
  """)

  val config = ConfigFactory.load.getConfig("$package$").withFallback(defaultSettings)

  val normalizedName = "$name;format="normalize"$"
  val failoverTimeout = 600000 // in milliseconds (10 minutes)
  val mesosMaster = config.getString("mesos.master")

  val frameworkId = FrameworkID.newBuilder.setValue(normalizedName)

  val frameworkInfo = FrameworkInfo.newBuilder()
    .setId(frameworkId)
    .setName(normalizedName)
    .setFailoverTimeout(failoverTimeout)
    .setUser("") // let Mesos assign the user
    .setCheckpoint(true)
    .build

  val scheduler = new $name;format="Camel"$Scheduler

  val driver = new MesosSchedulerDriver(
    scheduler,
    frameworkInfo,
    mesosMaster
  )

  // Execution entry point
  def main(args: Array[String]): Unit = {
    log.info("Hello from framework [{}]!", normalizedName)
    driver.run()
  }

}