package com.example.gsyvideoplayer.simple;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.gsyvideoplayer.R;

public class FragmentDialog extends DialogFragment {
    String textContent;

    TextView TextView;

    FragmentDialog(String textContent){
        this.textContent=textContent;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialog_anno, null);

        // 如果你想在 Java 代码中设置内容或监听点击
        view.findViewById(R.id.btn_confirm).setOnClickListener(v -> dismiss());
        TextView=view.findViewById(R.id.tv_announcement);
        TextView.setText(textContent);

        return new AlertDialog.Builder(requireContext())
            .setView(view)
            .create();
    }

    public  void setTextContent(String content){
        this.textContent=content;

    }
}
