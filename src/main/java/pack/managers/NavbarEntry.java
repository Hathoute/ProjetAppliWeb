package pack.managers;

import java.util.List;
import java.util.Map;

public class NavbarEntry {

    public String title;
    public Map<String, String> children;
    public boolean visible;

    public NavbarEntry(String title, Map<String, String> children, boolean visible) {
        this.title = title;
        this.children = children;
        this.visible = visible;
    }

    public NavbarEntry clone(boolean newVisibility) {
        return new NavbarEntry(title, children, newVisibility);
    }
}
