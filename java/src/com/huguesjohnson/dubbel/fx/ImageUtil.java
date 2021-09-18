/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.fx;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class ImageUtil{

	//this exists to address issues with JavaFX distribution
	public static void drawButtonImageIfNotLoadedFromFXML(Button b,String imgName,Class<?> c){
		boolean needToFix=true;
		if(b.getGraphic()!=null){
			ImageView iv=(ImageView)b.getGraphic();
			if(iv!=null){
				Image i=iv.getImage();
				if(i!=null){
					needToFix=!((i.getWidth()>0.0D)&&(i.getHeight()>0.0D));
				}
			}
        }
		if(needToFix){
			b.setGraphic(new ImageView(new Image(c.getResourceAsStream(imgName))));
		}
	}
}