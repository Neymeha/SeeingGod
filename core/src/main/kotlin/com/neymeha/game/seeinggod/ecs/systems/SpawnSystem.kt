package com.neymeha.game.seeinggod.ecs.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.neymeha.game.seeinggod.ecs.components.EntityType
import com.neymeha.game.seeinggod.ecs.components.HealthComponent
import com.neymeha.game.seeinggod.ecs.factory.EntityFactory
import com.neymeha.game.seeinggod.ecs.utils.Mappers

/**
 * SpawnSystem — отвечает за спавн сущностей при смерти.
 * Если здоровье сущности (ENEMY или BOSS) <= 0:
 * - Удаляет сущность
 * - Создаёт нового врага
 * - Спавнит золото
 */
class SpawnSystem(private val engine: Engine) : EntitySystem() {

    // Все сущности с компонентом здоровья
    private val entities: ImmutableArray<Entity>
        get() = engine.getEntitiesFor(Family.all(HealthComponent::class.java).get())

    override fun update(deltaTime: Float) {
        for (entity in entities) {
            val health = Mappers.health.get(entity)
            val type = Mappers.type.get(entity)

            if (health.currentHealth > 0) continue

            Log.logger.info { "Сущность ${type.type} (${entity}) уничтожена." }

            engine.removeEntity(entity)

            when (type.type) {
                EntityType.BOSS -> {
                    Log.logger.info { "BOSS погиб. Спавним золото и врага." }
                    spawnGold(count = 3, value = 2)
                    engine.addEntity(EntityFactory.createEnemy(100))
                }

                EntityType.ENEMY -> {
                    Log.logger.info { "ENEMY погиб. Спавним золото и врага." }
                    spawnGold(count = 3, value = 1)
                    engine.addEntity(EntityFactory.createEnemy(100))
                }

                else -> {
                    Log.logger.info { "Тип ${type.type} не обрабатывается SpawnSystem." }
                }
            }

            // Предполагаем, что обрабатываем одного врага за раз
            break
        }
    }

    /**
     * Спавнит указанное количество золота и добавляет его в движок.
     */
    private fun spawnGold(count: Int, value: Long) {
        val goldList = EntityFactory.createGold(count, value)
        for (gold in goldList) {
            engine.addEntity(gold)
            Log.logger.info { "Золото создано: value=$value" }
        }
    }
}
