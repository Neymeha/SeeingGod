package com.neymeha.game.seeinggod.ecs.components

import com.badlogic.ashley.core.Component

// Компонент для хранения типа сущности (ID)
class EntityTypeComponent(val type: EntityType) : Component

enum class EntityType(val baseSize: Float) {
    ENEMY(64f),
    BOSS(240f),
    GOLD(32f),
    PLAYER(64f),
    ATTENDANT(64f),
    COMPANION(64f)
}
