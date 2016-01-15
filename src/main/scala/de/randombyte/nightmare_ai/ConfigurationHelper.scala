package de.randombyte.nightmare_ai

import java.io.File

import com.google.common.reflect.TypeToken
import ninja.leaping.configurate.ConfigurationOptions
import ninja.leaping.configurate.hocon.HoconConfigurationLoader
import ninja.leaping.configurate.objectmapping.ObjectMapper
import ninja.leaping.configurate.objectmapping.serialize.{TypeSerializer, TypeSerializerCollection, TypeSerializers}
import org.spongepowered.api.CatalogType
import org.spongepowered.api.entity.EntityType
import org.spongepowered.api.item.ItemType

object ConfigurationHelper {
  //Classes that are used in the config and have to be registered manually for (de)serialization
  val catalogTypeSerializerClasses: List[Class[_ <: CatalogType]] = List(classOf[EntityType], classOf[ItemType])
}

/**
  * Helps with managing the configuration
  */
class ConfigurationHelper(configFile: File, serializerFactory: CatalogTypeSerializerFactory) {

  implicit class TupleUnpackingTypeSerializerCollection(collection: TypeSerializerCollection) {
    def registerType[T](params: (TypeToken[T], TypeSerializer[T])): Unit = {
      collection.registerType(params._1, params._2)
      System.out.println(s"Registered ${params._1.getRawType.getName}")
    }
    def addCatalogTypes(serializerFactory: CatalogTypeSerializerFactory,
                        catalogClasses: List[Class[_ <: CatalogType]]): TypeSerializerCollection = {
      for (catalogClass <- catalogClasses) registerType(serializerFactory.createSerializer(catalogClass))
      for (catalogClass <- catalogClasses) {
        System.out.println(s"Found ${collection.get(TypeToken.of(catalogClass))} for ${catalogClass.getName}")
      }
      collection
    }
  }

  val configMapper = ObjectMapper.forClass(classOf[NightmareAiConfig]).bindToNew()
    .asInstanceOf[ObjectMapper[NightmareAiConfig]#BoundInstance]

  val configurationLoader = HoconConfigurationLoader
    .builder()
    .setFile(configFile)
    //Register used CatalogTypes(like EntityType and ItemType) for (de)serialization
    .setDefaultOptions(ConfigurationOptions.defaults()
      .setSerializers(TypeSerializers.getDefaultSerializers.addCatalogTypes(serializerFactory,
        ConfigurationHelper.catalogTypeSerializerClasses)))
    .build()

  //Generate default config if it doesn't exist
  if (!configFile.exists()) resetToDefaultConfig()

  def saveConfig(config: NightmareAiConfig): Unit = {
    val rootNode = configurationLoader.load()
    configMapper.serialize(rootNode)
    configurationLoader.save(rootNode)
  }

  def loadConfig(): NightmareAiConfig = configMapper.populate(configurationLoader.load())

  def resetToDefaultConfig(): Unit = {
    configFile.delete()
    saveConfig(new NightmareAiConfig())
    System.out.println("Reset config!")
  }
}