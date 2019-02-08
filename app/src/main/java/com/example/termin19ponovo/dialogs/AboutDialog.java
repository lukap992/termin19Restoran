package com.example.termin19ponovo.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AboutDialog extends AlertDialog.Builder {

    public AboutDialog(Context context){
        super(context);

        setTitle("This is ABOUT DIALOG");
        setMessage("DO YOU WANT TO ORDER FOOD");
        setCancelable(false);

        setPositiveButton("YES I WANT TO ORDER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        setNegativeButton("NO I DONT WANT TO ORDER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

    }
    public AlertDialog prepareDialog(){
        AlertDialog dialog = create();
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }
}
