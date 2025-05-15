package com.neymeha.game.seeinggod.ecs.factory

import com.badlogic.ashley.core.Entity
import com.neymeha.game.seeinggod.ecs.components.*

/**
 * EnemyFactory предоставляет метод для создания новых врагов.
 * Каждый враг представляет собой сущность (Entity) с набором компонентов:
 * - HealthComponent (здоровье),
 * - PositionComponent (позиция и размер),
 * - ClickComponent (для отслеживания кликов по врагу).
 */
object EntityFactory {

    /**
     * Функция для создания новой сущности врага.
     * Каждый враг имеет компоненты здоровья, позиции и компонент для обработки кликов.
     *
     * @return Возвращает созданную сущность врага.
     */
    fun createEnemy(maxHealth: Long): Entity {
        // Создаём новую сущность
        val enemy = Entity()

        // Добавляем компонент здоровья (врагу дается 100 максимального и текущего здоровья)
        enemy.add(HealthComponent(maxHealth, maxHealth))

        // Добавляем компонент позиции (позиция (100, 100), размер (64x64) пикселя)
        enemy.add(PositionComponent(100f, 100f, 64f, 64f))

        // Добавляем компонент для обработки кликов (враг будет реагировать на клик)
        enemy.add(ClickComponent())

        // Добавляем тип сущности
        enemy.add(EntityTypeComponent(EntityType.ENEMY))

        // Возвращаем созданную сущность
        return enemy
    }

    fun createBoss(maxHealth: Long): Entity {
        val boss = Entity()
        boss.add(PositionComponent(100f, 100f, 128f, 128f))
        boss.add(HealthComponent(maxHealth, maxHealth))
        boss.add(ClickComponent())
        boss.add(EntityTypeComponent(EntityType.BOSS))
        return boss
    }

    fun createGold(count: Int, value: Long): List<Entity> {
        val goldList = mutableListOf<Entity>()
        for (i in 0 until count) {
            val gold = Entity()
            gold.add(PositionComponent(200f, 200f, 8f, 8f))
            gold.add(HealthComponent(10, 10))
            gold.add(GoldComponent(value))
            gold.add(ClickComponent())
            gold.add(EntityTypeComponent(EntityType.GOLD))
            goldList.add(gold)
        }

        return goldList
    }

    fun createPlayer(): Entity {
        val player = Entity()
        player.add(PositionComponent(100f, 150f, 32f, 32f))
        player.add(AttackComponent(10))
        player.add(GoldComponent(0))
        player.add(EntityTypeComponent(EntityType.PLAYER))
        return player
    }

    fun createCompanion(): Entity {
        val companion = Entity()
        companion.add(PositionComponent(100f, 100f, 16f, 16f))
        companion.add(AttackComponent(3))
        companion.add(EntityTypeComponent(EntityType.COMPANION))
        return companion
    }

    fun createAttendant(): Entity {
        val attendant = Entity()
        attendant.add(PositionComponent(100f, 100f, 28f, 28f))
        attendant.add(AttackComponent(5))
        attendant.add(EntityTypeComponent(EntityType.ATTENDANT))
        return attendant
    }
}
