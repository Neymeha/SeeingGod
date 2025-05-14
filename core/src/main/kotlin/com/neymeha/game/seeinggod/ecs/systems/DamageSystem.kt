package com.neymeha.game.seeinggod.ecs.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.neymeha.game.seeinggod.ecs.components.*
import com.neymeha.game.seeinggod.ecs.utils.Mappers

/**
 * Система DamageSystem управляет процессом нанесения урона сущностям с компонентами HealthComponent и DamageComponent.
 *
 * Процесс работы системы:
 * - Ищет все сущности, у которых есть компоненты HealthComponent и DamageComponent.
 * - Уменьшает значение здоровья сущности на величину урона.
 * - Удаляет компонент DamageComponent, так как урон был применен.
 *
 * Эта система важна для того, чтобы обрабатывать урон, получаемый врагами, после того как они были атакованы.
 */
class DamageSystem : EntitySystem() {

    // Получаем все сущности, у которых есть как HealthComponent, так и DamageComponent
    private val entities: ImmutableArray<Entity>
        get() = engine
            .getEntitiesFor(
                Family.all(HealthComponent::class.java, DamageComponent::class.java).get()
            )

    override fun update(deltaTime: Float) {
        // Перебираем все сущности, которые могут получить урон
        for (entity in entities) {
            // Получаем компоненты здоровья и урона для текущей сущности
            val health = Mappers.health.get(entity)
            val damage = Mappers.damage.get(entity)

            Log.logger.debug { "Наносим урон сущности ${entity}. Урон: ${damage.damage}, Здоровье до: ${health.currentHealth}" }
            // Наносим урон, уменьшая текущее здоровье сущности
            health.currentHealth -= damage.damage

            // Удаляем компонент DamageComponent, так как урон уже был применен
            entity.remove(DamageComponent::class.java)
            Log.logger.debug { "Здоровье сущности ${entity} после урона: ${health.currentHealth}" }
        }
    }
}
