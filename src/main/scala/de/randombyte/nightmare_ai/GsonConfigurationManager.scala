package de.randombyte.nightmare_ai

import java.io.{IOException, File}

import com.google.common.base.Charsets
import com.google.common.io.Files
import com.google.gson.GsonBuilder
import de.randombyte.nightmare_ai.serializer.{GsonCatalogTypeDeserializer, GsonCatalogTypeSerializer}
import org.spongepowered.api.{GameRegistry, CatalogType}
import org.spongepowered.api.entity.EntityType

class GsonConfigurationManager(file: File, registry: GameRegistry) {

  val gson = new GsonBuilder().setVersion(NightmareAi.version)
    .registerTypeAdapter(classOf[CatalogType], new GsonCatalogTypeSerializer())
    .registerTypeAdapter(classOf[EntityType], new GsonCatalogTypeDeserializer(registry, classOf[EntityType]))
    .setPrettyPrinting()
    .create()

  if (!file.exists()) save(new NightmareAiConfig())

  def load(): NightmareAiConfig = {
    gson.fromJson(scala.io.Source.fromFile(file).mkString,
      classOf[NightmareAiConfig])
  }

  @throws(classOf[IOException])
  def save(config: NightmareAiConfig): Unit = {
    Files.write(gson.toJson(config), file, Charsets.UTF_8)
  }
}
