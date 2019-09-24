package com.app.learnquizjp.base

internal class MyBounceInterpolator(amplitude: Double, frequency: Double) : android.view.animation.Interpolator {
    private var mAmplitude : Double = 1.0
    private var mFrequency : Double = 10.0

    init {
        mAmplitude = amplitude
        mFrequency = frequency
    }

    override fun getInterpolation(time : Float): Float {
        var amplitude = mAmplitude
        if (amplitude == 0.0) amplitude = 0.05

        // The interpolation curve equation:
        //    -e^(-time / amplitude) * cos(frequency * time) + 1
        //
        // View the graph live: https://www.desmos.com/calculator/6gbvrm5i0s
        return (-1.0 * Math.pow(Math.E, -time / mAmplitude) * Math.cos(mFrequency * time) + 1).toFloat()
    }
}