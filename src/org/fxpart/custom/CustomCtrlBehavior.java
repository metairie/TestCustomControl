package org.fxpart.custom;

import com.sun.javafx.scene.control.behavior.ButtonBehavior;

import java.util.List;

public class CustomCtrlBehavior extends ButtonBehavior<CustomCtrl> {
    public CustomCtrlBehavior(CustomCtrl control, List list) {
        super(control, list);
    }
}
