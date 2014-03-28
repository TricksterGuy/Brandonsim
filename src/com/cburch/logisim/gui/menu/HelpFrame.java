package com.cburch.logisim.gui.menu;

import java.net.URL;

import javax.help.HelpSet;
import javax.help.JHelp;
import javax.swing.JFrame;

import com.cburch.logisim.gui.generic.LFrame;

public class HelpFrame extends LFrame
{
	private static final long serialVersionUID = 4495614931591957498L;

	private HelpSet helpSet;
	private String helpSetUrl = "";
	private JHelp helpComponent;

	public HelpFrame()
	{
		super();
		setTitle(Strings.get("helpWindowTitle"));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		//getContentPane().add(helpComponent);
		//pack();
	}
	
	public boolean loadBroker() throws Exception
	{
		String helpUrl = Strings.get("helpsetUrl");
		ClassLoader loader = MenuHelp.class.getClassLoader();
		URL hsURL = HelpSet.findHelpSet(loader, helpUrl);
		
		if (helpUrl == null) helpUrl = "doc/doc_en.hs";
		if (helpSet == null || !helpUrl.equals(helpSetUrl))
		{

			if (hsURL == null) return false;
			helpSetUrl = helpUrl;
			helpSet = new HelpSet(null, hsURL);
			helpComponent = new JHelp(helpSet);
			getContentPane().removeAll();
			getContentPane().add(helpComponent);
			pack();
			helpComponent.revalidate();
		}
		
		return true;
	}

	public void setCurrentID(String target)
	{
		helpComponent.setCurrentID(target);
	}

	public static HelpFrame instance = null;
	
	public static HelpFrame instance()
	{
		if (instance == null)
			instance = new HelpFrame();
		return instance;
	}
}
