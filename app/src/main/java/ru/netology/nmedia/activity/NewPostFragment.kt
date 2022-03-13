package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.PostViewModel
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.LongArg
import ru.netology.nmedia.util.StringArg

class NewPostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(layoutInflater)
        arguments?.textArg?.let {
            binding.content.setText(it)
        }

        val viewModel: PostViewModel by viewModels(::requireParentFragment)

        binding.content.requestFocus()
        binding.save.setOnClickListener {
            if (!binding.content.text.isNullOrBlank()) {
                val content = binding.content.text.toString()
                viewModel.editContent(content)
                viewModel.save()
            }
            findNavController().navigateUp()
        }
        binding.cancel.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

    companion object {
        var Bundle.textArg: String? by StringArg
        var Bundle.longArg: Long? by LongArg
    }

    class Contract : ActivityResultContract<String, String?>() {

        override fun createIntent(context: Context, input: String): Intent {
            return Intent(context, NewPostFragment::class.java).apply {
                putExtra(Intent.EXTRA_TEXT, input) }
    }
        override fun parseResult(resultCode: Int, intent: Intent?): String? =
            if (resultCode == Activity.RESULT_OK) {
                intent?.getStringExtra(Intent.EXTRA_TEXT)
            } else {
                null
            }
    }

}