package com.neymeha.game.seeinggod.ecs.systems

import com.badlogic.ashley.core.*
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.ashley.utils.ImmutableArray
import com.neymeha.game.seeinggod.ecs.components.*
import com.neymeha.game.seeinggod.ecs.utils.Mappers
import ktx.graphics.use

/**
 * Система отрисовки всех сущностей с позициями и анимациями.
 */
class RenderSystem(
    private val batch: SpriteBatch
) : EntitySystem() {

    private lateinit var entities: ImmutableArray<Entity>
    private var stateTime = 0f

    override fun addedToEngine(engine: Engine) {
        // Ищем только те сущности, у которых есть компоненты позиции и анимации
        entities = engine.getEntitiesFor(
            Family.all(PositionComponent::class.java, AnimationComponent::class.java).get()
        )
        Log.logger.info { "RenderSystem добавлена в движок. Найдено сущностей для рендера: ${entities.size()}" }
    }

    override fun update(deltaTime: Float) {

        batch.use {
            for (entity in entities) {
                val position = Mappers.position.get(entity)
                val animationComp = Mappers.animation.get(entity)

                val animation: Animation<TextureRegion> = animationComp.animation
                val frame = animation.getKeyFrame(stateTime, true)

                animationComp.stateTime += deltaTime
                stateTime = animationComp.stateTime

                if (frame == null) {
                    Log.logger.warn { "Frame для сущности $entity не найден!" }
                    continue
                }

                it.draw(frame, position.x, position.y, position.width, position.height)
            }
        }
    }
}
