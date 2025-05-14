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
object EnemyFactory {

    /**
     * Функция для создания новой сущности врага.
     * Каждый враг имеет компоненты здоровья, позиции и компонент для обработки кликов.
     *
     * @return Возвращает созданную сущность врага.
     */
    fun createEnemy(): Entity {
        // Создаём новую сущность
        val enemy = Entity()

        // Добавляем компонент здоровья (врагу дается 100 максимального и текущего здоровья)
        enemy.add(HealthComponent(100, 100))

        // Добавляем компонент позиции (позиция (100, 100), размер (64x64) пикселя)
        enemy.add(PositionComponent(100f, 100f, 64f, 64f))

        // Добавляем компонент для обработки кликов (враг будет реагировать на клик)
        enemy.add(ClickComponent())

        // Добавляем тип сущности
        enemy.add(EntityTypeComponent(EntityType.ENEMY))

        // Возвращаем созданную сущность
        return enemy
    }
}
