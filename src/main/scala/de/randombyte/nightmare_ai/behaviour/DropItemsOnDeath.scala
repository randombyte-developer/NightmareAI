package de.randombyte.nightmare_ai.behaviour

import org.slf4j.Logger
import org.spongepowered.api.entity.ArmorEquipable
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.entity.SpawnEntityEvent

import scala.collection.JavaConversions

/**
  * Drops all items of provided EntityTypes(except Players) at death
  * @param plugin The plugin object for cause-tracking
  * @param enabledEntityClasses The EntityTypes this behaviour is enabled for
  */
class DropItemsOnDeath(plugin: Object, enabledEntityClasses: List[Class[_ <: ArmorEquipable]], logger: Logger) {

  /**
    * Set Equipment mob drops to 1, so they definitely drop
    */
  @Listener
  def onSpawnEntity(event: SpawnEntityEvent): Unit = {
    val spawnEntities = JavaConversions.asScalaIterator(event.getEntities.iterator())
    for (entity <- spawnEntities) {
      //set DropChances for Equipment to 1
    }
  }

  /*  /**
      * Drops the Equipment of the dead ArmorEquipable
      */
    @Listener def onEntityDeath(event: DestructEntityEvent.Death): Unit = {
      val entity = event.getTargetEntity
      for (enabledEntityClass <- enabledEntityClasses) {
        if (entity.getClass.equals(enabledEntityClass) && !entity.getType.equals(EntityTypes.PLAYER)) {
          for (itemToDrop <- entity.asInstanceOf[ArmorEquipable].getItems) {
            if (itemToDrop.isPresent) {
              dropItem(entity.getLocation, itemToDrop.get.createSnapshot(), Cause.of(event, this, plugin))
              logger.debug(s"Droppped ${itemToDrop.get.getItem.getName} for ${entity.get(Keys.DISPLAY_NAME)}")
            }
          }
        }
      }
    }*/


/*  @Listener def onSpawnItem(event: SpawnEntityEvent): Unit = {
    val spawnEntities = JavaConversions.asScalaIterator(event.getEntities.iterator())
    val string = spawnEntities.mkString(", ")
    if (string.contains("chestplate")) {
      for (elem <- spawnEntities) {logger.debug(elem.getType.toString)}
      logger.debug(s"Chestplate: $string ||| ${event.getCause}")
    }
    for (entityClasses <- enabledEntityClasses) {
      if (event.getCause.containsType(entityClasses)) {
        //logger.debug(s"Accepted cause: ${event.getCause.toString}")
        //logger.debug(spawnEntities.mkString("Spawning entities: ", ", ", ""))
        for (itemEntity <- spawnEntities.filter(_.getType.equals(EntityTypes.ITEM))) {
          //logger.debug(spawnEntities.mkString("Spawning item(s): ", ", ", ""))
          if (itemEntity.asInstanceOf[Item].getItemData.item.get.isInstanceOf[EquipmentTypeWorn]) {
            //logger.debug(s"Deleting ${itemEntity.toString}")
            event.setCancelled(true)
          }
        }
      }
    }
  }*/

/*  implicit class ImprovedArmorEquipable(armorEquipable: ArmorEquipable) {
    /**
      * Returns the full Equipment of an ArmorEquipable
      * @return
      */
    def getItems = List(armorEquipable.getItemInHand, armorEquipable.getHelmet,
      armorEquipable.getChestplate, armorEquipable.getLeggings, armorEquipable.getBoots)
  }


  /**
    * Drops the specified item at the given location
    * @param location The location the drop should spawn
    * @param itemStackSnapshot The item(s) to drop
    * @param cause The cause for this spawn(an event, the plugin, etc.)
    */
  def dropItem(location: Location[World], itemStackSnapshot: ItemStackSnapshot,  cause: Cause): Unit = {
    val optionalItemEntity = location.getExtent.createEntity(EntityTypes.ITEM, location.getPosition)
    if (optionalItemEntity.isPresent) {
      val itemEntity = optionalItemEntity.get
        itemEntity.offer(Keys.REPRESENTED_ITEM, itemStackSnapshot)
        location.getExtent.spawnEntity(itemEntity, cause)
    }
  }*/
}