package de.randombyte.nightmare_ai.config

import java.io.{IOException, File}

import com.google.common.base.Charsets
import com.google.common.io.Files
import com.google.gson.{JsonIOException, JsonSyntaxException, JsonParseException, GsonBuilder}
import org.slf4j.{MarkerFactory, Logger}
import org.spongepowered.api.{CatalogType, GameRegistry}
import org.spongepowered.api.entity.EntityType
import org.spongepowered.api.item.ItemType

object GsonConfigurationManager {
  val marker = MarkerFactory.getMarker("ConfigParser")
}

/**
  * Manages the config file representation of NightmareAiConfig
  * @param file The File the config should be in
  * @param registry GameRegistry for deserializing string representation of CatalogTypes
  * @param version The version of the plugin(for maintaining compatibility with future versions)
  * @param logger A Logger for pushing information to the user
  */
class GsonConfigurationManager(file: File, registry: GameRegistry, version: Double, logger: Logger) {

  val gson = {
    val builder = new GsonBuilder().setVersion(version)
      /* Some classes Sponge have fields in its superclass with same name which bothers GSON, so prevent GSON even
        thinking about what to do with them */
      .addSerializationExclusionStrategy(new SubclassExclusionStrategy())
      .addDeserializationExclusionStrategy(new SubclassExclusionStrategy())
      .enableComplexMapKeySerialization() //So keys of Maps are Strings provided by JsonSerializer and not by toString()
      .setPrettyPrinting()
    //Register used CatalogTypes for (de)serialization
    List(classOf[EntityType], classOf[ItemType])
      .foreach((clazz: Class[_ <: CatalogType]) => builder.registerTypeAdapter(clazz, new CatalogTypeDeSerializer(registry, clazz)))
    builder.create()
  }

  if (!file.exists()) {
    logger.info("Configuration file doesn't exist -> creating default config file")
    save(new GsonNightmareAiConfig())
  }

  def load(): Option[NightmareAiConfig] = {

    try {
      gson.fromJson(Files.newReader(file, Charsets.UTF_8), classOf[GsonNightmareAiConfig]).toConfig(logger)
    } catch {
      case ex: JsonParseException => {
        logger.error(GsonConfigurationManager.marker, s"JsonParseException: ${ex.toString}")
        return None
      }
      case ex: JsonSyntaxException =>  {
        logger.error(GsonConfigurationManager.marker, s"JsonSyntaxException: ${ex.toString}")
        return None
      }
      case ex: JsonIOException =>  {
        logger.error(GsonConfigurationManager.marker, s"JsonIOException: ${ex.toString}")
        return None
      }
    }
  }

  @throws(classOf[IOException])
  def save(config: GsonNightmareAiConfig): Unit = Files.write(gson.toJson(config), file, Charsets.UTF_8)
}
