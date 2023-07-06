import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.Socket

class ServerConnection(socket: Socket) : Runnable {

    private var socket: Socket
    private var input: BufferedReader

    init {
        this.socket = socket
        this.input = BufferedReader(InputStreamReader(socket.getInputStream()))
    }

    override fun run() {

        try {
            while (true) {
                val serverResponse = input.readLine() ?: break
                println("Server says : $serverResponse")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                input.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}



