package com.neymeha.game.seeinggod.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion

class AnimationComponent(
    val animation: Animation<TextureRegion>,
    var stateTime: Float = 0f
) : Component
