package com.neymeha.game.seeinggod.ecs.utils

import com.badlogic.ashley.core.ComponentMapper
import com.neymeha.game.seeinggod.ecs.components.*

/**
 * Утилитный объект Mappers предоставляет быстрый доступ к компонентам сущностей.
 *
 * ComponentMapper — это оптимизированный способ получения компонентов у сущности
 * без необходимости вручную перебирать список компонентов.
 *
 * Используется во всех системах, чтобы ускорить доступ и улучшить читаемость кода.
 */
object Mappers {
    val health = ComponentMapper.getFor(HealthComponent::class.java)
    val damage = ComponentMapper.getFor(DamageComponent::class.java)
    val click = ComponentMapper.getFor(ClickComponent::class.java)
    val position = ComponentMapper.getFor(PositionComponent::class.java)
    val type = ComponentMapper.getFor(EntityTypeComponent::class.java)

    init {
        Log.logger.info { "Mappers инициализированы." }
    }
}
