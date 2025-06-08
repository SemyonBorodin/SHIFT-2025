package ru.cft.javaLessons.miner.view;

import javax.swing.*;
import java.awt.*;

public class RecordsWindow {
    private RecordNameListener nameListener;
    private final JDialog dialog;

    public RecordsWindow(JFrame parentFrame) {
        dialog = new JDialog(parentFrame, "New Record", true);

        JTextField nameField = new JTextField();

        dialog.setLayout(new GridLayout(3, 1));
        dialog.add(new JLabel("Enter your name:"));
        dialog.add(nameField);
        dialog.add(createOkButton(nameField));

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setPreferredSize(new Dimension(210, 120));
        dialog.setResizable(false);
        dialog.pack();
        dialog.setLocationRelativeTo(parentFrame);

    }

    public void setNameListener(RecordNameListener nameListener) {
        this.nameListener = nameListener;
    }

    private JButton createOkButton(JTextField nameField) {
        JButton button = new JButton("OK");
        button.addActionListener(e -> {

            if (nameListener != null) {
                nameListener.onRecordNameEntered(nameField.getText());
            }
            dialog.dispose();
        });
        return button;
    }

    public Window getDialog() {
        return dialog;
    }
}
