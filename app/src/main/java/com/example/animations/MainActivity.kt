package com.example.animations

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.example.animations.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvOpen.setOnClickListener {
            openCard(it)
        }

        binding.tvClose.setOnClickListener {
            binding.tvOpen.apply {
                animate()
                    .rotation(ROTATE_ANIM_PING)
                    .setDuration(PING_DURATION)
                    .setInterpolator(DecelerateInterpolator())
                    .withEndAction {
                        animate()
                            .rotation(ROTATE_ANIM_END)
                            .setDuration(PING_DURATION)
                            .setInterpolator(DecelerateInterpolator())
                            .start()
                    }
                    .start()
            }
        }
    }

    private fun openCard(view: View) {
        val rotate = if (!isOpen) {
            ObjectAnimator.ofFloat(view, "rotation", ROTATE_ANIM_START, ROTATE_ANIM_END).apply {
                duration = ROTATE_DURATION
            }
        } else {
            ObjectAnimator.ofFloat(view, "rotation", ROTATE_ANIM_END, ROTATE_ANIM_START).apply {
                duration = ROTATE_DURATION
            }
        }

        val pivotX = ObjectAnimator.ofFloat(view, "pivotX", ROTATE_ANIM_START)
        val pivotY = ObjectAnimator.ofFloat(view, "pivotY", view.height.toFloat())
        isOpen = !isOpen

        AnimatorSet().apply {
            playTogether(rotate, pivotX, pivotY)
            start()
        }
    }

    companion object {
        const val PING_DURATION = 100L
        const val ROTATE_DURATION = 1000L
        const val ROTATE_ANIM_START = 0.0f
        const val ROTATE_ANIM_PING = -60.0f
        const val ROTATE_ANIM_END = -75.0f
    }
}