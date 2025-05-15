package com.neymeha.game.seeinggod.ecs.factory

import com.badlogic.ashley.core.Entity
import com.neymeha.game.seeinggod.ecs.components.*

/**
 * EntityFactory — фабрика для создания игровых сущностей.
 * Сущности создаются с различными компонентами:
 * - HealthComponent, PositionComponent, ClickComponent, AttackComponent и др.
 * - EntityTypeComponent определяет тип сущности.
 */
object EntityFactory {

    /**
     * Создает врага с заданным максимальным здоровьем.
     */
    fun createEnemy(maxHealth: Long): Entity {
        val enemy = Entity().apply {
            add(HealthComponent(maxHealth, maxHealth))
            add(PositionComponent(100f, 100f, 64f, 64f))
            add(ClickComponent())
            add(EntityTypeComponent(EntityType.ENEMY))
        }
        Log.logger.info { "Создан враг с $maxHealth HP" }
        return enemy
    }

    /**
     * Создает босса с заданным максимальным здоровьем.
     */
    fun createBoss(maxHealth: Long): Entity {
        val boss = Entity().apply {
            add(PositionComponent(100f, 100f, 128f, 128f))
            add(HealthComponent(maxHealth, maxHealth))
            add(ClickComponent())
            add(EntityTypeComponent(EntityType.BOSS))
        }
        Log.logger.info { "Создан БОСС с $maxHealth HP" }
        return boss
    }

    /**
     * Создает список золотых сущностей.
     *
     * @param count Количество сущностей золота
     * @param value Стоимость одной единицы
     */
    fun createGold(count: Int, value: Long): List<Entity> {
        val goldList = mutableListOf<Entity>()
        repeat(count) {
            val gold = Entity().apply {
                add(PositionComponent(200f, 200f, 8f, 8f))
                add(HealthComponent(10, 10))
                add(GoldComponent(value))
                add(ClickComponent())
                add(EntityTypeComponent(EntityType.GOLD))
            }
            goldList.add(gold)
            Log.logger.info { "Создано золото (value=$value)" }
        }
        return goldList
    }

    /**
     * Создает игрока.
     */
    fun createPlayer(): Entity {
        val player = Entity().apply {
            add(PositionComponent(100f, 150f, 32f, 32f))
            add(AttackComponent(10))
            add(GoldComponent(0))
            add(EntityTypeComponent(EntityType.PLAYER))
        }
        Log.logger.info { "Создан игрок" }
        return player
    }

    /**
     * Создает спутника (companion).
     */
    fun createCompanion(): Entity {
        val companion = Entity().apply {
            add(PositionComponent(100f, 100f, 16f, 16f))
            add(AttackComponent(3))
            add(EntityTypeComponent(EntityType.COMPANION))
        }
        Log.logger.info { "Создан спутник (companion)" }
        return companion
    }

    /**
     * Создает обслуживающего помощника (attendant).
     */
    fun createAttendant(): Entity {
        val attendant = Entity().apply {
            add(PositionComponent(100f, 100f, 28f, 28f))
            add(AttackComponent(5))
            add(EntityTypeComponent(EntityType.ATTENDANT))
        }
        Log.logger.info { "Создан attendant" }
        return attendant
    }
}
