/*
Copyright 2008 WebAtlas
Authors : Mathieu Bastian, Mathieu Jacomy, Julian Bilcke
Website : http://www.gephi.org

This file is part of Gephi.

Gephi is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Gephi is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.gephi.partition.plugin;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.gephi.partition.api.Part;
import org.gephi.partition.api.Partition;
import org.gephi.partition.spi.Transformer;
import org.openide.util.NbBundle;

/**
 *
 * @author Mathieu Bastian
 */
public class NodeSizeTransformerPanel extends javax.swing.JPanel implements ChangeListener {

    private static final float DEFAULT_SIZE = 4f;
    private NodeSizeTransformer nodeSizeTransformer;
    private Partition partition;
    private JPopupMenu popupMenu;

    public NodeSizeTransformerPanel() {
        net.miginfocom.swing.MigLayout migLayout1 = new net.miginfocom.swing.MigLayout();
        migLayout1.setColumnConstraints("[pref!]20[pref!]");
        setLayout(migLayout1);

        initComponents();
        createPopup();
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (nodeSizeTransformer != null) {
                    showPopup(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }

            private void showPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    public void setup(Partition partition, Transformer transformer) {
        removeAll();
        nodeSizeTransformer = (NodeSizeTransformer) transformer;
        if (nodeSizeTransformer.getMap().isEmpty()) {
            int i = 0;
            for (Part p : partition.getParts()) {
                nodeSizeTransformer.getMap().put(p.getValue(), DEFAULT_SIZE);
                i++;
            }
        }

        this.partition = partition;
        for (final Part p : partition.getParts()) {
            JLabel partLabel = new JLabel(p.getDisplayName());
            add(partLabel);
            Float value = nodeSizeTransformer.getMap().get(p.getValue());
            if (value == null) {
                value = DEFAULT_SIZE;
            }
            JSpinner spinner = new JSpinner(new javax.swing.SpinnerNumberModel(value, Float.valueOf(0.5f), null, Float.valueOf(0.5f)));
            spinner.setPreferredSize(new Dimension(42, 20));
            spinner.putClientProperty("part", p);
            spinner.addChangeListener(this);
            add(spinner, "wrap");
        }
    }

    private void createPopup() {
        popupMenu = new JPopupMenu();
        JMenuItem randomizeItem = new JMenuItem(NbBundle.getMessage(NodeColorTransformerPanel.class, "NodeSizeTransformerBuilder.action.randomize"));
        randomizeItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                nodeSizeTransformer.getMap().clear();
                setup(partition, nodeSizeTransformer);
                revalidate();
                repaint();
            }
        });
        popupMenu.add(randomizeItem);
    }

    public void stateChanged(ChangeEvent e) {
        JSpinner spinner = (JSpinner) e.getSource();
        Part part = (Part) spinner.getClientProperty("part");
        nodeSizeTransformer.getMap().put(part.getValue(), (Float) spinner.getValue());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}