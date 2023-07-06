import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import javax.swing.JOptionPane

class ClientHandler(clientSocket: Socket, clients: ArrayList<ClientHandler>) : Runnable {

    private var socket: Socket
    private var input: BufferedReader
    private var output: PrintWriter
    private var clients: ArrayList<ClientHandler>
    private lateinit var randomName :String


    init {
        this.socket = clientSocket
        this.clients = clients
        this.input = BufferedReader(InputStreamReader(socket.getInputStream()))
        this.output = PrintWriter(socket.getOutputStream(), true)
    }

    override fun run() {
        try {
            while (true) {
                val request = input.readLine() ?: continue

                if (request.contains("name")) {
                    randomName = Server.getRandomName()
                    output.println(randomName)
                } else if (request.startsWith("say")) {
                    val firstSpace = request.indexOf(" ")
                    if (firstSpace != -1) {
                        outToAll(request.substring(firstSpace + 1))
                    }
                } else {
                    output.println(" Type 'tell me a name' to get a random name ")
                }
            }
        } catch (e: IOException) {
            System.err.println("IO exception in client handler")
            /*System.err.println(e.stackTrace.toString())
            e.printStackTrace()*/
            JOptionPane.showMessageDialog(null,e.message)
        } finally {
            try {
                shutdown()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun outToAll(msg: String) {
        for (aClient in clients) {
            val message = "$randomName ==> $msg"
            aClient.output.println(message)
        }
    }

    private fun shutdown() {
        socket.close()
        input.close()
        output.close()
    }
}