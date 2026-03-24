/*
 * Clover - 4chan browser https://github.com/Floens/Clover/
 * Copyright (C) 2014  Floens
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.floens.chan.ui.controller;

import android.content.Context;
import android.view.ViewGroup;

import org.floens.chan.R;
import org.floens.chan.controller.Controller;
import org.floens.chan.controller.ControllerTransition;
import org.floens.chan.controller.ui.NavigationControllerContainerLayout;
import org.floens.chan.core.settings.ChanSettings;
import org.floens.chan.ui.theme.ThemeHelper;

public class StyledToolbarNavigationController extends ToolbarNavigationController {
    public StyledToolbarNavigationController(Context context) {
        super(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        view = inflateRes(R.layout.controller_navigation_toolbar);
        container = (NavigationControllerContainerLayout) view.findViewById(R.id.container);
        NavigationControllerContainerLayout nav = (NavigationControllerContainerLayout) container;
        nav.setNavigationController(this);
        nav.setSwipeEnabled(ChanSettings.controllerSwipeable.get());
        toolbar = view.findViewById(R.id.toolbar);
        if (ThemeHelper.getInstance().getTheme().isMaterialYou()) {
            android.util.TypedValue tv = new android.util.TypedValue();
            context.getTheme().resolveAttribute(com.google.android.material.R.attr.colorPrimary, tv, true);
            toolbar.setBackgroundColor(tv.data);
        } else {
            toolbar.setBackgroundColor(ThemeHelper.getInstance().getTheme().primaryColor.color);
        }
        toolbar.setCallback(this);

        // Edge-to-edge: extend toolbar behind status bar, pad its content down
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        if (statusBarHeight > 0) {
            toolbar.setPadding(toolbar.getPaddingLeft(), statusBarHeight, toolbar.getPaddingRight(), toolbar.getPaddingBottom());
            ViewGroup.LayoutParams lp = toolbar.getLayoutParams();
            lp.height = context.getResources().getDimensionPixelSize(R.dimen.toolbar_height) + statusBarHeight;
            toolbar.setLayoutParams(lp);
        }
    }

    @Override
    public boolean popController(ControllerTransition controllerTransition) {
        return !toolbar.isTransitioning() && super.popController(controllerTransition);

    }

    @Override
    public boolean pushController(Controller to, ControllerTransition controllerTransition) {
        return !toolbar.isTransitioning() && super.pushController(to, controllerTransition);
    }

    @Override
    public void transition(Controller from, Controller to, boolean pushing, ControllerTransition controllerTransition) {
        super.transition(from, to, pushing, controllerTransition);

        if (to != null) {
            DrawerController drawerController = getDrawerController();
            if (drawerController != null) {
                drawerController.setDrawerEnabled(to.navigation.hasDrawer);
            }
        }
    }

    @Override
    public void endSwipeTransition(Controller from, Controller to, boolean finish) {
        super.endSwipeTransition(from, to, finish);

        if (finish) {
            DrawerController drawerController = getDrawerController();
            if (drawerController != null) {
                drawerController.setDrawerEnabled(to.navigation.hasDrawer);
            }
        }
    }

    @Override
    public boolean onBack() {
        if (super.onBack()) {
            return true;
        } else if (parentController instanceof PopupController && childControllers.size() == 1) {
            ((PopupController) parentController).dismiss();
            return true;
        } else if (doubleNavigationController != null && childControllers.size() == 1) {
            if (doubleNavigationController.getRightController() == this) {
                doubleNavigationController.setRightController(null);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void onMenuClicked() {
        DrawerController drawerController = getDrawerController();
        if (drawerController != null) {
            drawerController.onMenuClicked();
        }
    }

    private DrawerController getDrawerController() {
        if (parentController instanceof DrawerController) {
            return (DrawerController) parentController;
        } else if (doubleNavigationController != null) {
            Controller doubleNav = (Controller) doubleNavigationController;
            if (doubleNav.parentController instanceof DrawerController) {
                return (DrawerController) doubleNav.parentController;
            }
        }
        return null;
    }
}
