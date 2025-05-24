package com.neymeha.game.seeinggod.ecs.utils

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g2d.Animation
import com.neymeha.game.seeinggod.ecs.components.EntityType
import com.badlogic.gdx.utils.Array as GdxArray // используем именно GDX Array

/**
 * Утилита для создания анимаций из текстурных спрайт-листов.
 */
object AnimationUtils {
    private val frameDuration by lazy { 0.2f }
    /**
     * Создаёт анимацию из переданной текстуры.
     *
     * @param texture текстура с кадрами (спрайт-лист)
     * @param frameCols количество колонок
     * @param frameRows количество строк
     * @param frameDuration длительность одного кадра
     *
     * @return Animation<TextureRegion> — корректный для использования в AnimationComponent
     */
    private fun createAnimation(
        texture: Texture,
        frameCols: Int,
        frameRows: Int,
    ): Animation<TextureRegion> {
        val tmp = TextureRegion.split(
            texture,
            texture.width / frameCols,
            texture.height / frameRows
        )

        val frames = GdxArray<TextureRegion>(frameCols * frameRows)
        for (row in 0 until frameRows) {
            for (col in 0 until frameCols) {
                frames.add(tmp[row][col])
            }
        }

        return Animation(frameDuration, frames)
    }

    private fun calculateFrameColsRows(texture: Texture, entityType: EntityType): Pair<Int, Int> {
        val baseSize = entityType.baseSize
        val cols = texture.width / baseSize
        val rows = texture.height / baseSize
        return Pair(cols.toInt(), rows.toInt())
    }

    fun createAnimationForEntityType(
        texture: Texture,
        entityType: EntityType
    ): Animation<TextureRegion> {
        val (cols, rows) = calculateFrameColsRows(texture, entityType)
        return createAnimation(texture, cols, rows)
    }

}
