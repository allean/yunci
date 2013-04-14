

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import wordcram.*;

public class Test{

	public static void main(String[] args) {
		// create a GraphRenderer object
		WordCloud g = new WordCloud();

	// initialize it, passing the length and width of the desired target image
//        g.initialize(1000, 1000);
        g.initialize(1000, 1000);

	// have the GraphRenderer render out to a file; the file extension determines the output format
        g.render("./pjh.png");

	}
	
	
	
}
