package com.neymeha.game.seeinggod.ecs.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector3
import com.neymeha.game.seeinggod.ecs.components.*
import com.neymeha.game.seeinggod.ecs.utils.Mappers

/**
 * Система ClickSystem отслеживает клики по сущностям с компонентом ClickComponent.
 * Когда пользователь кликает по экрану, система проверяет, был ли клик по какой-либо сущности.
 * Если клик был на сущности, то она получает урон, добавляя DamageComponent.
 */
class ClickSystem : EntitySystem() {

    // Получаем все сущности, которые имеют как компонент ClickComponent, так и компонент PositionComponent.
    private val entities: ImmutableArray<Entity>
        get() = engine
            .getEntitiesFor(
                Family.all(ClickComponent::class.java, PositionComponent::class.java).get()
            )

    override fun update(deltaTime: Float) {
        // Проверяем, был ли клик на экране
        if (Gdx.input.justTouched()) {
            Log.logger.info { "Нажатие" }

            // Получаем позицию касания на экране (в пикселях)
            val touchPos = Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)

            // Инвертируем координату Y для того, чтобы она соответствовала системе координат отрисовки
            val invertedY = Gdx.graphics.height - touchPos.y

            // Проходим по всем сущностям с компонентами ClickComponent и PositionComponent
            for (entity in entities) {
                // Получаем компонент PositionComponent сущности, чтобы узнать её координаты
                val pos = Mappers.position.get(entity)
                val type = Mappers.type.get(entity)
                Log.logger.info { "Нашли энтити, которое реагирует на нажатие его x:${pos.x} y:${pos.y} нажатие x:${touchPos.x} y:${invertedY}" }

                // Проверяем, попадает ли точка касания в область сущности
                // Для этого мы сравниваем координаты касания с размерами сущности
                if (type.type == EntityType.ENEMY && touchPos.x in pos.x..(pos.x + pos.width) && invertedY in pos.y..(pos.y + pos.height)) {
                    // Если клик произошел по сущности, добавляем ей DamageComponent с фиксированным уроном (10)
                    entity.add(DamageComponent(10)) // фиксированный урон
                    Log.logger.info { "Сущность была поражена, нанесен урон." }
                    break // Заканчиваем цикл, чтобы не обрабатывать несколько кликов на разных сущностях
                }
            }
        }
    }
}
