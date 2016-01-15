package de.randombyte.nightmare_ai.serializer

import java.lang.reflect.Type

import com.google.gson.{JsonPrimitive, JsonElement, JsonSerializationContext, JsonSerializer}
import org.spongepowered.api.CatalogType

class GsonCatalogTypeSerializer extends JsonSerializer[CatalogType] {
  override def serialize(catalogType: CatalogType, typeOfSrc: Type, context: JsonSerializationContext): JsonElement =
    new JsonPrimitive(catalogType.getName)
}
