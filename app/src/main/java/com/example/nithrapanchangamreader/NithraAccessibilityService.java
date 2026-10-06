package com.example.nithrapanchangamreader;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class NithraAccessibilityService extends AccessibilityService {

    private static final String NITHRA_PACKAGE = "nithra.telugu.calendar";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        if (event == null || event.getPackageName() == null) {
            return;
        }

        if (!NITHRA_PACKAGE.contentEquals(event.getPackageName())) {
            return;
        }

        AccessibilityNodeInfo root = getRootInActiveWindow();

        if (root == null) {
            return;
        }

        AccessibilityNodeInfo vasaraNode =
                findNodeById(root, "nithra.telugu.calendar:id/tam_date_txt");

        AccessibilityNodeInfo thithiNode =
                findNodeById(root, "nithra.telugu.calendar:id/thithi_txt");

        if (vasaraNode != null && thithiNode != null) {

            CharSequence vasaraText = vasaraNode.getText();
            CharSequence thithiText = thithiNode.getText();

            String vasara = extractVasara(
                    vasaraText == null ? "" : vasaraText.toString()
            );

            String thithi = extractThithi(
                    thithiText == null ? "" : thithiText.toString()
            );

            android.util.Log.d(
                    "NithraReader",
                    "వారము: " + vasara + " | తిథి: " + thithi
            );
        }
    }

    private AccessibilityNodeInfo findNodeById(
            AccessibilityNodeInfo root,
            String resourceId) {

        java.util.List<AccessibilityNodeInfo> nodes =
                root.findAccessibilityNodeInfosByViewId(resourceId);

        if (nodes != null && !nodes.isEmpty()) {
            return nodes.get(0);
        }

        return null;
    }

    private String extractVasara(String text) {

        for (String line : text.split("\\r?\\n")) {
            line = line.trim();

            if (line.contains("వాసరః")) {
                return line;
            }
        }

        return "";
    }

    private String extractThithi(String text) {

        String[] thithis = {
                "అమావాస్య",
                "పౌర్ణమి",
                "ఏకాదశి",
                "ద్వాదశి",
                "త్రయోదశి",
                "చతుర్దశి",
                "దశమి",
                "నవమి",
                "అష్టమి",
                "సప్తమి",
                "షష్ఠి",
                "పంచమి",
                "చతుర్థి",
                "తృతీయ",
                "ద్వితీయ",
                "పాడ్యమి"
        };

        for (String thithi : thithis) {
            if (text.contains(thithi)) {
                return thithi;
            }
        }

        return "";
    }

    @Override
    public void onInterrupt() {
    }
}
