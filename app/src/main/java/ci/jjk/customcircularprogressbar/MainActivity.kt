package ci.jjk.customcircularprogressbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ci.jjk.customcircularprogressbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.load.setOnClickListener {
            val maxValue=100
            val minValue=1
            val progresVal=((Math.random() * (maxValue -minValue))-minValue).toFloat()
            binding.customProgressBar.setProgress(progresVal)
            binding.customProgressBar.setProgressWithAnimationAndMax(progresVal,maxValue.toFloat())
        }
    }
}