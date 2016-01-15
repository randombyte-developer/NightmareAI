package de.randombyte.nightmare_ai

import com.google.common.reflect.TypeToken
import ninja.leaping.configurate.ConfigurationNode
import ninja.leaping.configurate.objectmapping.ObjectMappingException
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer
import org.spongepowered.api.{CatalogType, GameRegistry}

/**
  * Provides an an already implemented TypeSerializer for type T. It uses the id of the CatalogType to represent the
  * type in a String format.
  */
class CatalogTypeSerializerFactory(registry: GameRegistry) {

  /**
    * Returns a tuple with the TypeToken and the already implemented TypeSerializer of type T
    * @tparam T The type which extends CatalogType to create the TypeSerializer for
    * @return A tuple with the TypeToken and the already implemented TypeSerializer of type T
    */
  def createSerializer[T <: CatalogType](classOfT: Class[T]): (TypeToken[T], TypeSerializer[T]) =
    (TypeToken.of(classOfT), new TypeSerializer[T] {
      override def serialize(`type`: TypeToken[_], obj: T, value: ConfigurationNode): Unit = {
        value.setValue(obj.getId)
      }
      override def deserialize(`type`: TypeToken[_], value: ConfigurationNode): T = {
        val plainString = value.getString
        if (plainString == null)
          throw new ObjectMappingException(s"No value present in node $value")
        val optType = registry.getType[T](classOfT, plainString)
        if (!optType.isPresent)
          throw new ObjectMappingException(s"Invalid ID $plainString string provided for key ${value.getKey}")
        optType.get
      }
    })
}
