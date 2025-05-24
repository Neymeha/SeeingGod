package com.neymeha.game.seeinggod.screens

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.neymeha.game.seeinggod.ecs.factory.EntityFactory
import com.neymeha.game.seeinggod.ecs.systems.*
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely

/**
 * Главный экран игры. Отвечает за инициализацию ECS-движка, систем и стартовых сущностей.
 * Логика отрисовки полностью вынесена в RenderSystem.
 */
class FirstScreen : KtxScreen {

    private val engine = Engine()
    private val batch = SpriteBatch()

    init {
        Log.logger.info { "Инициализация FirstScreen..." }

        setupSystems()
        createInitialEntities()

        Log.logger.info { "FirstScreen инициализирован." }
    }

    /**
     * Добавляет все необходимые системы в движок.
     */
    private fun setupSystems() {
        Log.logger.info { "Добавление ECS-систем..." }
        engine.addSystem(ClickSystem())
        engine.addSystem(DamageSystem())
        engine.addSystem(SpawnSystem(engine))
        engine.addSystem(RenderSystem(batch)) // Отрисовка теперь через отдельную систему
    }

    /**
     * Создание стартовых сущностей через фабрику.
     */
    private fun createInitialEntities() {
        val enemy = EntityFactory.createEnemy(100)
        engine.addEntity(enemy)
        Log.logger.info { "Создан и добавлен первый враг: $enemy" }

        // Также здесь можно создавать других персонажей по желанию
        // val player = EntityFactory.createPlayer()
        // engine.addEntity(player)
    }

    /**
     * Основной цикл рендера: обновляет ECS-движок. Очистка экрана и отрисовка происходит внутри RenderSystem.
     */
    override fun render(delta: Float) {
        clearScreen(0.2f, 0.2f, 0.2f, 1f) // Тёмно-серый фон
        engine.update(delta)
    }

    /**
     * Очистка ресурсов.
     */
    override fun dispose() {
        Log.logger.info { "Освобождение ресурсов FirstScreen..." }
        batch.disposeSafely()
    }
}
