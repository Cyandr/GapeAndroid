package com.gape.ide.working.instructions;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.Spinner;
import com.gape.cyandr.gapeandroid.gape.R;


/**
 * Created by cyandr on 2016/10/24.
 */
public class VariableWindow extends Dialog {


    public String variable_name;
    public String variable_type;

    public VariableWindow(Context context) {
        super(context);
    }
    public VariableWindow(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        public Spinner spinner;
        private EditText editText;
        private View layout;
        private Context context;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setPositiveButton(DialogInterface.OnClickListener listener) {
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(DialogInterface.OnClickListener listener) {
            this.negativeButtonClickListener = listener;
            return this;
        }

        public String getVariableName() {
            if (editText.getText().toString().length() >= 2) {
                return editText.getText().toString();
            }
            return null;
        }


        public VariableWindow create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final VariableWindow dialog = new VariableWindow(context);
            layout = inflater.inflate(R.layout.variable_choices, null);
            editText = layout.findViewById(R.id.text_variable_name);
            spinner = layout.findViewById(R.id.spinner_variable_type);
            dialog.addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            if (positiveButtonClickListener != null) {
                layout.findViewById(R.id.btn_positive).setOnClickListener(v -> positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE));
            }
            if (negativeButtonClickListener != null) {
                layout.findViewById(R.id.btn_negtive).setOnClickListener(v -> negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE));
            }
            //dialog.setContentView(layout);
            return dialog;
        }
    }
}
