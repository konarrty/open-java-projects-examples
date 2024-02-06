package com.example.client.factory;

import javax.swing.*;
import java.util.Collection;

public class ComboBoxModelFactory {
    public static <T> DefaultComboBoxModel<T> createComboBoxModel(Collection<T> collection) {
        DefaultComboBoxModel<T> comboBoxModel = new DefaultComboBoxModel<>();

        if (collection != null)
            comboBoxModel.addAll(collection);

        return comboBoxModel;
    }
}
