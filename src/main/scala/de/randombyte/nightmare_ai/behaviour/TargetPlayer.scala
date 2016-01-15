package de.randombyte.nightmare_ai.behaviour

import org.slf4j.Logger
import org.spongepowered.api.entity.ai.task.builtin.creature.AttackLivingAITask
import org.spongepowered.api.entity.ai.task.builtin.creature.target.TargetAITask
import org.spongepowered.api.entity.living.Agent
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.entity.ai.AITaskEvent

/**
  * When an Agent targets a Player every Agent of the same type in radius also attacks Player
  */
class TargetPlayer(plugin: Object, entityRadius: Map[Class[_ <: Agent], Int], logger: Logger) {

  @Listener
  def onTargetPlayer(event: AITaskEvent): Unit = {
    event.getTask match {
      case attackTask: AttackLivingAITask => {
        logger.debug(s"${event.getTargetEntity.getType.getName} is attacking ${attackTask.getTargetClass}")
      }
      case targetAITask: TargetAITask[_] => {
        logger.debug(s"${event.getTargetEntity.getType.getName} is targeting ${event.getTargetEntity.getTarget.get()}")
      }
      case _ =>
    }
  }

}
