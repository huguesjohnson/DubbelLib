/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.swing;

import java.awt.Image;
import java.awt.Taskbar;
import java.awt.Toolkit;
import java.awt.Taskbar.Feature;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public abstract class IconUtil{

	/*
	 * will attempt to set the icon starting at element [0] of resourcePaths
	 * for example, one might pass: {"icon.png","com/mypackage/icon.png"} 
	 * because there is no pattern to which one actually works in a given environment
	 * 
	 * exits with true the first time one of these is set successfully
	 * exits with false if none are set or there is an exception
	 */
	public static boolean attemptToSetIcon(JFrame frame,String[] resourcePaths){
		if(frame==null){return(false);}
		if((resourcePaths==null)||(resourcePaths.length<1)){return(false);}
		for(int i=0;i<resourcePaths.length;i++){
			if(attemptToSetIcon(frame,resourcePaths[i])){return(true);}
		}
		return(false);
	}
	
	public static boolean attemptToSetIcon(JFrame frame,String resourcePath){
		if(frame==null){return(false);}
		if((resourcePath==null)||(resourcePath.isBlank())){return(false);}
		try{
			URL iconURL=frame.getClass().getResource(resourcePath);
    		if(iconURL!=null){
    			frame.setIconImage(new ImageIcon(iconURL).getImage());
			}else{
				return(false);
			}
    		/* if the previous step failed we won't get here and that's OK
    		 * because this would fail too */
    		if(Taskbar.isTaskbarSupported()){
    			Taskbar taskbar=Taskbar.getTaskbar();
    			if(taskbar.isSupported(Feature.ICON_IMAGE)){
    				Toolkit defaultToolkit=Toolkit.getDefaultToolkit();
    	    		/* either setting these is intentionally different for reasons
    	    		 * or I am doing this wrong */
    				Image icon=defaultToolkit.getImage(frame.getClass().getResource(resourcePath));
    				if(icon!=null){
    					taskbar.setIconImage(icon);
    					return(true);
    				}else{
    					return(false);
					}
        		}
            }
		}catch(Exception x){ 
			return(false);
		}
		return(false);
	}
}