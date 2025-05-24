package com.neymeha.game.seeinggod.ecs.factory

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Texture
import com.neymeha.game.seeinggod.ecs.components.*
import com.neymeha.game.seeinggod.ecs.utils.AnimationUtils

/**
 * EntityFactory — фабрика для создания игровых сущностей.
 * Сущности создаются с различными компонентами:
 * - HealthComponent, PositionComponent, ClickComponent, AttackComponent и др.
 * - EntityTypeComponent определяет тип сущности.
 */
object EntityFactory {
    private val heroTexture by lazy { Texture("hero.png") }
    private val enemyTexture by lazy { Texture("enemy.png") }
    private val bossTexture by lazy { Texture("boss.png") }
    private val goldTexture by lazy { Texture("gold.png") }

    /**
     * Создает врага с заданным максимальным здоровьем.
     */

    fun createEnemy(maxHealth: Long): Entity {
        val type = EntityType.ENEMY
        val size = type.baseSize

        val enemy = Entity()
        enemy.add(PositionComponent(100f, 100f, size, size))
        enemy.add(HealthComponent(maxHealth, maxHealth))
        enemy.add(ClickComponent())
        enemy.add(EntityTypeComponent(type))

        val animation = AnimationUtils.createAnimationForEntityType(enemyTexture, type)
        enemy.add(AnimationComponent(animation))

        return enemy
    }

    /**
     * Создает босса с заданным максимальным здоровьем.
     */
    fun createBoss(maxHealth: Long): Entity {
        val type = EntityType.BOSS
        val size = type.baseSize

        val boss = Entity()
        boss.add(PositionComponent(200f, 100f, size, size))
        boss.add(HealthComponent(maxHealth, maxHealth))
        boss.add(ClickComponent())
        boss.add(EntityTypeComponent(type))

        val animation = AnimationUtils.createAnimationForEntityType(bossTexture, type)
        boss.add(AnimationComponent(animation))

        return boss
    }

    /**
     * Создает список золотых сущностей.
     *
     * @param count Количество сущностей золота
     * @param value Стоимость одной единицы
     */
    fun createGold(count: Int, value: Long): List<Entity> {
        val type = EntityType.GOLD
        val size = type.baseSize

        return List(count) {
            val gold = Entity()
            gold.add(PositionComponent(200f, 200f, size, size))
            gold.add(HealthComponent(10, 10))
            gold.add(GoldComponent(value))
            gold.add(ClickComponent())
            gold.add(EntityTypeComponent(type))

            val animation = AnimationUtils.createAnimationForEntityType(goldTexture, type)
            gold.add(AnimationComponent(animation))

            gold
        }
    }

    /**
     * Создает игрока.
     */
    fun createPlayer(): Entity {
        val type = EntityType.PLAYER
        val size = type.baseSize

        val player = Entity()
        player.add(PositionComponent(100f, 150f, size, size))
        player.add(AttackComponent(10))
        player.add(GoldComponent(0))
        player.add(EntityTypeComponent(type))

        val animation = AnimationUtils.createAnimationForEntityType(heroTexture, type)
        player.add(AnimationComponent(animation))

        return player
    }

    /**
     * Создает спутника (companion).
     */
    fun createCompanion(): Entity {
        val type = EntityType.COMPANION
        val size = type.baseSize

        val companion = Entity()
        companion.add(PositionComponent(110f, 150f, size, size))
        companion.add(AttackComponent(3))
        companion.add(EntityTypeComponent(type))

        val animation = AnimationUtils.createAnimationForEntityType(heroTexture, type)
        companion.add(AnimationComponent(animation))

        return companion
    }

    /**
     * Создает обслуживающего помощника (attendant).
     */
    fun createAttendant(): Entity {
        val type = EntityType.ATTENDANT
        val size = type.baseSize

        val attendant = Entity()
        attendant.add(PositionComponent(120f, 150f, size, size))
        attendant.add(AttackComponent(5))
        attendant.add(EntityTypeComponent(type))

        val animation = AnimationUtils.createAnimationForEntityType(heroTexture, type)
        attendant.add(AnimationComponent(animation))

        return attendant
    }
}
