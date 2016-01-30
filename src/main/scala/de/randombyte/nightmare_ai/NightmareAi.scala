package de.randombyte.nightmare_ai

import java.nio.file.Path

import com.google.inject.Inject
import de.randombyte.nightmare_ai.behaviour.DropItemsOnDeath
import de.randombyte.nightmare_ai.config.GsonConfigurationManager
import org.slf4j.Logger
import org.spongepowered.api.Game
import org.spongepowered.api.config.DefaultConfig
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.game.state.GameInitializationEvent
import org.spongepowered.api.plugin.Plugin

@Plugin(name = "NightmareAI", id = "NightmareAI", version = "0.1")
class NightmareAi {

  private val version = this.getClass.getAnnotation(classOf[Plugin]).version().toDouble

  @Inject
  @DefaultConfig(sharedRoot = true)
  private val configPath: Path = null
  private lazy val configManager = new GsonConfigurationManager(configPath.toFile, game.getRegistry, version, logger)

  @Inject private val logger: Logger = null
  @Inject private val game: Game = null

  private var enabled = false

  @Listener
  def onInit(event: GameInitializationEvent): Unit = {

    logger.info(s"NightmareAI loading...")

    val configOpt = configManager.load()
    if (configOpt.isEmpty) {
      logger.error("NightmareAI couldn't be loaded! This plugin won't work!")
      return
    }
    val config = configOpt.get

    game.getEventManager.registerListeners(NightmareAi.this,
      new DropItemsOnDeath(NightmareAi.this, config.dropItemsOnDeathConfig, logger))
    //game.getEventManager.registerListeners(NightmareAi.this, new TargetPlayer(NightmareAi.this, Map(classOf[Zombie] -> 10), logger))

    logger.info(s"NightmareAI version $version: Done loading!")
  }
}