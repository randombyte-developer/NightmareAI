package de.randombyte.nightmare_ai.config

import org.spongepowered.api.entity.ArmorEquipable
import org.spongepowered.api.item.ItemType

/**
  * Represents the usable config object
  */
case class NightmareAiConfig(dropItemsOnDeathConfig: DropItemsOnDeathConfig)

case class DropItemsOnDeathConfig(
  val enabledArmorEquipables: Set[Class[_ <: ArmorEquipable]],
  val dropItemsBlacklist: List[ItemType]
)
