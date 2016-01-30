package de.randombyte.nightmare_ai.config

import com.google.gson.annotations.Since
import org.slf4j.{Logger, MarkerFactory}
import org.spongepowered.api.entity.{ArmorEquipable, EntityType, EntityTypes}
import org.spongepowered.api.item.{ItemType, ItemTypes}

import scala.collection.JavaConversions._

object GsonNightmareAiConfig {
  val marker = MarkerFactory.getMarker("ConfigValidator")
}

/**
  * Represents the configuration, backed by the real configfile.
  * Using Java Maps and Lists so Gson can handle them.
  */
class GsonNightmareAiConfig(dropItemsOnDeath : GsonDropItemsOnDeathConfig = new GsonDropItemsOnDeathConfig()) {

  /**
    * Checks if the config is valid. Does some validations on type.
    *
    * @param logger For logging validation errors to the user
    * @return True if config is correct, false if not
    */
  def toConfig(logger: Logger): Option[NightmareAiConfig] = {

    if (dropItemsOnDeath.enabledArmorEquipables.exists(_._1.getClass.isAssignableFrom(classOf[ArmorEquipable])))
      return logger.e(GsonNightmareAiConfig.marker, "DropItemsOnDeath: Some non-ArmorEquipables in enabledEntities!")

    if (dropItemsOnDeath.enabledArmorEquipables.exists(_._1.equals(EntityTypes.PLAYER)))
      return logger.e(GsonNightmareAiConfig.marker, "DropItemsOnDeath: Player must not be in enabledArmorEquipables!")

    Some(
      NightmareAiConfig(
        DropItemsOnDeathConfig(
          dropItemsOnDeath.enabledArmorEquipables.filter(_._2).keySet.map(e => e.getEntityClass.asSubclass(classOf[ArmorEquipable])).toSet,
          dropItemsOnDeath.dropItemsBlacklist.toList
        )
      )
    )
  }
}

class GsonDropItemsOnDeathConfig(
  @Since(0.1) val enabledArmorEquipables: java.util.Map[EntityType, Boolean] =
    DefaultMap.create(List(EntityTypes.GIANT, EntityTypes.SKELETON, EntityTypes.ZOMBIE, EntityTypes.PIG_ZOMBIE), true),
  @Since(0.1) val dropItemsBlacklist: java.util.List[ItemType] = List(ItemTypes.ARROW)
)
