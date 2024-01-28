package id.technice.rispekabangku.networkUtils

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class EmptyStringDeserializer : JsonDeserializer<String?> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): String? {
        return when (json?.isJsonObject) {
            true -> {
                // Empty object case, return null
                null
            }
            else -> {
                // Default deserialization for non-empty strings
                json?.asString
            }
        }
    }
}