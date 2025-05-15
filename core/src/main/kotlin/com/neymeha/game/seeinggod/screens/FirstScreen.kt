package com.neymeha.game.seeinggod.screens

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.neymeha.game.seeinggod.ecs.components.EntityType
import com.neymeha.game.seeinggod.ecs.components.PositionComponent
import com.neymeha.game.seeinggod.ecs.factory.EntityFactory
import com.neymeha.game.seeinggod.ecs.systems.*
import com.neymeha.game.seeinggod.ecs.utils.Mappers
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.graphics.use

/**
 * Главный экран игры. Здесь инициализируются ECS-системы, создаются сущности и обрабатывается рендеринг.
 */
class FirstScreen : KtxScreen {

    private val engine = Engine()
    private val batch = SpriteBatch()

    // Используем карту текстур по типу сущностей
    private val textures: Map<EntityType, Texture> = mapOf(
        EntityType.ENEMY to Texture("enemy.png"),
//        EntityType.BOSS to Texture("boss.png"),
        EntityType.GOLD to Texture("gold.png"),
//        EntityType.PLAYER to Texture("player.png"),
//        EntityType.COMPANION to Texture("companion.png"),
//        EntityType.ATTENDANT to Texture("attendant.png")
    )

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
        Log.logger.info { "Добавление систем..." }
        engine.addSystem(ClickSystem())
        engine.addSystem(DamageSystem())
        engine.addSystem(SpawnSystem(engine))
    }

    /**
     * Создает стартовые сущности.
     */
    private fun createInitialEntities() {
        val enemy = EntityFactory.createEnemy(100)
        engine.addEntity(enemy)
        Log.logger.info { "Создан и добавлен первый враг: $enemy" }
    }

    /**
     * Рендер кадра. Очищает экран, обновляет ECS и отрисовывает сущности с позициями.
     */
    override fun render(delta: Float) {
        clearScreen(red = 0.2f, green = 0.2f, blue = 0.2f)

        engine.update(delta)

        batch.use {
            engine.entities.forEach { entity ->
                val pos: PositionComponent = Mappers.position.get(entity) ?: return@forEach
                val type = Mappers.type.get(entity) ?: return@forEach

                val texture = textures[type.type]
                if (texture == null) {
                    Log.logger.warn { "Нет текстуры для типа: ${type.type}" }
                    return@forEach
                }

                it.draw(texture, pos.x, pos.y, pos.width, pos.height)
            }
        }
    }

    /**
     * Очистка ресурсов — вызывается при уничтожении экрана.
     */
    override fun dispose() {
        Log.logger.info { "Освобождение ресурсов FirstScreen..." }

        textures.values.forEach { it.disposeSafely() }
        batch.disposeSafely()

        Log.logger.info { "Ресурсы FirstScreen успешно освобождены." }
    }
}
