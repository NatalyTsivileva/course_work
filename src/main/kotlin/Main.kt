import kotlinx.coroutines.*

fun main() {
    runBlocking {
        val storage = Storage()
        val center = LogisticCenter(storage)
        println(storage)
        center.income()
        center.outcome()

        launch {
            delay(300000)
            center.stop()
            delay(2000)
            println("Game over")
        }

    }
}