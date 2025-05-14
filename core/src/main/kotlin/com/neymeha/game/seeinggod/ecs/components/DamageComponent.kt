package com.neymeha.game.seeinggod.ecs.components

import com.badlogic.ashley.core.Component

/**
 * Компонент, описывающий урон, который может быть нанесен сущностью.
 * Этот компонент используется для хранения информации о том,
 * сколько урона сущность может нанести другим сущностям.
 *
 * @property damage Количество урона, который наносит сущность.
 */
class DamageComponent(
    var damage: Int // Количество урона, который наносит сущность.
) : Component
