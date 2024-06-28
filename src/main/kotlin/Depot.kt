import cargo.TypeCargo
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import transporter.*

@OptIn(ExperimentalCoroutinesApi::class)
object Depot {
    private val scopeDepot = CoroutineScope(Job() + Dispatchers.Default)
    private var check = 1
    val channelIn:ReceiveChannel<Transporter>
    private fun getTransporterForDownload(): Transporter {
        val a = listOf(TransporterBig(), TransporterMedium(), TransporterSmall()).random()
        val typeCargo = TypeCargo.values().random()
        do {
            val cargo = when (typeCargo) {
                TypeCargo.SmallCargo -> Storehouse.getSmallCargo()
                TypeCargo.MediumCargo -> Storehouse.getMediumCargo()
                TypeCargo.BulkyCargo -> Storehouse.getBulkyCargo()
                TypeCargo.Food -> Storehouse.getFood()
            }
            if (cargo.weight + a.currentCapacity <= a.capacity) {
                a.addCargoList(cargo)
            } else break
        } while (true)
        return a
    }

    fun getTransporterForUpload(): Transporter = listOf(TransporterMedium(), TransporterSmall()).random()

    fun stop() {
        check = 2
        scopeDepot.cancel()
        channelIn.cancel()
    }

    init {
        channelIn = scopeDepot.produce {
            while (check == 1) {
                send(getTransporterForDownload())
                delay(60000) //раз в минуту фура приезжает в хранилище
            }
        }
    }
}