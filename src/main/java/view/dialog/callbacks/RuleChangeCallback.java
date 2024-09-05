package view.dialog.callbacks;

import model.rules.HtmlTransformationRule;

public interface RuleChangeCallback {
    void onCompleted(HtmlTransformationRule oldRule, HtmlTransformationRule newRule);
}

