package org.fxpart.autosuggest;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.behavior.KeyBinding;
import com.sun.javafx.scene.control.skin.BehaviorSkinBase;
import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.fxpart.combobox.AutosuggestComboBoxList;

import java.util.Collections;

/**
 * Created by metairie on 07-Jul-15.
 */
public class AutosuggestCBListSkin<T> extends BehaviorSkinBase<AutosuggestCBList<T>, BehaviorBase<AutosuggestCBList<T>>> {

    /**************************************************************************
     * fields
     **************************************************************************/

    // visuals
    private final HBox root;
    private final VBox vtext;
    private final VBox vcombo;
    private final ComboBox<T> comboBox;
    private final AutosuggestComboBoxList<T> ascbl;

    private final TextField selectedItem;
    private final ProgressBar loadingIndicator;

    // data
    private final AutosuggestCBList<T> control;
    private final ObservableList<T> items;

    /**************************************************************************
     * Constructors
     **************************************************************************/
    public AutosuggestCBListSkin(final AutosuggestCBList<T> control) {
        super(control, new BehaviorBase<>(control, Collections.<KeyBinding>emptyList()));
        this.control = control;
        items = control.getItems();
        root = new HBox();
        vcombo = new VBox();
        vtext = new VBox();
        loadingIndicator = new ProgressBar();
        selectedItem = new TextField("");
        ascbl = new AutosuggestComboBoxList<>();
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
        // Insets(double top, double right, double bottom, double left)
        root.setStyle("-fx-background-color: #336699;");
        root.setPadding(new Insets(1, 1, 1, 1));

        vcombo.setStyle("-fx-background-color: #FFFFBB;");
        vcombo.setPadding(new Insets(1, 1, 1, 1));
        loadingIndicator.setMaxWidth(Double.MAX_VALUE);
        vcombo.getChildren().add(loadingIndicator);
        vcombo.getChildren().add(ascbl);

        vtext.setStyle("-fx-background-color: #AAFFBB;");
        vtext.setPadding(new Insets(6, 1, 0, 1));
        selectedItem.setVisible(true);
        selectedItem.setMaxHeight(Double.MAX_VALUE);
        vtext.getChildren().add(selectedItem);

        root.getChildren().add(vtext);
        root.getChildren().add(vcombo);
        getChildren().add(root);
    }
}
