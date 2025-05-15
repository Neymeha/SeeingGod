package com.neymeha.game.seeinggod.ecs.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector3
import com.neymeha.game.seeinggod.ecs.components.*
import com.neymeha.game.seeinggod.ecs.utils.Mappers

/**
 * ClickSystem — система обработки кликов по сущностям.
 * Если пользователь кликает по врагу или золоту, соответствующей сущности наносится урон.
 */
class ClickSystem : EntitySystem() {

    // Получаем сущности, у которых есть и позиция, и компонент клика
    private val entities: ImmutableArray<Entity>
        get() = engine.getEntitiesFor(
            Family.all(ClickComponent::class.java, PositionComponent::class.java).get()
        )

    override fun update(deltaTime: Float) {
        if (Gdx.input.justTouched()) {
            Log.logger.info { "Клик зафиксирован." }

            // Получаем координаты касания
            val touchPos = Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
            val invertedY = Gdx.graphics.height - touchPos.y

            for (entity in entities) {
                val pos = Mappers.position.get(entity)
                val type = Mappers.type.get(entity)

                val withinBounds = touchPos.x in pos.x..(pos.x + pos.width) &&
                    invertedY in pos.y..(pos.y + pos.height)

                if (!withinBounds) continue

                when (type.type) {
                    EntityType.ENEMY, EntityType.BOSS -> {
                        entity.add(DamageComponent(10))
                        Log.logger.info {
                            "Урон нанесён по сущности типа ${type.type}. Координаты попадания: x=${touchPos.x}, y=${invertedY}"
                        }
                    }

                    EntityType.GOLD -> {
                        entity.add(DamageComponent(10))
                        Log.logger.info {
                            "Клик по золоту. Координаты: x=${touchPos.x}, y=${invertedY}, value=${Mappers.gold.get(entity).value}"
                        }
                    }

                    else -> {
                        Log.logger.info {
                            "Клик был по сущности типа ${type.type}, но она не обрабатывается системой кликов."
                        }
                    }
                }

                break // только одна сущность обрабатывается за кадр
            }
        }
    }
}
