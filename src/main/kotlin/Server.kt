import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Server {
    companion object {

        private val names = arrayListOf("Wily", "Felix", "Carlsbad", "Omar")
        private val adjs = arrayListOf("the gentle", "the un-gentle", "the overwrought", "the urbane")
        private const val port = 9090
        private var clients: ArrayList<ClientHandler> = arrayListOf()
        private val pool: ExecutorService = Executors.newFixedThreadPool(4)

        @JvmStatic
        fun main(args: Array<String>) {
            val listener = ServerSocket(port)

            while (true) {
                println("[SERVER] Waiting for client connection...")
                val client: Socket = listener.accept()
                println("[SERVER] Connected to client!")
                val clientThread = ClientHandler(client, clients)
                clients.add(clientThread)

                pool.execute(clientThread)
            }
        }

        fun getRandomName(): String {
            val name = names.random()
            val adj = adjs.random()
            return "$name $adj"
        }
    }

}