package de.randombyte.nightmare_ai.behaviour

import de.randombyte.nightmare_ai.config.DropItemsOnDeathConfig
import org.slf4j.Logger
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.entity.SpawnEntityEvent

import scala.collection.JavaConversions._

/**
  * Drops all items of provided EntityTypes at death
  *
  * @param plugin The plugin object for cause-tracking
  * @param config The corresponding config
  */
class DropItemsOnDeath(plugin: Object, config: DropItemsOnDeathConfig, logger: Logger) {

  /**
    * Set Equipment mob drops to 1, so they definitely drop
    */
  @Listener
  def onSpawnEntity(event: SpawnEntityEvent): Unit = {
    val enabledEntities = event.getEntities.filter(e => config.enabledArmorEquipables.exists(
      c => e.getType.getEntityClass.equals(c)))
    //todo enabledEntities foreach set DropChances for Equipment to 1, wait for Api
  }
}