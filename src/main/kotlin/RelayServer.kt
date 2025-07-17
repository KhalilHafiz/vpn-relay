import java.io.InputStream
import java.io.OutputStream
import java.net.ServerSocket
import kotlin.concurrent.thread

val PORT = System.getenv("PORT")?.toInt() ?: 9090

fun main() {
    println("Relay server starting on port $PORT...")
    val serverSocket = ServerSocket(PORT)

    while (true) {
        println("Waiting for Phone A...")
        val phoneASocket = serverSocket.accept()
        println("Phone A connected from ${phoneASocket.inetAddress.hostAddress}")

        println("Waiting for Phone B...")
        val phoneBSocket = serverSocket.accept()
        println("Phone B connected from ${phoneBSocket.inetAddress.hostAddress}")

        thread { forward("A ➝ B", phoneASocket.getInputStream(), phoneBSocket.getOutputStream()) }
        thread { forward("B ➝ A", phoneBSocket.getInputStream(), phoneASocket.getOutputStream()) }

        println("Forwarding started between A and B.")
    }
}

fun forward(tag: String, input: InputStream, output: OutputStream) {
    try {
        val buffer = ByteArray(4096)
        while (true) {
            val bytesRead = input.read(buffer)
            if (bytesRead == -1) break
            output.write(buffer, 0, bytesRead)
            output.flush()
        }
    } catch (e: Exception) {
        println("Connection lost [$tag]: ${e.message}")
    } finally {
        try {
            input.close()
            output.close()
        } catch (_: Exception) {}
    }
}
