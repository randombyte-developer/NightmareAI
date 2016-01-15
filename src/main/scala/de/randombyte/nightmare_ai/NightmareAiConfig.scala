package de.randombyte.nightmare_ai

import com.google.gson.annotations.Since
import org.spongepowered.api.entity.{EntityType, EntityTypes}
import org.spongepowered.api.item.{ItemType, ItemTypes}

/**
  * Represents the configuration, backed by the real configfile
  */
class NightmareAiConfig(var dropItemsOnDeath : DropItemsOnDeathConfig = new DropItemsOnDeathConfig())

  class DropItemsOnDeathConfig(
    @Since(0.1) var enabledMobs: Map[EntityType, Boolean] = List(EntityTypes.GIANT, EntityTypes.SKELETON,
      EntityTypes.ZOMBIE, EntityTypes.PIG_ZOMBIE).map { mob => mob -> true }.toMap,
    @Since(0.1) var dropItemsBlacklist: List[ItemType] = List(ItemTypes.ARROW)
  ) extends EntityTypeMapConfig(enabledMobs)

class EntityTypeListConfig(enabledEntities: List[EntityType])
class EntityTypeMapConfig[AdditionalValue](enabledEntities: Map[EntityType, AdditionalValue])