package com.neymeha.game.seeinggod

import com.neymeha.game.seeinggod.screens.FirstScreen
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.async.KtxAsync

/**
 * Главный класс игры — точка входа.
 * Наследуется от [KtxGame] с типом экранов [KtxScreen].
 *
 * Используем KTX-подход для лаконичного и модульного управления экранами.
 */
class SeeingGod : KtxGame<KtxScreen>() {

    /**
     * Метод create() вызывается один раз при запуске игры.
     * Здесь инициализируется всё необходимое для старта.
     */
    override fun create() {
        Log.logger.info { "Игра запускается..." }
        // Инициализация корутин (KTX-обёртка над kotlinx.coroutines)
        // Позволяет использовать асинхронные задачи в игре (например, загрузку ресурсов)
        KtxAsync.initiate()

        // Регистрируем экран в менеджере экранов
        // Это позволяет переключаться между экранами по имени класса
        addScreen(FirstScreen())

        // Устанавливаем экран, который будет отображаться при запуске
        setScreen<FirstScreen>()
    }
}
