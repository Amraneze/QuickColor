package fr.justgame.quickcolor.common.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import fr.justgame.quickcolor.R;
import fr.justgame.quickcolor.common.ui.CommonButton;


/**
 * Created by Amrane Ait Zeouay on 12-May-18.
 */

public class BaseDialog extends DialogFragment {

    private static String TYPE_KEY = "DialogType";
    private static String TITLE_KEY = "Title_Key";
    private static String MESSAGE_KEY = "Message_Key";

    public static final int BUTTON_INDEX_0 = 0;
    public static final int BUTTON_INDEX_1 = 1;

    protected DialogListener dialogListener;

    public DialogListener getDialogListener() {
        return dialogListener;
    }

    public BaseDialog setDialogListener(DialogListener dialogListener) {
        this.dialogListener = dialogListener;
        return this;
    }

    public BaseDialog() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_confirm_dialog, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        view.setMinimumWidth(width);

        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvMessage = view.findViewById(R.id.tv_message);
        CommonButton positiveButton = view.findViewById(R.id.btn_positive_answer);
        //CommonButton negativeButton = (CommonButton) view.findViewById(R.id.btn_negative_answer);

        tvTitle.setText(getArguments().getString(TITLE_KEY));
        tvMessage.setText(getArguments().getString(MESSAGE_KEY));

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogListener != null) {
                    dialogListener.onButtonClicked(BUTTON_INDEX_1);
                    dismiss();
                }
            }
        });
//        negativeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (dialogListener != null) {
//                    dialogListener.onButtonClicked(BUTTON_INDEX_0);
//                    dismiss();
//                }
//            }
//        });
        return view;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
        manager.executePendingTransactions();
        getDialog().getWindow().getDecorView().setSystemUiVisibility(
                getActivity().getWindow().getDecorView().getSystemUiVisibility());
        //getDialog().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    public interface DialogListener {
        void onButtonClicked(int which);
    }

    public static BaseDialog newInstance(String title, String message){
        BaseDialog dialog = new BaseDialog();
        Bundle args = new Bundle();
        args.putString(TITLE_KEY, title);
        args.putString(MESSAGE_KEY, message);
        dialog.setArguments(args);
        return dialog;
    }

}
