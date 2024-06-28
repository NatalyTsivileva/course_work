import cargo.TypeCargo
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import transporter.Transporter

class LogisticCenter(private val storage: Storage) {
    private val channelOut = Channel<Transporter>()
    private var check = 1
    private val scopeStorage = CoroutineScope(Job() + Dispatchers.Default)

    private suspend fun download(transporter: Transporter) {
        val cargoList = transporter.cargoList
        for (cargo in cargoList) {
            yield()
            storage.addElement(cargo)
        }
    }

    private suspend fun upload(transporter: Transporter, typeCargo: TypeCargo) {
        do {
            yield()
            val cargo = storage.getElement(typeCargo)
            if (cargo.weight + transporter.currentCapacity <= transporter.capacity) {
                transporter.addCargoList(cargo)
            } else {
                storage.addElement(cargo)
                break
            }
        } while (true)
    }

    fun income() {
        repeat(storage.countPortsForDownload) {
            launchProcessor(
                it,
                Depot.channelIn
            )
        }
    }

    fun outcome() {
        for (i in 1..storage.countPortsForUpload) sendProcessor(i, channelOut)
    }

    fun stop() {
        channelOut.cancel()
        check = 2
        Depot.stop()
        scopeStorage.cancel()
    }

    private fun launchProcessor(id: Int, channel: ReceiveChannel<Transporter>) = scopeStorage.launch {
        for (msg in channel) {
            println(
                "${ColorFont.RED}Порт загрузки #${id + 1} занят транспортом $msg\n" +
                        "тип груза ${msg.cargoList[0].typeCargo}\n" +
                        "количество груза ${msg.getCargoListSize()}${ColorFont.WHITE}\n"
            )
            download(msg)
            println("${ColorFont.RED}Порт загрузки #${id + 1} - загрузка завершена${ColorFont.WHITE}")
            println(storage)
        }
    }

    private fun sendProcessor(i: Int, channel: SendChannel<Transporter>) = scopeStorage.launch {
        while (check == 1) {
            val msg = Depot.getTransporterForUpload()
            val typeCargo = TypeCargo.values().random()
            println(
                "${ColorFont.BLUE}Порт отгрузки #$i занят транспортом $msg\n" +
                        "тип груза $typeCargo\n" +
                        "вместимость транспорта ${msg.capacity}${ColorFont.WHITE}\n"
            )
            upload(msg, typeCargo)
            channel.send(msg)
            println("${ColorFont.BLUE}Порт отгрузки #$i - отгрузка завершена${ColorFont.WHITE}")
            println(storage)
        }
    }
}