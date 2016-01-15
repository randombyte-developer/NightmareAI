package de.randombyte.nightmare_ai.serializer

import java.lang.reflect.Type

import com.google.gson.{JsonDeserializationContext, JsonDeserializer, JsonElement}
import org.spongepowered.api.{CatalogType, GameRegistry}

class GsonCatalogTypeDeserializer[T <: CatalogType](registry: GameRegistry, classOfCatalogType: Class[T])
  extends JsonDeserializer[T] {
  override def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): T =
    registry.getType[T](classOfCatalogType, json.getAsString).get()
}
