package com.neymeha.game.seeinggod.ecs.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.neymeha.game.seeinggod.ecs.components.HealthComponent
import com.neymeha.game.seeinggod.ecs.factory.EnemyFactory
import com.neymeha.game.seeinggod.ecs.utils.Mappers

/**
 * Система SpawnSystem отвечает за появление нового врага после смерти текущего.
 *
 * Проверяет всех врагов с компонентом здоровья (HealthComponent),
 * и если здоровье <= 0, удаляет сущность и создает нового врага.
 *
 * Эта логика важна для базового геймплейного цикла: клик → урон → смерть → следующий враг.
 */
class SpawnSystem(private val engine: Engine) : EntitySystem() {

    // Получаем список всех сущностей с компонентом HealthComponent
    private val entities: ImmutableArray<Entity>
        get() = engine.getEntitiesFor(Family.all(HealthComponent::class.java).get())

    override fun update(deltaTime: Float) {
        // Перебираем всех "живых" врагов
        for (entity in entities) {
            val health = Mappers.health.get(entity)
            if (health.currentHealth <= 0) {
                Log.logger.info { "Враг с ID ${entity} погиб. Спавним нового врага." }
                // Удаляем мертвого врага
                engine.removeEntity(entity)

                // Создаем нового врага через EnemyFactory
//                engine.addEntity(EnemyFactory.createEnemy())

                // Прерываем цикл — в текущей версии только один враг активен за раз
                break
            }
        }
    }
}
