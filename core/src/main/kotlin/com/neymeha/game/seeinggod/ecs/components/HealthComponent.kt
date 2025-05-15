package com.neymeha.game.seeinggod.ecs.components

import com.badlogic.ashley.core.Component

/**
 * Компонент, описывающий здоровье сущности.
 * Этот компонент необходим для управления уровнем здоровья сущности,
 * а также для выполнения логики урона и смерти.
 *
 * @property maxHealth Максимальное количество здоровья сущности.
 * @property currentHealth Текущее количество здоровья сущности.
 */
class HealthComponent(
    var maxHealth: Long,    // Максимальное количество здоровья сущности.
    var currentHealth: Long // Текущее количество здоровья сущности.
) : Component
