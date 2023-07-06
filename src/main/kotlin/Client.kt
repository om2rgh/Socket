import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import kotlin.system.exitProcess

class Client {
    companion object {
        private const val port = 9090
        private const val server_port = "127.0.0.1"

        @JvmStatic
        fun main(args: Array<String>) {
            val socket = Socket(server_port, port)
            val serverConnection = ServerConnection(socket)
            val keyboard = BufferedReader(InputStreamReader(System.`in`))
            val output = PrintWriter(socket.getOutputStream(), true)
            Thread(serverConnection).start()

            while (true) {
                println(">")
                val command = keyboard.readLine()
                if (command.equals("quit")) break
                output.println(command)
            }
            socket.close()
            exitProcess(0)
        }
    }
}