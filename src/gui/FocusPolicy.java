package gui;

import java.awt.*;
import java.util.List;

public class FocusPolicy extends FocusTraversalPolicy {

    private List<? extends Component> order;

    public FocusPolicy(List<? extends Component> order) {
        this.order = order;
    }

    public Component getComponentAfter(Container focusCycleRoot,
                                       Component aComponent) {
        int idx = (order.indexOf(aComponent) + 1) % order.size();
        return order.get(idx);
    }

    public Component getComponentBefore(Container focusCycleRoot,
                                        Component aComponent) {
        int idx = order.indexOf(aComponent) - 1;
        if (idx < 0) {
            idx = order.size() - 1;
        }
        return order.get(idx);
    }

    public Component getDefaultComponent(Container focusCycleRoot) {
        return order.get(0);
    }

    public Component getLastComponent(Container focusCycleRoot) {
        return order.get(order.size() - 1);
    }

    public Component getFirstComponent(Container focusCycleRoot) {
        return order.get(0);
    }
}
