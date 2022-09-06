package pro.siberian.dynamicsql

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DynamicSqlApplication

fun main(args: Array<String>) {
    runApplication<DynamicSqlApplication>(*args)
}
