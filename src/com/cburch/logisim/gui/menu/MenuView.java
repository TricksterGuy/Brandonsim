/* Copyright (c) 2010, Carl Burch. License information is located in the
 * com.cburch.logisim.Main source code and at www.cburch.com/logisim/. */

package com.cburch.logisim.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import com.cburch.logisim.gui.generic.ZoomModel;
import com.cburch.logisim.gui.main.Frame;

class MenuView extends Menu implements ActionListener
{

	private class ZoomLevelChoice extends JRadioButtonMenuItem implements ActionListener
	{
		private double zoom;
		private ZoomModel zmModel;

		public ZoomLevelChoice(ZoomModel zModel, double zoom)
		{
			this.zmModel = zModel;
			this.zoom = zoom;
		}

		public double getZoom()
		{
			return zoom;
		}

		public void actionPerformed(ActionEvent e)
		{
			zmModel.setZoomFactor(zoom / 100.0);
		}

		public String getZoomLvlString()
		{
			return String.format("%.0f%%", zoom);
			
		}
	}

	private LogisimMenuBar menubar;
	private JMenu zoom = new JMenu();
	private ZoomLevelChoice[] zoomLevels;
	private JMenuItem grid = new JCheckBoxMenuItem();
	private ZoomModel model;

	public MenuView(LogisimMenuBar menubar, ZoomModel zm)
	{
		this.menubar = menubar;
		model = zm;

		zoomLevels = new ZoomLevelChoice[Frame.ZOOM_OPTIONS.length];
		for (int i = 0; i < Frame.ZOOM_OPTIONS.length; i++)
		{
			zoomLevels[i] = new ZoomLevelChoice(model, Frame.ZOOM_OPTIONS[i]);
		}

		int menuMask = getToolkit().getMenuShortcutKeyMask();

		//zoom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, menuMask));
		grid.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, menuMask));

		ButtonGroup bGroup = new ButtonGroup();
		for (ZoomLevelChoice zlc : zoomLevels)
		{
			bGroup.add(zlc);
			zoom.add(zlc);
			zlc.addActionListener(zlc);
		}
		
		add(zoom);
		add(grid);

		grid.addActionListener(this);
		if (model != null)
		{
			int index = Arrays.binarySearch(Frame.ZOOM_OPTIONS, model.getZoomFactor() * 100);
			zoomLevels[index].setSelected(true);
			
			grid.setSelected(model.getShowGrid());
		}
	}

	public void localeChanged()
	{
		setText("View");
		zoom.setText("Zoom");
		for (ZoomLevelChoice zlc : zoomLevels)
			zlc.setText(zlc.getZoomLvlString());
		grid.setText("Show Grid");
	}

	public void actionPerformed(ActionEvent e)
	{
		model.setShowGrid(grid.isSelected());
	}

	@Override
	void computeEnabled()
	{
	    zoom.setEnabled(true);
	    grid.setEnabled(true);
	}
}
