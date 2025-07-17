import java.io.InputStream
import java.io.OutputStream
import java.net.ServerSocket
import kotlin.concurrent.thread

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 10000
    println("🌐 Relay server starting on port $port...")
    
    try {
        val serverSocket = ServerSocket(port)
        println("✅ Listening for connections...")

        while (true) {
            println("📱 Waiting for Phone A...")
            val phoneASocket = serverSocket.accept()
            println("✅ Phone A connected: ${phoneASocket.inetAddress.hostAddress}")

            println("📱 Waiting for Phone B...")
            val phoneBSocket = serverSocket.accept()
            println("✅ Phone B connected: ${phoneBSocket.inetAddress.hostAddress}")

            // Start bidirectional forwarding
            thread { forward("A ➝ B", phoneASocket.getInputStream(), phoneBSocket.getOutputStream()) }
            thread { forward("B ➝ A", phoneBSocket.getInputStream(), phoneASocket.getOutputStream()) }

            println("🔁 Forwarding started between A and B.")
        }

    } catch (e: Exception) {
        println("❌ Error starting server: ${e.message}")
        e.printStackTrace()
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
        println("⚠️ Connection lost [$tag]: ${e.message}")
    } finally {
        try {
            input.close()
            output.close()
        } catch (_: Exception) {
        }
    }
}
