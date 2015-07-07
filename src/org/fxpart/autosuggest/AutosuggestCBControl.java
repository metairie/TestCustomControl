package org.fxpart.autosuggest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * based on controlsfx code
 * <p>
 * Created by metairie on 07-Jul-15.
 */
public class AutosuggestCBControl<T> extends Control {

    private ObservableList<T> items = FXCollections.observableArrayList();

    public AutosuggestCBControl() {
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new AutosuggestCBListSkin<>(this);
    }

    public void setLoadingIndicator(boolean b) {
        ((AutosuggestCBListSkin) getSkin()).setLoading(b);
    }

    public boolean getLoadingIndicator() {
        return ((AutosuggestCBListSkin) getSkin()).getLoading();
    }

    public ObservableList<T> getItems() {
        return items;
    }
}
