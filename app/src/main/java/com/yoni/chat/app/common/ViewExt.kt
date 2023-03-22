package com.yoni.chat.app.common

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener

private const val DEFAULT_ANIMATION_DURATION = 500L

fun View.fadeIn(
    visibilityAtEnd: Int = View.VISIBLE,
    duration: Long = DEFAULT_ANIMATION_DURATION
)  {
    alphaAnimation(0f, 1f, visibilityAtEnd, duration)
}
fun View.fadeOut(
    visibilityAtEnd: Int = View.GONE,
    duration: Long = DEFAULT_ANIMATION_DURATION
) {
    alphaAnimation(1f, 0f, visibilityAtEnd, duration)
}

fun View.alphaAnimation(
    from: Float,
    to: Float,
    visibilityAtEnd: Int,
    duration: Long
) {
    visibility = View.VISIBLE
    val alphaAnimation = AlphaAnimation(from, to)
    alphaAnimation.duration = duration

    alphaAnimation.setAnimationListener(object: AnimationListener {
        override fun onAnimationStart(animation: Animation?) {
        }

        override fun onAnimationEnd(animation: Animation?) {
            visibility = visibilityAtEnd
        }

        override fun onAnimationRepeat(animation: Animation?) {
        }

    })

    startAnimation(alphaAnimation)
}