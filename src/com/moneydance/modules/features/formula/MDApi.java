package com.moneydance.modules.features.formula;

import com.infinitekind.moneydance.model.AccountBook;
import com.infinitekind.moneydance.model.Reminder;
import com.moneydance.apps.md.controller.FeatureModuleContext;
import com.moneydance.apps.md.view.gui.MoneydanceGUI;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MDApi {

    public static final String ENABLED_KEY = "formula";

    private FeatureModuleContext context;

    private Supplier<MoneydanceGUI> gui;

    public MDApi(FeatureModuleContext context, Supplier<MoneydanceGUI> gui) {
        this.context = context;
        this.gui = gui;
    }

    public AccountBook getBook() {
        return context.getCurrentAccountBook();
    }

    public List<Reminder> getReminders(boolean isFormulaEnabled) {
        return getBook().getReminders().getAllReminders().stream()
                .filter(reminder -> isFormulaEnabled == reminder.getBooleanParameter(ENABLED_KEY, false))
                .collect(Collectors.toList());
    }

    public static void enableReminder(Reminder reminder) {
        reminder.setParameter(MDApi.ENABLED_KEY, true);
        reminder.syncItem();
    }

    public static void disableReminder(Reminder reminder) {
        reminder.removeParameter(MDApi.ENABLED_KEY);
        reminder.syncItem();
    }

    public MoneydanceGUI getGUI() {
        return gui.get();
    }
}