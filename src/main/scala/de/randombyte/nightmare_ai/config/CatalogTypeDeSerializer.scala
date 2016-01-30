package de.randombyte.nightmare_ai.config

import java.lang.reflect.Type
import java.util.function.Supplier

import com.google.gson._
import org.spongepowered.api.{CatalogType, GameRegistry}

/**
  *(De)serializes given T types of CatalogType
  * @param registry GameRegistry for resolving the string representation of the types
  * @tparam T Sublcass of CatalogType to (de)serialize
  */
class CatalogTypeDeSerializer[T <: CatalogType](registry: GameRegistry, classOfT: Class[T])
  extends JsonDeserializer[T] with JsonSerializer[T] {
  override def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): T = {
    registry.getType[T](classOfT, json.getAsString).orElseThrow(new Supplier[JsonParseException] {
      override def get(): JsonParseException =
        new JsonParseException(s"Couldn't find ${json.getAsString} in GameRegistry")
    })
  }

  override def serialize(catalogType: T, typeOfSrc: Type, context: JsonSerializationContext): JsonElement =
    new JsonPrimitive(catalogType.getId)
}
