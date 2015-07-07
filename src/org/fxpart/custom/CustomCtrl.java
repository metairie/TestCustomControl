package org.fxpart.custom;

import com.sun.javafx.scene.traversal.ParentTraversalEngine;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WritableValue;
import javafx.css.PseudoClass;
import javafx.css.StyleableProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.AccessibleAttribute;
import javafx.scene.AccessibleRole;
import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Skin;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

/**
 * metairie 26.06.2015
 */
public class CustomCtrl extends ButtonBase implements Toggle {

    private static final String DEFAULT_STYLE_CLASS = "star-button";
    private static final PseudoClass PSEUDO_CLASS_SELECTED = PseudoClass.getPseudoClass("selected");

    public CustomCtrl() {
        initialize();
    }

    public CustomCtrl(String text) {
        setText(text);
        initialize();
    }

    /**
     * Creates a button with the specified text and icon for its label.
     *
     * @param text    A text string for its label.
     * @param graphic the icon for its label.
     */
    public CustomCtrl(String text, Node graphic) {
        setText(text);
        setGraphic(graphic);
        initialize();
    }

    private void initialize() {
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);
        setAccessibleRole(AccessibleRole.TOGGLE_BUTTON);
        // alignment is styleable through css. Calling setAlignment
        // makes it look to css like the user set the value and css will not
        // override. Initializing alignment by calling set on the
        // CssMetaData ensures that css will be able to override the value.
        ((StyleableProperty<Pos>) (WritableValue<Pos>) alignmentProperty()).applyStyle(null, Pos.CENTER);
        setMnemonicParsing(true);     // enable mnemonic auto-parsing by default
    }

    /***************************************************************************
     *                                                                         *
     * Properties                                                              *
     *                                                                         *
     **************************************************************************/
    /**
     * Indicates whether this toggle button is selected. This can be manipulated
     * programmatically.
     */
    private BooleanProperty selected;

    public final void setSelected(boolean value) {
        selectedProperty().set(value);
    }

    public final boolean isSelected() {
        return selected == null ? false : selected.get();
    }

    public final BooleanProperty selectedProperty() {
        if (selected == null) {
            selected = new BooleanPropertyBase() {
                @Override
                protected void invalidated() {
                    final boolean selected = get();
                    final ToggleGroup tg = getToggleGroup();
                    // Note: these changes need to be done before selectToggle/clearSelectedToggle since
                    // those operations change properties and can execute user code, possibly modifying selected property again
                    pseudoClassStateChanged(PSEUDO_CLASS_SELECTED, selected);
                    notifyAccessibleAttributeChanged(AccessibleAttribute.SELECTED);
                    if (tg != null) {
                        if (selected) {
                            tg.selectToggle(CustomCtrl.this);
                        } else if (tg.getSelectedToggle() == CustomCtrl.this) {
                            // HACK clearSelectedToggle is not public outside package ...
                            // tg.clearSelectedToggle();
                            for (Toggle toggle : tg.getToggles()) {
                                if (toggle.isSelected()) {
                                    return;
                                }
                            }
                            tg.selectToggle(null);
                            // end HACK
                        }
                    }
                }

                @Override
                public Object getBean() {
                    return CustomCtrl.this;
                }

                @Override
                public String getName() {
                    return "selected";
                }
            };
        }
        return selected;
    }

    /**
     * The {@link ToggleGroup} to which this {@code CustomCtrl} belongs. A
     * {@code CustomCtrl} can only be in one group at any one time. If the
     * group is changed, then the button is removed from the old group prior to
     * being added to the new group.
     */
    private ObjectProperty<ToggleGroup> toggleGroup;

    public final void setToggleGroup(ToggleGroup value) {
        toggleGroupProperty().set(value);
    }

    public final ToggleGroup getToggleGroup() {
        return toggleGroup == null ? null : toggleGroup.get();
    }

    public final ObjectProperty<ToggleGroup> toggleGroupProperty() {
        if (toggleGroup == null) {
            toggleGroup = new ObjectPropertyBase<ToggleGroup>() {
                private ToggleGroup old;
                private ChangeListener<Toggle> listener = (o, oV, nV) -> getImpl_traversalEngine().setOverriddenFocusTraversability(nV != null ? isSelected() : null);

                @Override
                protected void invalidated() {
                    final ToggleGroup tg = get();
                    if (tg != null && !tg.getToggles().contains(CustomCtrl.this)) {
                        if (old != null) {
                            old.getToggles().remove(CustomCtrl.this);
                        }
                        tg.getToggles().add(CustomCtrl.this);
                        final ParentTraversalEngine parentTraversalEngine = new ParentTraversalEngine(CustomCtrl.this);
                        setImpl_traversalEngine(parentTraversalEngine);
                        // If there's no toggle selected, do not override
                        parentTraversalEngine.setOverriddenFocusTraversability(tg.getSelectedToggle() != null ? isSelected() : null);
                        tg.selectedToggleProperty().addListener(listener);
                    } else if (tg == null) {
                        old.selectedToggleProperty().removeListener(listener);
                        old.getToggles().remove(CustomCtrl.this);
                        setImpl_traversalEngine(null);
                    }

                    old = tg;
                }

                @Override
                public Object getBean() {
                    return CustomCtrl.this;
                }

                @Override
                public String getName() {
                    return "toggleGroup";
                }
            };
        }
        return toggleGroup;
    }

    /***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/

    /**
     * {@inheritDoc}
     */
    @Override
    public void fire() {
        if (!isDisabled()) {
            setSelected(!isSelected());
            fireEvent(new ActionEvent());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Skin<?> createDefaultSkin() {
        return new CustomCtrlSkin(this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return CustomCtrl.class.getResource("customctrl.css").toExternalForm();
    }


    /***************************************************************************
     *                                                                         *
     * Stylesheet Handling                                                     *
     *                                                                         *
     **************************************************************************/

    /**
     * Not everything uses the default value of false for alignment.
     * This method provides a way to have them return the correct initial value.
     *
     * @treatAsPrivate implementation detail
     */
    @Deprecated
    @Override
    protected Pos impl_cssGetAlignmentInitialValue() {
        return Pos.CENTER;
    }

    /***************************************************************************
     * *
     * Accessibility handling                                                  *
     * *
     **************************************************************************/

    @Override
    public Object queryAccessibleAttribute(AccessibleAttribute attribute, Object... parameters) {
        switch (attribute) {
            case SELECTED:
                return isSelected();
            default:
                return super.queryAccessibleAttribute(attribute, parameters);
        }
    }
}
