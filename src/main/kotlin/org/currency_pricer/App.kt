package org.currency_pricer

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class App {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val appCtx = SpringApplication.run(org.currency_pricer.App::class.java, *args)

            val snapshotScheduler = SnapshotScheduler()
            snapshotScheduler.start()

            println("OK, CurrencyPricer started")
            readLine()
        }
    }
}
