package com.neymeha.game.seeinggod.screens

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.neymeha.game.seeinggod.ecs.components.PositionComponent
import com.neymeha.game.seeinggod.ecs.factory.EntityFactory
import com.neymeha.game.seeinggod.ecs.systems.*
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.graphics.use

/**
 * Главный экран игры, где разворачивается логика MVP.
 * Использует Ashley ECS-движок и KTX для удобства.
 */
class FirstScreen : KtxScreen {
    // Основной движок ECS. Управляет сущностями и системами.
    private val engine = Engine()

    // Спрайтбатч для рендера врага (временно, пока нет полноценного рендера)
    private val batch = SpriteBatch()

    // Текстура-заглушка для отрисовки врага (временно)
    private val placeholderEnemyTexture = Texture("logo.png")

    init {
        Log.logger.info { "Инициализация экрана FirstScreen..." }
        // Добавляем все системы, реализующие логику MVP:
        // 1. ClickSystem — обрабатывает клики игрока
        // 2. DamageSystem — применяет урон существам
        // 3. SpawnSystem — создает нового врага при смерти
        engine.addSystem(ClickSystem())
        engine.addSystem(DamageSystem())
        engine.addSystem(SpawnSystem(engine))

        // Создаем первого врага (через EnemyFactory)
        engine.addEntity(EntityFactory.createEnemy(100))
        Log.logger.info { "Враг создан и добавлен в движок." }
    }

    /**
     * Вызывается каждый кадр. Отвечает за:
     * 1. Обновление логики через ECS-системы
     * 2. Рендеринг временной текстуры врага по позиции
     */
    override fun render(delta: Float) {
        // Очищаем экран перед рендерингом
        clearScreen(red = 0.2f, green = 0.2f, blue = 0.2f)

        // Обновляем ECS-движок — вызывает все активные системы
        engine.update(delta)

        // Временно отрисовываем текстуру на позиции врага
        batch.use {
            engine.entities.forEach { entity ->
                // Получаем компонент позиции (если есть)
                val pos: PositionComponent = entity.components.find { component -> component is PositionComponent }
                    as? PositionComponent ?: return@forEach

                // Отрисовываем врага по его позиции и размеру
                it.draw(placeholderEnemyTexture, pos.x, pos.y, pos.width, pos.height)
            }
        }
    }

    /**
     * Очистка ресурсов, вызывается при уничтожении экрана
     */
    override fun dispose() {
        Log.logger.info { "Освобождение ресурсов..." }
        placeholderEnemyTexture.disposeSafely()
        batch.disposeSafely()
    }
}
