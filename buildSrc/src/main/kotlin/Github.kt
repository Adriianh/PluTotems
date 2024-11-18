import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

fun getLatestRelease(repoOwner: String, repoName: String, overwrite: String? = null, fallback: String? = null): String =
    overwrite ?: try {
        val url = URL("https://api.github.com/repos/$repoOwner/$repoName/releases/latest")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.setRequestProperty("Accept", "application/vnd.github+json")

        if (connection.responseCode != 200) {
            error("Failed to retrieve the latest release")
        }
        val reader = BufferedReader(InputStreamReader(connection.inputStream))
        val response = reader.readText()
        reader.close()

        val index = response.indexOf("\"tag_name\":") + "\"tag_name\":".length + 1
        val tagName = response.substring(index, response.indexOf(",", index))
        tagName.replace("\"", "")
    } catch (e: Exception) {
        e.printStackTrace()
        fallback ?: throw e
    }