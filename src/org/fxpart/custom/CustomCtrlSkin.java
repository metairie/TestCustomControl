package org.fxpart.custom;

import com.sun.javafx.scene.control.skin.LabeledSkinBase;

import java.util.ArrayList;

public class CustomCtrlSkin extends LabeledSkinBase<CustomCtrl, CustomCtrlBehavior> {
    protected CustomCtrlSkin(CustomCtrl control) {
        super(control, new CustomCtrlBehavior(control, new ArrayList()));
    }
}
