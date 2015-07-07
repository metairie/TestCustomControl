package org.fxpart.autosuggest;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.behavior.KeyBinding;
import com.sun.javafx.scene.control.skin.BehaviorSkinBase;
import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.util.Collections;

/**
 * Created by metairie on 07-Jul-15.
 */
public class AutosuggestComboBoxListSkin<T> extends BehaviorSkinBase<AutosuggestComboBoxList<T>, BehaviorBase<AutosuggestComboBoxList<T>>> {

    /**************************************************************************
     * fields
     **************************************************************************/

    // visuals
    private final ComboBox<T> comboBox;

    // data
    private final AutosuggestComboBoxList<T> control;
    private final ObservableList<T> items;

    /**************************************************************************
     * Constructors
     **************************************************************************/
    public AutosuggestComboBoxListSkin(final AutosuggestComboBoxList<T> control) {
        super(control, new BehaviorBase<>(control, Collections.<KeyBinding>emptyList()));

        this.control = control;
        this.items = control.getItems();

        comboBox = new ComboBox<T>(items) {
            @Override
            protected javafx.scene.control.Skin<?> createDefaultSkin() {
                return new ComboBoxListViewSkin<T>(this) {
                    // overridden to prevent the popup from disappearing
                    @Override
                    protected boolean isHideOnClickEnabled() {
                        return false;
                    }
                };
            }
        };
        comboBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        getChildren().add(comboBox);
    }

}
