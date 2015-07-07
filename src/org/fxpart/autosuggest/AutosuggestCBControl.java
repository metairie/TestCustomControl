package org.fxpart.autosuggest;

import javafx.scene.control.Control;

/**
 * based on controlsfx code
 * <p>
 * Created by metairie on 07-Jul-15.
 */
public class AutosuggestCBControl extends Control {
    public AutosuggestCBControl() {
    }

    private String stylesheet;

    protected final String getUserAgentStylesheet(Class<?> clazz, String fileName) {
        if (stylesheet == null) {
            stylesheet = clazz.getResource(fileName).toExternalForm();
        }
        return stylesheet;
    }
}
