package de.randombyte.nightmare_ai

import java.nio.file.Path

import com.google.inject.Inject
import de.randombyte.nightmare_ai.behaviour.{TargetPlayer, DropItemsOnDeath}
import org.slf4j.Logger
import org.spongepowered.api.Game
import org.spongepowered.api.command.args.CommandContext
import org.spongepowered.api.command.spec.{CommandExecutor, CommandSpec}
import org.spongepowered.api.command.{CommandResult, CommandSource}
import org.spongepowered.api.config.DefaultConfig
import org.spongepowered.api.entity.living.monster.{Zombie, Skeleton}
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.game.state.GameInitializationEvent
import org.spongepowered.api.plugin.Plugin

object NightmareAi {
  val version = 0.1
}

@Plugin(name = "NightmareAI", id = "NightmareAI", version = "0.1")
class NightmareAi {

  @Inject
  @DefaultConfig(sharedRoot = true)
  private val configPath: Path = null
  private lazy val configManager = new GsonConfigurationManager(configPath.toFile, game.getRegistry)

  @Inject private val logger: Logger = null
  @Inject private val game: Game = null

  private var enabled = false

  @Listener
  def onInit(event: GameInitializationEvent): Unit = {
    game.getCommandManager.register(this, CommandSpec.builder()
      .executor(new CommandExecutor {
        override def execute(commandSource: CommandSource, commandContext: CommandContext): CommandResult = {
          logger.debug(configManager.load().dropItemsOnDeath.dropItemsBlacklist.mkString("Blacklist: ", ", ", ""))
          CommandResult.success()
        }
      })
      .build(), "reloadConf")

    /*game.getCommandManager.register(this, CommandSpec.builder()
      .executor(new CommandExecutor {
        override def execute(src: CommandSource, args: CommandContext): CommandResult = {
          enabled = !enabled
          if (enabled) {
            game.getEventManager.registerListeners(NightmareAi.this,
              new DropItemsOnDeath(this, List(classOf[Zombie], classOf[Skeleton]), logger))
          } else {
            game.getEventManager.unregisterPluginListeners(this)
          }
          src.sendMessage(Text.of(enabled.toString))
          CommandResult.success()
        }
      })
      .build(), "toggle")*/

    val config = configManager.load()

    game.getEventManager.registerListeners(NightmareAi.this,
      new DropItemsOnDeath(NightmareAi.this, List(classOf[Zombie], classOf[Skeleton]), logger))
    game.getEventManager.registerListeners(NightmareAi.this,
      new TargetPlayer(NightmareAi.this, Map(classOf[Zombie] -> 10), logger))

    logger.info(s"NightmareAI version ${NightmareAi.version} loaded!")
  }
}