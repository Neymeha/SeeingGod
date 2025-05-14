package com.neymeha.game.seeinggod.ecs.components

import com.badlogic.ashley.core.Component

/**
 * Компонент, описывающий позицию и размеры сущности.
 * Этот компонент необходим для определения местоположения сущности на экране и ее размеров.
 *
 * @property x Координата X (горизонтальная позиция) сущности на экране.
 * @property y Координата Y (вертикальная позиция) сущности на экране.
 * @property width Ширина сущности, используемая для отрисовки и проверки столкновений.
 * @property height Высота сущности, используемая для отрисовки и проверки столкновений.
 */
class PositionComponent(
    var x: Float,   // Позиция по оси X
    var y: Float,   // Позиция по оси Y
    var width: Float,   // Ширина сущности
    var height: Float   // Высота сущности
) : Component
