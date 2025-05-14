package com.neymeha.game.seeinggod.ecs.components

import com.badlogic.ashley.core.Component

// Компонент для хранения типа сущности (ID)
class EntityTypeComponent(val type: EntityType) : Component

enum class EntityType {
    ENEMY,
    GOLD,
    PLAYER,
    OBSTACLE
}
