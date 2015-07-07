package org.fxpart.autosuggest;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.behavior.KeyBinding;
import com.sun.javafx.scene.control.skin.BehaviorSkinBase;
import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Collections;

/**
 * Created by metairie on 07-Jul-15.
 */
public class AutosuggestComboBoxListSkin<T> extends BehaviorSkinBase<AutosuggestComboBoxList<T>, BehaviorBase<AutosuggestComboBoxList<T>>> {

    /**************************************************************************
     * fields
     **************************************************************************/

    // visuals
    private final VBox vroot;
    private final HBox htexts;
    private final ComboBox<T> comboBox;
    private final TextField selectedItem;
    private final ProgressBar loadingIndicator;

    // data
    private final AutosuggestComboBoxList<T> control;
    private final ObservableList<T> items;

    /**************************************************************************
     * Constructors
     **************************************************************************/
    public AutosuggestComboBoxListSkin(final AutosuggestComboBoxList<T> control) {
        super(control, new BehaviorBase<>(control, Collections.<KeyBinding>emptyList()));
        this.control = control;
        items = control.getItems();
        vroot = new VBox();
        htexts = new HBox();
        loadingIndicator = new ProgressBar();
        selectedItem = new TextField("");
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
        comboBox.setEditable(true);

        // build control up
        htexts.setPrefWidth(300);
        htexts.getChildren().add(selectedItem);
        htexts.getChildren().add(comboBox);
        vroot.setPrefWidth(htexts.getWidth());
        vroot.getChildren().add(loadingIndicator);
        vroot.getChildren().add(htexts);
        getChildren().add(vroot);
    }

}
